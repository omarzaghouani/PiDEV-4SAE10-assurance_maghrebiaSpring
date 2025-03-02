package tn.esprit.examen.nomPrenomClasseExamen.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Client;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Contract;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Invoice;
import tn.esprit.examen.nomPrenomClasseExamen.repository.ClientRepository;
import tn.esprit.examen.nomPrenomClasseExamen.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class InvoiceService implements IInvoiceService{
 @Autowired
    InvoiceRepository invoiceRepository;
 @Autowired
    ClientRepository clientRepository;
    @Autowired
    private ContractService contractService;

    @Override
    public Invoice addInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }



    @Override
    public Invoice CreateInvoiceByname(Invoice invoice, Long idClient){
        Client client = clientRepository.findById(idClient).orElse(null);
        invoice.setClient(client);
        invoiceRepository.save(invoice);
        return invoice;
    }

    @Override
    public Invoice createInvoiceByClientId(Invoice invoice, Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if (client != null) {
            invoice.setClient(client);
            return invoiceRepository.save(invoice);
        } else {
            throw new RuntimeException("Client not found with ID: " + idClient);
        }
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
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + id));
        existingInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
        existingInvoice.setIssueDate(invoice.getIssueDate());
        existingInvoice.setAmount(invoice.getAmount());
        existingInvoice.setStatus(invoice.getStatus());
        existingInvoice.setContract(invoice.getContract());
        existingInvoice.setClient(invoice.getClient());
        return invoiceRepository.save(existingInvoice);
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}

