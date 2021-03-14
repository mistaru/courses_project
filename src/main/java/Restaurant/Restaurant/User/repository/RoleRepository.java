package Restaurant.Restaurant.User.repository;

import Restaurant.Restaurant.User.Model.Role;
import Restaurant.Restaurant.User.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByName(String name);

}
