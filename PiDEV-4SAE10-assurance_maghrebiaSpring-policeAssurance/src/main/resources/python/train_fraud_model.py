import pandas as pd
import joblib
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import classification_report

# Load historical fraud data (replace with actual database extraction)
data = pd.read_csv('fraud_data.csv')

# Features used for training
features = ['user_id', 'order_id', 'amount', 'reason_code', 'refund_count', 'previous_frauds']
X = data[features]
y = data['is_fraud']  # Target variable (1 = fraud, 0 = legitimate)

# Split data into training and test sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Train a Random Forest model
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X_train, y_train)

# Evaluate performance
y_pred = model.predict(X_test)
print("Model Performance:\n", classification_report(y_test, y_pred))

# Save trained model to a file
model_path = "src/main/resources/python/fraud_model.pkl"
joblib.dump(model, model_path)

print(f"Model saved at: {model_path}")
