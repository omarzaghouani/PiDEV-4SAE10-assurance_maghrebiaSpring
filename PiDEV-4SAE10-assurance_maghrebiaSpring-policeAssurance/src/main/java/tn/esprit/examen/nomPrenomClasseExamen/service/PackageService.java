package tn.esprit.examen.nomPrenomClasseExamen.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Package;
import tn.esprit.examen.nomPrenomClasseExamen.repository.PackageRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService implements IPackageService {

    private final PackageRepository packageRepository;

    @Override
    public Package addPackage(Package p) {
        p.setCreatedAt(new Date());
        p.setUpdatedAt(new Date());
        return packageRepository.save(p);
    }

    @Override
    public Package updatePackage(Long id, Package packageEntity) {
        return packageRepository.findById(id)
                .map(existingPackage -> {
                    existingPackage.setType(packageEntity.getType());
                    existingPackage.setName(packageEntity.getName());
                    existingPackage.setDescription(packageEntity.getDescription());
                    existingPackage.setImageUrl(packageEntity.getImageUrl());
                    existingPackage.setDuration(packageEntity.getDuration());
                    existingPackage.setPrice(packageEntity.getPrice());
                    existingPackage.setUpdatedAt(new Date());

                    return packageRepository.save(existingPackage);
                })
                .orElse(null);
    }


    @Override
    @Transactional
    public void deletePackage(Long id) {
        Package packageToDelete = packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found with id: " + id));

        // Clear relationships to avoid constraint violations
        if (packageToDelete.getSubscriptions() != null) {
            packageToDelete.getSubscriptions().clear();
        }
        if (packageToDelete.getPartnershipOffers() != null) {
            packageToDelete.getPartnershipOffers().clear();
        }

        packageRepository.delete(packageToDelete);
    }

    @Override
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    @Override
    public Package getPackageById(Long id) {
        return packageRepository.findById(id).orElse(null);
    }
}
