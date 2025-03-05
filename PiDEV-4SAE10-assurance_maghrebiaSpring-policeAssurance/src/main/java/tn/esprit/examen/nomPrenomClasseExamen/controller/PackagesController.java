package tn.esprit.examen.nomPrenomClasseExamen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Package;
import tn.esprit.examen.nomPrenomClasseExamen.service.IPackageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // ✅ Ensure it allows Angular frontend
public class PackagesController {

    private final IPackageService packageService;

    @PostMapping("/add")
    public ResponseEntity<?> addPackage(@RequestBody Package packageEntity) {
        try {
            // Call the service to save the package
            Package savedPackage = packageService.addPackage(packageEntity);
            return ResponseEntity.ok(savedPackage); // Return the saved package
        } catch (Exception e) {
            // Catch and return a user-friendly error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error adding package: " + e.getMessage());
        }
    }


    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Package> updatePackage(@PathVariable Long id, @RequestBody Package packageEntity) {
        Package updatedPackage = packageService.updatePackage(id, packageEntity);
        return updatedPackage != null ? ResponseEntity.ok(updatedPackage) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Long id) {
        try {
            packageService.deletePackage(id);
            return ResponseEntity.ok().body(Map.of("message", "Package deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Package>> getAllPackages() {
        List<Package> packages = packageService.getAllPackages();
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable Long id) {
        Package packageEntity = packageService.getPackageById(id);
        return packageEntity != null ? ResponseEntity.ok(packageEntity) : ResponseEntity.notFound().build();
    }

    //Advanced Functions Work Here :

    @PutMapping("/{id}/reduce-price")
    public ResponseEntity<Package> reducePrice(@PathVariable Long id, @RequestParam double percentage) {
        Package updatedPackage = packageService.applyPriceReduction(id, percentage);
        return updatedPackage != null ? ResponseEntity.ok(updatedPackage) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/reset-price")
    public ResponseEntity<Package> resetPrice(@PathVariable Long id) {
        Package updatedPackage = packageService.resetPrice(id);
        return updatedPackage != null ? ResponseEntity.ok(updatedPackage) : ResponseEntity.notFound().build();
    }

}
