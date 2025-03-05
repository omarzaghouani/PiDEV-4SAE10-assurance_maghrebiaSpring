package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.Reclamation;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Sinistre;
import tn.esprit.examen.nomPrenomClasseExamen.entities.StatutReclamation;
import tn.esprit.examen.nomPrenomClasseExamen.entities.StatutSinistre;

import java.util.List;

public interface SinistreService {
    Sinistre createSinistre(Sinistre sinistre);
    List<Sinistre> getAllSinistres();
    Sinistre getSinistreById(int id);
    Sinistre updateSinistre(int id, Sinistre sinistre);
    void deleteSinistre(int id);
    Sinistre updateStatutSinistre(int id, StatutSinistre statutSinistre);

}
