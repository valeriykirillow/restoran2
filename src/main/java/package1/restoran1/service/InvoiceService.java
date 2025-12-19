package package1.restoran1.service;

import org.springframework.stereotype.Service;
import package1.restoran1.model.Invoice;
import package1.restoran1.model.Order;
import package1.restoran1.repository.InvoiceRepository;

import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> findByOrder(Order order) {
        return invoiceRepository.findByOrder(order);
    }

    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }
}
