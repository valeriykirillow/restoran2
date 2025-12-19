package package1.restoran1.service;

import org.springframework.stereotype.Service;
import package1.restoran1.model.Order;
import package1.restoran1.model.User;
import package1.restoran1.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findByClient(User client) {
        return orderRepository.findByClient(client);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}
