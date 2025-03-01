import sys
import pandas as pd
import joblib

# Load the trained model
model = joblib.load('fraud_model.pkl')

# Read input transaction details from command line arguments
input_data = [float(x) for x in sys.argv[1:]]  # Convert input to numerical values

# Convert to DataFrame (Ensure features match training data)
columns = ['user_id', 'order_id', 'amount', 'reason_code', 'refund_count', 'previous_frauds']
df = pd.DataFrame([input_data], columns=columns)

# Predict fraud status
prediction = model.predict(df)[0]
probability = model.predict_proba(df)[0][1]

# Print result for Java to capture
print(f"{prediction},{probability}")
