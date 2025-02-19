package tn.esprit.examen.nomPrenomClasseExamen.services;

import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.HistoriqueReclamation;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.HistoriqueReclamationRepository;


import java.util.List;
import java.util.Optional;

@Service
public class HistoriqueReclamationServiceImpl implements HistoriqueReclamationService {

    private final HistoriqueReclamationRepository historiqueRepository;

    public HistoriqueReclamationServiceImpl(HistoriqueReclamationRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    @Override
    public HistoriqueReclamation createHistorique(HistoriqueReclamation historique) {
        return historiqueRepository.save(historique);
    }

    @Override
    public List<HistoriqueReclamation> getAllHistoriques() {
        return historiqueRepository.findAll();
    }

    @Override
    public HistoriqueReclamation getHistoriqueById(int id) {
        return historiqueRepository.findById(id).orElse(null);
    }

    @Override
    public HistoriqueReclamation updateHistorique(int id, HistoriqueReclamation historique) {
        Optional<HistoriqueReclamation> existingHistorique = historiqueRepository.findById(id);
        if (existingHistorique.isPresent()) {
            historique.setId(id);
            return historiqueRepository.save(historique);
        }
        return null;
    }

    @Override
    public void deleteHistorique(int id) {
        historiqueRepository.deleteById(id);
    }
}
