package package1.restoran1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import package1.restoran1.model.Invoice;
import package1.restoran1.model.Order;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByOrder(Order order);
}
