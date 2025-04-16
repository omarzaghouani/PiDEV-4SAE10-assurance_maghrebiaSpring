package tn.esprit.examen.nomPrenomClasseExamen.service;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

@Service
public class CurrencyService {

    private final String API_KEY = "72572a4d89322b3098896a9f";
    private final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public double convert(String from, String to, double amount) {
        String url = API_URL + from;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("conversion_rates")) {
            Map<String, Double> rates = (Map<String, Double>) response.get("conversion_rates");
            double rate = rates.get(to);
            return amount * rate;
        } else {
            throw new RuntimeException("Échec de récupération des taux de change");
        }
    }
    public Set<String> getAvailableCurrencies() {
        String url = "https://v6.exchangerate-api.com/v6/72572a4d89322b3098896a9f/latest/USD";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        System.out.println("🔍 Réponse de l'API = " + response);

        if (response != null && response.containsKey("conversion_rates")) {
            Map<String, Object> rates = (Map<String, Object>) response.get("conversion_rates");
            return rates.keySet();
        } else {
            throw new RuntimeException("Conversion rates manquants : " + response);
        }
    }

}
