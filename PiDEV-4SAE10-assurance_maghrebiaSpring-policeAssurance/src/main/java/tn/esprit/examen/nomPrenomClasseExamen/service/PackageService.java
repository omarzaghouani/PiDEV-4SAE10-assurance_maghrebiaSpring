package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Package;
import tn.esprit.examen.nomPrenomClasseExamen.repository.PackageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok handles constructor injection
public class PackageService implements IPackageService {

    private final PackageRepository packageRepository; // ✅ Fixed missing repository injection

    @Override
    public Package addPackage(Package p) {
        return packageRepository.save(p);
    }

    @Override
    public Package updatePackage(Long id, Package packageEntity) {
        return packageRepository.findById(id)
                .map(existingPackage -> {
                    existingPackage.setName(packageEntity.getName());
                    existingPackage.setDescription(packageEntity.getDescription());
                    existingPackage.setPrice(packageEntity.getPrice());
                    return packageRepository.save(existingPackage);
                })
                .orElse(null);
    }

    @Override
    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
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
