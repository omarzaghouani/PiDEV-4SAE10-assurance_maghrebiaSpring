package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.HistoriqueReclamation;

import java.util.List;

public interface HistoriqueReclamationService {
    HistoriqueReclamation createHistorique(HistoriqueReclamation historique);
    List<HistoriqueReclamation> getAllHistoriques();
    HistoriqueReclamation getHistoriqueById(int id);
    HistoriqueReclamation updateHistorique(int id, HistoriqueReclamation historique);
    void deleteHistorique(int id);
}
