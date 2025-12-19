package package1.restoran1.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//Представляет заказ, сделанный клиентом, и содержит список блюд и текущий статус.
//Связан с пользователем (клиентом), позициями заказа (OrderItem) и счетом (Invoice).
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User client;  // Связь с клиентом

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();  // Список позиций (OrderItem)

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Invoice invoice;  // Связь с счетом (Invoice)

    public enum OrderStatus {
        NEW, IN_PROGRESS, COMPLETED
    }

    // Конструкторы, геттеры и сеттеры
    public Order() {}

    // Конструктор без Invoice
    public Order(User client, List<OrderItem> items, OrderStatus status) {
        this.client = client;
        this.items = items;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    // Метод для добавления OrderItem
    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
        orderItem.setOrder(this);  // Устанавливаем заказ в OrderItem
    }
}

