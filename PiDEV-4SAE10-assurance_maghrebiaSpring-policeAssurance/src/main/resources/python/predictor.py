import sys
import json
from statistics import mean

def safe_float(value):
    try:
        return float(value)
    except:
        return 0.0

def main():
    try:
        raw_json = sys.argv[1]
        data = json.loads(raw_json)

        # Extraction avec noms corrects en minuscules
        revenues = [safe_float(item.get("totalRevenue")) for item in data]
        expenses = [safe_float(item.get("expenses")) for item in data]
        net_incomes = [safe_float(item.get("netIncome")) for item in data]

        if not revenues or not expenses or not net_incomes:
            raise Exception("Données manquantes.")

        predicted_revenue = revenues[-1] * 1.05
        predicted_expenses = mean(expenses)
        anomaly = net_incomes[-1] < mean(net_incomes) * 0.5

        result = {
            "predictedRevenue": round(predicted_revenue, 2),
            "predictedExpenses": round(predicted_expenses, 2),
            "anomaly": anomaly
        }

        print(json.dumps(result), flush=True)

    except Exception as e:
        print(json.dumps({"error": str(e)}), flush=True)

if __name__ == "__main__":
    main()
