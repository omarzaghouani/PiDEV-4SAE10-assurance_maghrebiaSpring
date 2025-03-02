import sys
import os
import pandas as pd
import joblib

try:
    # ✅ Get the script directory (relative path)
    script_dir = os.path.dirname(os.path.abspath(__file__))
    model_path = os.path.join(script_dir, "fraud_model.pkl")

    # ✅ Load the trained model
    model = joblib.load(model_path)

    # ✅ Read input transaction details
    input_data = [float(x) for x in sys.argv[1:7]]

    # ✅ Convert input to DataFrame
    columns = ['user_id', 'order_id', 'amount', 'reason_code', 'refund_count', 'previous_frauds']
    df = pd.DataFrame([input_data], columns=columns)

    # ✅ Make a fraud prediction
    prediction = model.predict(df)[0]
    probability = model.predict_proba(df)[0][1]

    # ✅ Print result for Java to capture
    print(f"{prediction},{probability}")

except Exception as e:
    print("0,0.0")  # ✅ Ensures Java does not break
