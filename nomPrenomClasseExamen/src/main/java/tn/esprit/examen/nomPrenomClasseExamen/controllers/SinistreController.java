package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Reclamation;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Sinistre;
import tn.esprit.examen.nomPrenomClasseExamen.entities.StatutReclamation;
import tn.esprit.examen.nomPrenomClasseExamen.entities.StatutSinistre;
import tn.esprit.examen.nomPrenomClasseExamen.services.SinistreService;
import java.util.List;

@RestController
@RequestMapping("/api/sinistres")
@CrossOrigin(origins = "http://localhost:4200")

public class SinistreController {

    private final SinistreService sinistreService;

    public SinistreController(SinistreService sinistreService) {
        this.sinistreService = sinistreService;
    }

    @PostMapping("/add")
    public Sinistre addSinistre(@RequestBody Sinistre sinistre) {
        return sinistreService.createSinistre(sinistre);
    }

    @GetMapping("/list")
    public List<Sinistre> getAllSinistres() {
        return sinistreService.getAllSinistres();
    }

    @GetMapping("/{id}")
    public Sinistre getSinistreById(@PathVariable int id) {
        return sinistreService.getSinistreById(id);
    }

    @PutMapping("/update/{id}")
    public Sinistre updateSinistre(@PathVariable int id, @RequestBody Sinistre sinistre) {
        return sinistreService.updateSinistre(id, sinistre);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSinistre(@PathVariable int id) {
        sinistreService.deleteSinistre(id);
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<Sinistre> updateStatus(@PathVariable int id, @RequestParam StatutSinistre statutSinistre) {
        try {
            Sinistre updatedSinistre = sinistreService.updateStatutSinistre(id, statutSinistre);
            return ResponseEntity.ok(updatedSinistre);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
