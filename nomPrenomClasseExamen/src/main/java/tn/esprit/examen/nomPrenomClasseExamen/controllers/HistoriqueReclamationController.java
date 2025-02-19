package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.HistoriqueReclamation;
import tn.esprit.examen.nomPrenomClasseExamen.services.HistoriqueReclamationService;

import java.util.List;

@RestController
@RequestMapping("/api/historiques")
public class HistoriqueReclamationController {

    private final HistoriqueReclamationService historiqueService;

    public HistoriqueReclamationController(HistoriqueReclamationService historiqueService) {
        this.historiqueService = historiqueService;
    }

    @PostMapping("/add")
    public HistoriqueReclamation addHistorique(@RequestBody HistoriqueReclamation historique) {
        return historiqueService.createHistorique(historique);
    }

    @GetMapping("/list")
    public List<HistoriqueReclamation> listHistoriques() {
        return historiqueService.getAllHistoriques();
    }

    @GetMapping("/{id}")
    public HistoriqueReclamation getHistorique(@PathVariable int id) {
        return historiqueService.getHistoriqueById(id);
    }

    @PutMapping("/{id}")
    public HistoriqueReclamation updateHistorique(@PathVariable int id, @RequestBody HistoriqueReclamation historique) {
        return historiqueService.updateHistorique(id, historique);
    }

    @DeleteMapping("/{id}")
    public void deleteHistorique(@PathVariable int id) {
        historiqueService.deleteHistorique(id);
    }
}
