package tn.esprit.examen.nomPrenomClasseExamen.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Invoice;
import tn.esprit.examen.nomPrenomClasseExamen.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class InvoiceService implements IInvoiceService{
    InvoiceRepository invoiceRepository;

    @Override
    public Invoice addInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoice) {
        Optional<Invoice> existingInvoice = invoiceRepository.findById(id);
        if (existingInvoice.isPresent()) {
            Invoice updatedInvoice = existingInvoice.get();
            updatedInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
            updatedInvoice.setIssueDate(invoice.getIssueDate());
            updatedInvoice.setAmount(invoice.getAmount());
            updatedInvoice.setStatus(invoice.getStatus());
            updatedInvoice.setContract(invoice.getContract());
            updatedInvoice.setUser(invoice.getUser());
            return invoiceRepository.save(updatedInvoice);
        } else {
            throw new RuntimeException("Invoice not found with ID: " + id);
        }
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}

