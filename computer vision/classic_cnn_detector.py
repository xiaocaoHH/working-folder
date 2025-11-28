"""
Classic CNN-based object detector on Pascal VOC 2007 (person, car, dog).

Model:
  - VGG-like CNN backbone (Conv + MaxPool blocks)
  - Flatten + Dense to predict a fixed number of boxes per image

Each box slot predicts:
  [x_center, y_center, width, height] in [0,1],
  objectness (0/1),
  class scores for (person, car, dog)

Requirements:
  pip install tensorflow tensorflow-datasets matplotlib

Run:
  python classic_cnn_detector.py
"""

import math
import numpy as np
import tensorflow as tf
import tensorflow_datasets as tfds
import matplotlib.pyplot as plt

# ==========================
# 1. CONFIG
# ==========================

IMG_SIZE = 224          # input images resized to 224x224
MAX_BOXES = 5           # max objects per image (slots)
CLASS_NAMES = ["person", "car", "dog"]
NUM_CLASSES = len(CLASS_NAMES)

LAMBDA_COORD = 5.0
LAMBDA_NOOBJ = 0.5

BATCH_SIZE = 16
EPOCHS = 10


# ==========================
# 2. DATASET: PASCAL VOC 2007
# ==========================

# VOC 2007 label names (TFDS order)
VOC_LABEL_NAMES = [
    "aeroplane", "bicycle", "bird", "boat", "bottle",
    "bus", "car", "cat", "chair", "cow",
    "diningtable", "dog", "horse", "motorbike", "person",
    "pottedplant", "sheep", "sofa", "train", "tvmonitor"
]
VOC_NAME_TO_ID = {name: i for i, name in enumerate(VOC_LABEL_NAMES)}

CLASS_NAME_TO_INDEX = {name: i for i, name in enumerate(CLASS_NAMES)}
# Map VOC label id -> our class index (or None if we ignore)
VOC_ID_TO_CLASS_INDEX = {}
for name, voc_id in VOC_NAME_TO_ID.items():
    if name in CLASS_NAME_TO_INDEX:
        VOC_ID_TO_CLASS_INDEX[voc_id] = CLASS_NAME_TO_INDEX[name]
    else:
        VOC_ID_TO_CLASS_INDEX[voc_id] = None


def load_voc(split):
    """
    Load VOC 2007 from TFDS.

    Each example has:
      image: uint8 [H,W,3]
      objects: {
        'bbox': [N,4] float32 (ymin, xmin, ymax, xmax) normalized [0,1]
        'label': [N] int64 (index in VOC_LABEL_NAMES)
      }
    Splits: "train", "validation", "test"

    # It is a way to download data once
    MANUAL_DIR = "C:\\Users\\fiona\\Desktop\\working-folder\\__workspace\\python\\computer vision\\tensorflow_datasets"

    builder = tfds.builder("voc/2007")
    builder.download_and_prepare(
        download_config=tfds.download.DownloadConfig(
            manual_dir=MANUAL_DIR
        )
    )

    ds_train = builder.as_dataset(split="train")
    ds_val   = builder.as_dataset(split="validation")
    ds_test  = builder.as_dataset(split="test")
    """

    ds, info = tfds.load("voc/2007", split=split, with_info=True)
    return ds, info


# ==========================
# 3. TARGET ENCODING
# ==========================

def encode_example(example):
    """
    Convert TFDS example -> (image, y_true).

    image: float32 [IMG_SIZE, IMG_SIZE, 3], in [0,1]
    y_true: [MAX_BOXES, 5 + NUM_CLASSES]
        per box: [x_center, y_center, width, height, objectness, class_one_hot...]
        - all normalized to [0,1]
        - objectness = 1 only for actual objects
        - extra slots are zeros (objectness=0)
    """
    image = tf.cast(example["image"], tf.float32) / 255.0     # [H,W,3] -> [0,1]
    bboxes = example["objects"]["bbox"]                       # [N,4], (ymin,xmin,ymax,xmax)
    labels = example["objects"]["label"]                      # [N]

    # Resize image to fixed size
    image = tf.image.resize(image, (IMG_SIZE, IMG_SIZE))

    bboxes = tf.cast(bboxes, tf.float32)
    labels = tf.cast(labels, tf.int32)

    def _encode(bboxes_np, labels_np):
        target = np.zeros((MAX_BOXES, 5 + NUM_CLASSES), dtype=np.float32)

        # We keep at most MAX_BOXES objects
        count = 0
        for box, lab in zip(bboxes_np, labels_np):
            voc_id = int(lab)
            cls_idx = VOC_ID_TO_CLASS_INDEX.get(voc_id, None)
            if cls_idx is None:
                continue  # ignore classes not in our subset

            ymin, xmin, ymax, xmax = box
            cx = (xmin + xmax) / 2.0
            cy = (ymin + ymax) / 2.0
            w = xmax - xmin
            h = ymax - ymin

            if count >= MAX_BOXES:
                break

            slot = target[count]
            slot[0] = cx
            slot[1] = cy
            slot[2] = w
            slot[3] = h
            slot[4] = 1.0  # objectness

            one_hot = np.zeros(NUM_CLASSES, dtype=np.float32)
            one_hot[cls_idx] = 1.0
            slot[5:] = one_hot

            count += 1

        return target

    y_true = tf.py_function(
        func=_encode,
        inp=[bboxes, labels],
        Tout=tf.float32
    )
    y_true.set_shape((MAX_BOXES, 5 + NUM_CLASSES))

    return image, y_true


def build_dataset(split, batch_size, shuffle=True):
    ds, _ = load_voc(split)
    ds = ds.map(encode_example, num_parallel_calls=tf.data.AUTOTUNE)
    if shuffle:
        ds = ds.shuffle(1000)
    #ds = ds.take(100).batch(batch_size).prefetch(tf.data.AUTOTUNE)
    ds = ds.take(1000).batch(batch_size).prefetch(tf.data.AUTOTUNE)
    return ds


# ==========================
# 4. MODEL: CLASSIC CNN + FC HEAD
# ==========================

def build_cnn_detector():
    """
    Classic CNN (VGG-like) backbone + fully connected head.

    Input: (IMG_SIZE, IMG_SIZE, 3)
    Output: (MAX_BOXES, 5 + NUM_CLASSES) per image
    """
    inputs = tf.keras.Input(shape=(IMG_SIZE, IMG_SIZE, 3))

    x = inputs
    # Block 1: 224 -> 112
    x = tf.keras.layers.Conv2D(64, 3, padding="same", activation="relu")(x)
    x = tf.keras.layers.Conv2D(64, 3, padding="same", activation="relu")(x)
    x = tf.keras.layers.MaxPool2D(pool_size=2, strides=2)(x)

    # Block 2: 112 -> 56
    x = tf.keras.layers.Conv2D(128, 3, padding="same", activation="relu")(x)
    x = tf.keras.layers.Conv2D(128, 3, padding="same", activation="relu")(x)
    x = tf.keras.layers.MaxPool2D(pool_size=2, strides=2)(x)

    # Block 3: 56 -> 28
    x = tf.keras.layers.Conv2D(256, 3, padding="same", activation="relu")(x)
    x = tf.keras.layers.Conv2D(256, 3, padding="same", activation="relu")(x)
    x = tf.keras.layers.MaxPool2D(pool_size=2, strides=2)(x)

    # Block 4: 28 -> 14
    x = tf.keras.layers.Conv2D(512, 3, padding="same", activation="relu")(x)
    x = tf.keras.layers.Conv2D(512, 3, padding="same", activation="relu")(x)
    x = tf.keras.layers.MaxPool2D(pool_size=2, strides=2)(x)

    # Block 5: 14 -> 7
    x = tf.keras.layers.Conv2D(512, 3, padding="same", activation="relu")(x)
    x = tf.keras.layers.Conv2D(512, 3, padding="same", activation="relu")(x)
    x = tf.keras.layers.MaxPool2D(pool_size=2, strides=2)(x)

    x = tf.keras.layers.Flatten()(x)
    x = tf.keras.layers.Dense(1024, activation="relu")(x)
    x = tf.keras.layers.Dropout(0.5)(x)

    # Output: MAX_BOXES * (4 + 1 + NUM_CLASSES) values
    output_dim = MAX_BOXES * (4 + 1 + NUM_CLASSES)
    x = tf.keras.layers.Dense(output_dim, activation=None)(x)
    outputs = tf.keras.layers.Reshape((MAX_BOXES, 4 + 1 + NUM_CLASSES))(x)

    model = tf.keras.Model(inputs, outputs, name="classic_cnn_detector")
    return model


# ==========================
# 5. LOSS FUNCTION
# ==========================

@tf.function
def detection_loss(y_true, y_pred):
    """
    y_true, y_pred: (batch, MAX_BOXES, 5 + NUM_CLASSES)
      0: x_center, 1: y_center, 2: w, 3: h (all in [0,1])
      4: objectness (0 or 1)
      5..: class one-hot

    For predictions:
      - we apply sigmoid to the first 5 (so boxes in [0,1], objectness in [0,1])
      - classification logits are raw (for softmax)
    """
    true_box = y_true[..., 0:4]      # (B,N,4)
    true_obj = y_true[..., 4:5]      # (B,N,1)
    true_cls = y_true[..., 5:]       # (B,N,C)

    # Predicted
    pred_raw_box = y_pred[..., 0:4]        # raw
    pred_raw_obj = y_pred[..., 4:5]        # raw
    pred_raw_cls = y_pred[..., 5:]         # logits

    # Constrain predictions
    pred_box = tf.sigmoid(pred_raw_box)    # (B,N,4) in [0,1]
    pred_obj = tf.sigmoid(pred_raw_obj)    # (B,N,1) in [0,1]

    # Masks
    obj_mask = true_obj                      # 1 where slot has object
    noobj_mask = 1.0 - obj_mask

    # 1) Box regression loss (only for object slots)
    box_diff = true_box - pred_box
    box_loss_per_box = tf.reduce_sum(tf.square(box_diff), axis=-1, keepdims=True)  # (B,N,1)
    box_loss = LAMBDA_COORD * tf.reduce_sum(obj_mask * box_loss_per_box)

    # 2) Objectness loss (binary cross-entropy)
    bce_logits = tf.keras.losses.BinaryCrossentropy(from_logits=False, reduction="none")
    obj_loss_all = bce_logits(true_obj, pred_obj)[..., tf.newaxis]  # (B,N,1)

    # Separate contributions
    obj_loss_pos = tf.reduce_sum(obj_mask * obj_loss_all)
    obj_loss_neg = tf.reduce_sum(noobj_mask * obj_loss_all)
    obj_loss_neg = LAMBDA_NOOBJ * obj_loss_neg

    # 3) Classification loss (only for object slots)
    ce_logits = tf.keras.losses.CategoricalCrossentropy(from_logits=True, reduction="none")
    cls_loss_per_box = ce_logits(true_cls, pred_raw_cls)      # (B,N)
    cls_loss_per_box = cls_loss_per_box[..., tf.newaxis]      # (B,N,1)
    cls_loss = tf.reduce_sum(obj_mask * cls_loss_per_box)

    total_loss = box_loss + obj_loss_pos + obj_loss_neg + cls_loss
    batch_size = tf.cast(tf.shape(y_true)[0], tf.float32)
    return total_loss / batch_size


# ==========================
# 6. TRAINING LOOP
# ==========================

def train():
    print("Loading datasets...")
    train_ds = build_dataset("train", BATCH_SIZE, shuffle=True)
    val_ds = build_dataset("validation", BATCH_SIZE, shuffle=False)

    print("Building model...")
    model = build_cnn_detector()
    model.summary()

    optimizer = tf.keras.optimizers.Adam(1e-4)
    train_loss_metric = tf.keras.metrics.Mean()
    val_loss_metric = tf.keras.metrics.Mean()

    @tf.function
    def train_step(images, y_true):
        with tf.GradientTape() as tape:
            y_pred = model(images, training=True)
            loss = detection_loss(y_true, y_pred)
        grads = tape.gradient(loss, model.trainable_variables)
        optimizer.apply_gradients(zip(grads, model.trainable_variables))
        return loss

    @tf.function
    def val_step(images, y_true):
        y_pred = model(images, training=False)
        loss = detection_loss(y_true, y_pred)
        return loss

    for epoch in range(EPOCHS):
        print(f"\nEpoch {epoch+1}/{EPOCHS}")
        train_loss_metric.reset_state()
        val_loss_metric.reset_state()

        # Training
        for i, (images, y_true) in enumerate(train_ds):
            loss = train_step(images, y_true)
            train_loss_metric.update_state(loss)
            if (i + 1) % 50 == 0:
                print(f"  Step {i+1}: train loss = {train_loss_metric.result().numpy():.4f}")

        # Validation
        for images, y_true in val_ds:
            loss = val_step(images, y_true)
            val_loss_metric.update_state(loss)

        print(f"  Train loss: {train_loss_metric.result().numpy():.4f}")
        print(f"  Val   loss: {val_loss_metric.result().numpy():.4f}")

    model.save("classic_cnn_detector.h5")
    print("Model saved to classic_cnn_detector.h5")
    return model, val_ds


# ==========================
# 7. INFERENCE & VISUALIZATION
# ==========================

def decode_predictions(pred, conf_thresh=0.5):
    """
    pred: (MAX_BOXES, 5 + NUM_CLASSES) for a single image.
    Returns list of (xmin, ymin, xmax, ymax, class_idx, score) in pixel coords.
    """
    pred = pred.numpy()
    boxes_out = []

    for i in range(MAX_BOXES):
        slot = pred[i]
        raw_box = slot[0:4]
        raw_obj = slot[4]
        raw_cls = slot[5:]

        obj = 1.0 / (1.0 + math.exp(-raw_obj))  # sigmoid
        if obj < conf_thresh:
            continue

        # Box and class
        box = 1.0 / (1.0 + np.exp(-raw_box))  # sigmoid to [0,1]
        cx, cy, w, h = box
        cls_probs = tf.nn.softmax(raw_cls).numpy()
        cls_idx = int(np.argmax(cls_probs))
        cls_score = float(cls_probs[cls_idx])
        score = obj * cls_score

        xmin = (cx - w / 2.0) * IMG_SIZE
        ymin = (cy - h / 2.0) * IMG_SIZE
        xmax = (cx + w / 2.0) * IMG_SIZE
        ymax = (cy + h / 2.0) * IMG_SIZE

        boxes_out.append((xmin, ymin, xmax, ymax, cls_idx, score))

    return boxes_out


def visualize_sample(model, dataset, num_images=3, conf_thresh=0.5):
    it = iter(dataset)
    images, y_trues = next(it)
    preds = model(images, training=False)

    for i in range(min(num_images, images.shape[0])):
        img = images[i].numpy()
        pred = preds[i]

        boxes = decode_predictions(pred, conf_thresh=conf_thresh)

        plt.figure(figsize=(6, 6))
        plt.imshow(img)
        ax = plt.gca()

        for (xmin, ymin, xmax, ymax, cls_idx, score) in boxes:
            rect = plt.Rectangle(
                (xmin, ymin),
                xmax - xmin,
                ymax - ymin,
                fill=False,
                edgecolor="red",
                linewidth=2
            )
            ax.add_patch(rect)
            label = f"{CLASS_NAMES[cls_idx]} {score:.2f}"
            ax.text(
                xmin,
                ymin,
                label,
                fontsize=8,
                color="yellow",
                bbox=dict(facecolor="black", alpha=0.5)
            )

        plt.axis("off")
        plt.show()


# ==========================
# 8. MAIN
# ==========================

if __name__ == "__main__":
    tf.random.set_seed(42)
    np.random.seed(42)

    model, val_ds = train()
    # Show a few validation predictions
    visualize_sample(model, val_ds, num_images=3, conf_thresh=0.3)
