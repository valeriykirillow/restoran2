package package1.restoran1.model;

import jakarta.persistence.*;

//Отображает отдельную позицию в заказе: какое блюдо и в каком количестве.
//Связан с конкретным заказом (Order) и блюдом (MenuItem).
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")  // Убедитесь, что имя столбца указано верно
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")  // Связь с MenuItem
    private MenuItem item;

    private int quantity;

    // Конструкторы, геттеры и сеттеры
    public OrderItem() {}

    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() {
        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}


