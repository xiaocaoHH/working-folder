import os
import sys
import argparse
import json
import time
import math
import numpy as np
import tensorflow as tf
from tensorflow import keras

from data_model import StockDataSet
from model_lstm import LSTM
from sklearn.metrics import mean_squared_error, mean_absolute_error, r2_score

def load_sp500(input_dim, num_steps, target_symbol=None, test_ratio=0.05):
    return [
        StockDataSet(
            target_symbol,
            input_dim=input_dim,
            num_steps=num_steps,
            test_ratio=test_ratio)
    ]

def main():
    # Parse arguments
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--stock_symbol",
        type=str,
        default="_SP500",
        help="Target stock symbol [default: None]"
    )
    parser.add_argument(
        "--train",
        action='store_true',
        help="Train model [default: False]"
    )
    parser.add_argument(
        "--input_dim",
        type=int,
        default=2,
        help="Input size [default: 2]"
    )
    parser.add_argument(
        "--num_steps",
        type=int,
        default=30,
        help="Number of steps [default: 30]"
    )
    parser.add_argument(
        "--num_layers",
        type=int,
        default=1,
        help="Number of layers [default: 1]"
    )
    parser.add_argument(
        "--lstm_size",
        type=int,
        default=128,
        help="LSTM hidden size [default: 128]"
    )
    parser.add_argument(
        "--keep_prob",
        type=float,
        default=0.8,
        help="Keep probability for dropout [default: 0.8]"
    )
    parser.add_argument(
        "--batch_size",
        type=int,
        default=64,
        help="Batch size [default: 64]"
    )
    parser.add_argument(
        "--init_learning_rate",
        type=float,
        default=0.001,
        help="Initial learning rate [default: 0.001]"
    )
    parser.add_argument(
        "--learning_rate_decay",
        type=float,
        default=0.99,
        help="Learning rate decay [default: 0.99]"
    )
    parser.add_argument(
        "--init_epoch",
        type=int,
        default=5,
        help="Initial epoch for decay [default: 5]"
    )
    parser.add_argument(
        "--max_epoch",
        type=int,
        default=50,
        help="Maximum epochs [default: 50]"
    )
    
    args = parser.parse_args()
    
    # Set random seeds for reproducibility
    np.random.seed(101)
    tf.random.set_seed(101)
    
    # Load data
    stock_data_list = load_sp500(
        args.input_dim,
        args.num_steps,
        target_symbol=args.stock_symbol,
    )
    
    # Create model
    model = LSTM(
        input_dim=args.input_dim,
        num_steps=args.num_steps,
        lstm_size=args.lstm_size,
        num_layers=args.num_layers,
        keep_prob=args.keep_prob,
        batch_size=args.batch_size,
        init_learning_rate=args.init_learning_rate,
        learning_rate_decay=args.learning_rate_decay,
        init_epoch=args.init_epoch
    )

    
    if args.train:
        # Training mode
        for stock in stock_data_list:
            print(f"Training on stock: {stock.stock_sym}")
            model.train(stock, args.max_epoch)
    else:
        # Inference mode
        if args.stock_symbol is None:
            raise ValueError("Must specify --stock_symbol for inference mode")
        
        # Load the model and make predictions
        stock = stock_data_list[0]
        model.load_model(stock.stock_sym)
        
        # Example prediction
        print(f"Making predictions for {stock.stock_sym}")
        # Add your prediction logic here
        pred_y = model.predict(stock.test_X)
        
        # Evaluate
        true_y = stock.test_y

        # Ensure they are NumPy arrays
        pred_y = np.array(pred_y)
        true_y = np.array(true_y)

        # Print evaluation metrics
        mse = mean_squared_error(true_y, pred_y)
        mae = mean_absolute_error(true_y, pred_y)
        r2 = r2_score(true_y, pred_y)

        print(f"MSE: {mse:.4f}")
        print(f"MAE: {mae:.4f}")
        print(f"R² Score: {r2:.4f}")
        print("*********done***********")


if __name__ == '__main__':
    main()