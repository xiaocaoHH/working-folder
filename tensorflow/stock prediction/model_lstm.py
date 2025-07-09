import numpy as np
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers
import os


class LSTM:
    def __init__(self, input_dim=1, num_steps=30, lstm_size=128, num_layers=1,
                 keep_prob=0.8, batch_size=64, init_learning_rate=0.001,
                 learning_rate_decay=0.99, init_epoch=5, max_epoch=50):
        """
        Initialize LSTM model.
        """
        self.input_dim = input_dim
        self.num_steps = num_steps
        self.lstm_size = lstm_size
        self.num_layers = num_layers
        self.keep_prob = keep_prob
        self.batch_size = batch_size
        self.init_learning_rate = init_learning_rate
        self.learning_rate_decay = learning_rate_decay
        self.init_epoch = init_epoch
        self.max_epoch = max_epoch
        
        self.model = None
        self.build_model()
    
    def build_model(self):
        """Build the LSTM model using Keras."""
        # Input layer
        inputs = layers.Input(shape=(self.num_steps, self.input_dim), name='inputs')
        
        # Stack LSTM layers
        x = inputs
        for i in range(self.num_layers):
            return_sequences = (i < self.num_layers - 1)
            x = layers.LSTM(
                units=self.lstm_size,
                return_sequences=return_sequences,
                dropout=1-self.keep_prob,
                recurrent_dropout=1-self.keep_prob,
                name=f'lstm_layer_{i}'
            )(x)
        
        # Output layer
        outputs = layers.Dense(self.input_dim, name='output')(x)
        
        # Create model
        self.model = keras.Model(inputs=inputs, outputs=outputs)
        
        # Compile model
        self.optimizer = keras.optimizers.Adam(learning_rate=self.init_learning_rate)
        self.model.compile(
            optimizer=self.optimizer,
            loss='mean_squared_error',
            metrics=['mae']
        )
    
    def train(self, stock_data, max_epoch=None):
        """Train the model."""
        if max_epoch is None:
            max_epoch = self.max_epoch
        
        # Prepare callbacks
        callbacks = []
        
        # Learning rate scheduler
        def lr_schedule(epoch):
            if epoch < self.init_epoch:
                return self.init_learning_rate
            else:
                return self.init_learning_rate * (self.learning_rate_decay ** (epoch - self.init_epoch))
        
        lr_scheduler = keras.callbacks.LearningRateScheduler(lr_schedule)
        callbacks.append(lr_scheduler)
        
        # Model checkpoint
        checkpoint_path = f"models/{stock_data.stock_sym}_lstm_checkpoint.keras"
        os.makedirs(os.path.dirname(checkpoint_path), exist_ok=True)
        checkpoint = keras.callbacks.ModelCheckpoint(
            checkpoint_path,
            save_best_only=True,
            monitor='val_loss',
            verbose=1
        )
        callbacks.append(checkpoint)
        
        # Early stopping
        early_stopping = keras.callbacks.EarlyStopping(
            monitor='val_loss',
            patience=5,
            restore_best_weights=True
        )
        callbacks.append(early_stopping)
        
        # Prepare data
        train_X, train_y, val_X, val_y = stock_data.train_X, stock_data.train_y, stock_data.test_X, stock_data.test_y

        validation_data = (val_X, val_y)        
        # Train the model
        history = self.model.fit(
            train_X, train_y,
            batch_size=self.batch_size,
            epochs=max_epoch,
            validation_data=validation_data,
            callbacks=callbacks,
            verbose=1
        )
        
        # Save final model
        self.model.save(f"models/{stock_data.stock_sym}_lstm_final.keras")
        
        return history
    
    def predict(self, X):
        """Make predictions."""
        return self.model.predict(X)
    
    def load_model(self, symbol):
        """Load a saved model."""
        model_path = f"models/{symbol}_lstm_final.keras"
        checkpoint_path = f"models/{symbol}_lstm_checkpoint"
        
        if os.path.exists(model_path):
            self.model = keras.models.load_model(model_path)
            print(f"Model loaded from {model_path}")
        elif os.path.exists(checkpoint_path):
            self.model = keras.models.load_model(checkpoint_path)
            print(f"Model loaded from {checkpoint_path}")
        else:
            print(f"No saved model found for {symbol}")