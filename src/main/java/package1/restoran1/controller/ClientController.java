package package1.restoran1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import package1.restoran1.model.*;
import package1.restoran1.service.InvoiceService;
import package1.restoran1.service.MenuItemService;
import package1.restoran1.service.OrderService;
import package1.restoran1.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final MenuItemService menuItemService;
    private final OrderService orderService;
    private final UserService userService;
    private final InvoiceService invoiceService;

    public ClientController(MenuItemService menuItemService, OrderService orderService, UserService userService, InvoiceService invoiceService) {
        this.menuItemService = menuItemService;
        this.orderService = orderService;
        this.userService = userService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/menu")
    public String viewMenu(Model model) {
        List<MenuItem> menuItems = menuItemService.findAll();
        model.addAttribute("menuItems", menuItems);
        return "client/menu";
    }

    // Обработка создания заказа
    @PostMapping("/orders")
    public String makeOrder(@RequestParam Map<String, String> itemQuantities) {
        // Логирование перед началом обработки
        System.out.println("Received itemQuantities: " + itemQuantities);

        // Получаем клиента
        User client = userService.findByEmail("client@example.com").orElseThrow();

        List<OrderItem> orderItems = new ArrayList<>();
        itemQuantities.forEach((key, value) -> {
            // Пропускаем CSRF токен и параметры формы с другими именами
            if (key.startsWith("_csrf") || !key.startsWith("itemQuantities[")) {
                return;
            }

            try {
                // Извлекаем ID из ключа вида "itemQuantities[1]" - берем число внутри квадратных скобок
                String menuItemIdStr = key.substring("itemQuantities[".length(), key.length() - 1);
                Long menuItemId = Long.parseLong(menuItemIdStr);  // Преобразуем ID в Long
                Integer quantity = Integer.parseInt(value);  // Преобразуем количество в Integer

                // Если количество больше 0, добавляем в заказ
                if (quantity > 0) {
                    MenuItem menuItem = menuItemService.findById(menuItemId).orElseThrow();
                    orderItems.add(new OrderItem(menuItem, quantity));
                }
            } catch (NumberFormatException e) {
                // Логируем ошибку, если невозможно преобразовать ID или количество
                System.err.println("Ошибка преобразования параметров: " + e.getMessage());
            }
        });

        // Создаем новый заказ
        Order order = new Order(client, new ArrayList<>(), Order.OrderStatus.NEW);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order); // обязательно
            order.getItems().add(orderItem); // тоже обязательно
        }
        orderService.save(order);


        return "redirect:/client/orders";  // Перенаправляем на страницу с заказами
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        // Получаем клиента
        User client = userService.findByEmail("client@example.com").orElseThrow();
        // Получаем все заказы клиента
        List<Order> orders = orderService.findByClient(client);
        // Добавляем заказы в модель
        model.addAttribute("orders", orders);
        return "client/orders";  // Страница с заказами
    }

    @PostMapping("/pay/{orderId}")
    public String payInvoice(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId).orElseThrow();
        Invoice invoice = order.getInvoice();

        if (invoice != null && invoice.getStatus() == Invoice.PaymentStatus.UNPAID) {
            invoice.setStatus(Invoice.PaymentStatus.PAID);
            invoiceService.save(invoice);
        }

        return "redirect:/client/orders";
    }

    // Эндпоинт для получения актуального статуса заказа
    @GetMapping("/order-status/{orderId}")
    @ResponseBody
    public Map<String, String> getOrderStatus(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId).orElseThrow();
        Map<String, String> response = new HashMap<>();
        response.put("status", order.getStatus().toString()); // Возвращаем текущий статус заказа
        return response;
    }
}





