package package1.restoran1.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

//Представляет счет за заказ, содержащий сумму, дату создания и статус оплаты.
//Связан один-к-одному с заказом (Order) и хранит статус PAID или UNPAID.
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order order;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime createdDate;

    public enum PaymentStatus {
        UNPAID, PAID
    }

    // Конструкторы, геттеры и сеттеры
    public Invoice() {
        this.createdDate = LocalDateTime.now(); // Инициализация времени создания счёта
    }

    public Invoice(Order order, double amount, PaymentStatus status) {
        this.order = order;
        this.amount = amount;
        this.status = status;
        this.createdDate = LocalDateTime.now();
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}

