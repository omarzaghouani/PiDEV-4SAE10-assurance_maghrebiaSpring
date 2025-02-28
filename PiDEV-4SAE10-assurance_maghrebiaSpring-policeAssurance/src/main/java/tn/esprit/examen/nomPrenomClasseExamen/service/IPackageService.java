package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Package;

import java.util.List;

public interface IPackageService {
    Package addPackage(Package p);
    Package updatePackage(Long id, Package packageEntity);
    void deletePackage(Long id);
    List<Package> getAllPackages();
    Package getPackageById(Long id);
    //a.f
    public Package applyPriceReduction(Long id, double reductionPercentage);
    public Package resetPrice(Long id);
}
