package package1.restoran1.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import package1.restoran1.model.MenuItem;
import package1.restoran1.model.User;
import package1.restoran1.repository.MenuItemRepository;
import package1.restoran1.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    public DataInitializer(MenuItemRepository menuItemRepository, UserRepository userRepository) {
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Инициализация меню
        menuItemRepository.save(new MenuItem("Суп", "Тёплый овощной суп", 150.0));
        menuItemRepository.save(new MenuItem("Пицца", "Пицца с пепперони", 350.0));
        menuItemRepository.save(new MenuItem("Салат", "Свежий овощной салат", 200.0));
        menuItemRepository.save(new MenuItem("Гарнир", "Картофельное пюре", 100.0));
        menuItemRepository.save(new MenuItem("Напиток", "Лимонад", 50.0));


        // Инициализация пользователей
        User admin = new User("Администратор", "admin@example.com", "admin", "ADMIN");
        User client = new User("Клиент", "client@example.com", "client", "USER");


        userRepository.save(admin);
        userRepository.save(client);

        System.out.println("База данных инициализирована с начальными данными.");
    }
}
