package Restaurant.Restaurant.User.repository;

import Restaurant.Restaurant.User.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);
}
