package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Package;
import tn.esprit.examen.nomPrenomClasseExamen.repository.PackageRepository;

import javax.transaction.Transactional;
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
                    // Update all fields except id and createdAt
                    existingPackage.setType(packageEntity.getType());
                    existingPackage.setName(packageEntity.getName());
                    existingPackage.setDescription(packageEntity.getDescription());
                    existingPackage.setImageUrl(packageEntity.getImageUrl());
                    existingPackage.setDuration(packageEntity.getDuration());
                    existingPackage.setPrice(packageEntity.getPrice());
                    existingPackage.setUpdatedAt(new Date());
                    
                    // Keep the existing relationships if not provided in the update
                    if (packageEntity.getSubscriptions() != null) {
                        existingPackage.setSubscriptions(packageEntity.getSubscriptions());
                    }
                    if (packageEntity.getPartnershipOffers() != null) {
                        existingPackage.setPartnershipOffers(packageEntity.getPartnershipOffers());
                    }
                    
                    return packageRepository.save(existingPackage);
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public void deletePackage(Long id) {
        try {
            Package packageToDelete = packageRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Package not found with id: " + id, 1));

            // Clear relationships before deleting
            if (packageToDelete.getSubscriptions() != null) {
                packageToDelete.getSubscriptions().clear();
            }
            if (packageToDelete.getPartnershipOffers() != null) {
                packageToDelete.getPartnershipOffers().clear();
            }

            packageRepository.delete(packageToDelete);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete package. It has active subscriptions or partnership offers.", e);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Package not found with id: " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting package with id: " + id, e);
        }
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
