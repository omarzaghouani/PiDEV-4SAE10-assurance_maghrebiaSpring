package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Reclamation;
import tn.esprit.examen.nomPrenomClasseExamen.services.ReclamationService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reclamations")
public class ReclamationController {

    private final ReclamationService reclamationService;

    @Autowired
    public ReclamationController(ReclamationService reclamationService) {
        this.reclamationService = reclamationService;
    }

    // ✅ Ajouter une réclamation avec TOUS les champs
    @PostMapping("/add")
    public Reclamation addReclamation(@RequestBody Reclamation reclamation) {
        // Si la date de création n'est pas envoyée, la mettre automatiquement
        if (reclamation.getDateCreation() == null) {
            reclamation.setDateCreation(new Date());
        }

        return reclamationService.createReclamation(reclamation);
    }

    // ✅ Récupérer toutes les réclamations
    @GetMapping("/list")
    public List<Reclamation> listReclamations() {
        return reclamationService.getAllReclamations();
    }

    // ✅ Modifier une réclamation
    @PutMapping("/update/{id}")
    public Reclamation updateReclamation(@PathVariable int id, @RequestBody Reclamation updatedReclamation) {
        return reclamationService.updateReclamation(id, updatedReclamation);
    }

    // ✅ Supprimer une réclamation
    @DeleteMapping("/delete/{id}")
    public String deleteReclamation(@PathVariable int id) {
        reclamationService.deleteReclamation(id);
        return "Réclamation supprimée avec succès";
    }
}
