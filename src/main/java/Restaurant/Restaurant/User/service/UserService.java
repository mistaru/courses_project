package Restaurant.Restaurant.User.service;

import Restaurant.Restaurant.User.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addUser(String firstname, String lastname, String username, String password, String restaurant);

    User addUser(User user);

    List<User> getAll();

    Optional<User> getByUsername(String username);

    Optional<User> getById(Long id);

    boolean isUserExist(User user);

    void editUser(User user);

    void editUser(Long id, String firstname, String lastname, String username, String password, String restaurant);

    void removeUser(Long id);

    boolean isUsernameUsed(String username);

}
