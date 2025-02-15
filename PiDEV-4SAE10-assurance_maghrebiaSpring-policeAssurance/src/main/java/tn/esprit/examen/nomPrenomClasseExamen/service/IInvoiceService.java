package tn.esprit.examen.nomPrenomClasseExamen.service;


import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Invoice;

import java.util.List;
import java.util.Optional;

public interface IInvoiceService {
    // Create or update invoice
    Invoice addInvoice(Invoice invoice);

    // Get all invoices
    List<Invoice> getAllInvoices();

    // Get invoice by ID
    Optional<Invoice> getInvoiceById(Long id);
    Invoice updateInvoice(Long id, Invoice invoice);
    // Delete invoice
    void deleteInvoice(Long id);
}
