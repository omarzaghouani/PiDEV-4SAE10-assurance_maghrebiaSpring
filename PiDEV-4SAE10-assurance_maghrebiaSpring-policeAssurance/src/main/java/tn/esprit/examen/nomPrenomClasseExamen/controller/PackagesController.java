package tn.esprit.examen.nomPrenomClasseExamen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Package;
import tn.esprit.examen.nomPrenomClasseExamen.service.IPackageService;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor

public class PackagesController {

    private final IPackageService packageService;

    @PostMapping
    public ResponseEntity<Package> addPackage(@RequestBody Package packageEntity) {
        Package savedPackage = packageService.addPackage(packageEntity);
        return ResponseEntity.ok(savedPackage);
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<Package> updatePackage(@PathVariable Long id, @RequestBody Package packageEntity) {
        Package updatedPackage = packageService.updatePackage(id, packageEntity);
        return updatedPackage != null ? ResponseEntity.ok(updatedPackage) : ResponseEntity.notFound().build();
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.noContent().build();
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
