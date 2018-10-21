package pl.training.cloud.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.training.cloud.users.model.User;

public interface UsersRepository extends JpaRepository<User, Long> {
}
