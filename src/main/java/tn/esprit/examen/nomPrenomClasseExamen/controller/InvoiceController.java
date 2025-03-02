package tn.esprit.examen.nomPrenomClasseExamen.controller;


import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Invoice;
import tn.esprit.examen.nomPrenomClasseExamen.service.IInvoiceService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin (origins = "http://localhost:4200")
@RequestMapping("/api/invoices")
public class InvoiceController {
    IInvoiceService invoiceService;
    @Autowired
    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
    @PostMapping("/add")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice ) {
        Invoice createdInvoice = invoiceService.addInvoice(invoice);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @PostMapping("/add/{idClient}")
    public ResponseEntity<Invoice> createInvoiceByClientId(@RequestBody Invoice invoice, @PathVariable("idClient") Long idClient) {
        Invoice createdInvoice = invoiceService.createInvoiceByClientId(invoice, idClient);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        return invoice.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        try {
            Invoice updatedInvoice = invoiceService.updateInvoice(id, invoice);
            return new ResponseEntity<>(updatedInvoice, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
