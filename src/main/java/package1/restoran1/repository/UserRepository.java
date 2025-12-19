package package1.restoran1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import package1.restoran1.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
