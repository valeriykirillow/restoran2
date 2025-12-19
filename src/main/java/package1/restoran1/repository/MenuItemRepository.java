package package1.restoran1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import package1.restoran1.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
