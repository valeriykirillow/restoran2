package package1.restoran1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import package1.restoran1.model.Invoice;
import package1.restoran1.model.Order;
import package1.restoran1.model.MenuItem;
import package1.restoran1.service.InvoiceService;
import package1.restoran1.service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderService orderService;
    private final InvoiceService invoiceService;

    public AdminController(OrderService orderService, InvoiceService invoiceService) {
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/orders")
    public String viewAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders";
    }

    @PostMapping("/confirm/{orderId}")
    public String confirmOrder(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId).orElseThrow();
        if (order.getStatus() == Order.OrderStatus.NEW) {
            order.setStatus(Order.OrderStatus.IN_PROGRESS);
            orderService.save(order);
        }
        return "redirect:/admin/orders";
    }

    @PostMapping("/complete/{orderId}")
    public String completeOrder(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId).orElseThrow();
        order.setStatus(Order.OrderStatus.COMPLETED);
        orderService.save(order);

        // Вычисляем общую сумму заказа
        double total = order.getItems().stream()
                .mapToDouble(orderItem -> orderItem.getItem().getPrice() * orderItem.getQuantity()) // В зависимости от поля, которое содержит MenuItem
                .sum();

        Invoice invoice = new Invoice(order, total, Invoice.PaymentStatus.UNPAID);
        invoiceService.save(invoice);

        return "redirect:/admin/orders";
    }

    @GetMapping("/orders-status-check")
    @ResponseBody
    public Map<String, Boolean> checkOrderStatuses() {
        boolean shouldReload = false;

        List<Order> allOrders = orderService.findAll();
        for (Order order : allOrders) {
            // Если заказ COMPLETED и счет оплачен — флагим как "нужна перезагрузка"
            if (order.getStatus() == Order.OrderStatus.COMPLETED &&
                    order.getInvoice() != null &&
                    order.getInvoice().getStatus() == Invoice.PaymentStatus.PAID) {
                shouldReload = true;
                break;
            }
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("reload", shouldReload);
        return response;
    }
}
