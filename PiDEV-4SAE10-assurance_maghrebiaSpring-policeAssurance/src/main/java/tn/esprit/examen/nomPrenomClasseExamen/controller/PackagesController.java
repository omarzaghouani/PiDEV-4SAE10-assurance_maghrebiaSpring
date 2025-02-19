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

    @PostMapping
    public ResponseEntity<Package> addPackage(@RequestBody Package packageEntity) {
        Package savedPackage = packageService.addPackage(packageEntity);
        return ResponseEntity.ok(savedPackage);
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
}
