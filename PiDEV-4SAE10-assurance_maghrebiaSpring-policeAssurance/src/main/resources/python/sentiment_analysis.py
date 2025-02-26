from textblob import TextBlob
import sys
import json

def analyze_sentiment(feedback_text):
    # Analyse du sentiment avec TextBlob
    analysis = TextBlob(feedback_text)
    polarity = analysis.sentiment.polarity

    if polarity > 0:
        return "Positive"
    elif polarity == 0:
        return "Neutral"
    else:
        return "Negative"

if __name__ == "__main__":
    # Récupérer le texte du feedback depuis l'argument en ligne de commande
    feedback_text = sys.argv[1]

    # Effectuer l'analyse du sentiment
    sentiment = analyze_sentiment(feedback_text)

    # Retourner le résultat sous forme JSON
    print(json.dumps({"sentiment": sentiment}))
