import sys
import json
from textblob import TextBlob

# 🔹 Listes de mots-clés enrichies
positive_keywords = [
    "excellent", "super", "rapide", "professionnel", "satisfait", "parfait", "merci", "réactif", "aimable",
    "top", "génial", "efficace", "agréable", "formidable", "incroyable", "fiable", "complet", "soutien",
    "accompagnement", "solution", "précis", "simple", "facile", "superbe", "adorable", "bravo", "parfaitement",
    "flexible", "courtois", "respectueux", "attentionné", "compétent", "clair", "pratique",
    "rassurant", "utile", "proactif", "à recommander", "proacti"
]

negative_keywords = [
    "lent", "nul", "horrible", "déçu", "décevant", "attente", "problème", "inadmissible", "pire", "erreur",
    "catastrophique", "délai", "désagréable", "injoignable", "compliqué", "incompréhensible", "dommage",
    "oubli", "manque", "faible", "inacceptable", "non professionnel", "long", "confus", "arnaque", "désorganisation",
    "perte de temps", "retard", "désastre", "chaotique", "fuite", "brouillon", "stressant", "colère", "mécontent",
    "désolé", "mauvais service", "mal poli", "déplorable", "négligence", "pas satisfait", "médiocre", "douteux"
]


def correct_text(text):
    """ Corrige automatiquement les fautes dans le texte. """
    blob = TextBlob(text)
    corrected = blob.correct()
    return str(corrected)


def analyze_sentiment(feedback_text):
    # 🔹 Correction automatique des fautes
    corrected_text = correct_text(feedback_text)

    analysis = TextBlob(corrected_text)
    polarity = analysis.sentiment.polarity
    text_lower = corrected_text.lower()

    # 🔹 Comptage des mots-clés
    positive_count = sum(word in text_lower for word in positive_keywords)
    negative_count = sum(word in text_lower for word in negative_keywords)

    # 🔹 Logique combinée
    if polarity >= 0.1 or positive_count > negative_count:
        sentiment = "Positive"
    elif polarity <= -0.1 or negative_count > positive_count:
        sentiment = "Negative"
    else:
        sentiment = "Neutral"

    return {
        "sentiment": sentiment,
        "polarity": polarity,
        "positive_keywords_found": positive_count,
        "negative_keywords_found": negative_count,
        "corrected_text": corrected_text
    }


if __name__ == "__main__":
    if len(sys.argv) > 1:
        feedback_text = sys.argv[1]
        result = analyze_sentiment(feedback_text)
        print(json.dumps(result))
    else:
        print(json.dumps({"error": "No input text provided"}))
