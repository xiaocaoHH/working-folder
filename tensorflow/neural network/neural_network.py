import tensorflow as tf
import pandas as pd

# read data
df_train = pd.read_csv('exercise_40_train.csv')
df_test = pd.read_csv('exercise_40_test.csv')

# print(df_train.describe) 40000 x 101 (y,x1...x100)
# print(df_test.describe) 10000 x 100

labels_train = df_train['y']
data_train = df_train.iloc[:,1:101]
data_test = df_test

# remove non numeric cols
non_numeric_cols = df_train.select_dtypes(exclude=['number']).columns.tolist()
# print(non_numeric_cols)
for col in non_numeric_cols:
    data_train.pop(col)
    data_test.pop(col)

# deal with NaN values
data_train.dropna(inplace=True)
data_test.dropna(inplace=True)

# some parameters
h1 = 64  # hidden layer 1 size
h2 = 32  # hidden layer 2 size
output = 1 # output size
num_features = len(data_train.columns) # number of features
# print(num_features)

# define variables
# hidden layer 1
W1 = tf.Variable(tf.random.normal([num_features, h1], stddev=0.1))
b1 = tf.Variable(tf.zeros([h1]))

# hidden layer 2
W2 = tf.Variable(tf.random.normal([h1, h2], stddev=0.1))
b2 = tf.Variable(tf.zeros([h2]))

# output layer
W3 = tf.Variable(tf.random.normal([h2, output], stddev=0.1))
b3 = tf.Variable(tf.zeros([output]))

trainable_variables = [W1,W2,W3,b1,b2,b3]

def forward_process(inputs):
    # Hidden Layer 1 + ReLU activation
    hl_1 = tf.matmul(inputs, W1) + b1
    hl_a_1 = tf.nn.relu(hl_1)
    
    # Hidden Layer 2 + ReLU activation
    hl_2 = tf.matmul(hl_a_1, W2) + b2
    hl_a_2 = tf.nn.relu(hl_2)
    
    # Output Layer 
    outputs = tf.matmul(hl_a_2, W3) + b3

    return outputs

# loss function
optimizer = tf.optimizers.Adam(learning_rate=0.001)

# some training parameters
epochs = 20
batch_size = 30

for epoch in range(epochs):
    # Basic mini-batching over your data tensor
    for i in range(0, len(data_train), batch_size):
        X_batch = data_train.iloc[i : i + batch_size, :]
        y_batch = labels_train.iloc[i : i + batch_size]

        # cast data type
        X_batch_tensor = tf.cast(X_batch.values, dtype=tf.float32)
        y_batch_tensor = tf.cast(y_batch.values, dtype=tf.float32)
        
        # Track operations to calculate gradients
        with tf.GradientTape() as tape:
            predictions = forward_process(X_batch_tensor)
            loss = tf.reduce_mean(tf.square(y_batch_tensor - predictions)) # MSE Loss
            
        # Calculate gradients with respect to all trainable variables
        gradients = tape.gradient(loss, trainable_variables)
        
        # Update weights and biases using the optimizer
        optimizer.apply_gradients(zip(gradients, trainable_variables))
        
    if epoch % 5 == 0 or epoch == epochs - 1:
        print(f"Epoch {epoch}: Loss = {loss.numpy():.4f}")
