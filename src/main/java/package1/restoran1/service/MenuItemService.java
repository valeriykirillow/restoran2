package package1.restoran1.service;

import org.springframework.stereotype.Service;
import package1.restoran1.model.MenuItem;
import package1.restoran1.repository.MenuItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItem> findById(Long id) {
        return menuItemRepository.findById(id);
    }

    public MenuItem save(MenuItem item) {
        return menuItemRepository.save(item);
    }
}
