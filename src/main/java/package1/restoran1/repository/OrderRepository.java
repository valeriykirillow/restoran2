package package1.restoran1.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import package1.restoran1.model.Order;
import package1.restoran1.model.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"items", "items.item", "invoice"})
    List<Order> findByClient(User client);
}
