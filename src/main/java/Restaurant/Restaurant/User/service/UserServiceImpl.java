package Restaurant.Restaurant.User.service;

import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import Restaurant.Restaurant.Restaurant.repository.RestaurantRepository;
import Restaurant.Restaurant.User.Model.Role;
import Restaurant.Restaurant.User.Model.User;
import Restaurant.Restaurant.User.repository.RoleRepository;
import Restaurant.Restaurant.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final RoleRepository roleRepository;

    @Override
    public void addUser(String firstname, String lastname, String username, String password, String restaurant) {
        User user = new User();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setUsername(username);
        user.setPassword(password);

        Optional<Restaurant> tempOptRestaurant = restaurantRepository.findByName(restaurant);
        if (tempOptRestaurant.isPresent()) {
            user.setRestaurant(tempOptRestaurant.get());
        }

        List<Role> tempList = new ArrayList<Role>();
        tempList.add(roleRepository.findByName("USER"));
        user.setRoles(tempList);
        userRepository.save(user);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);

    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    public boolean isUserExist(User user) {
        return userRepository.existsById(user.getId());
    }

    @Override
    public void editUser(Long id, String firstname, String lastname, String username, String password, String restaurant) {
        User user = userRepository.getOne(id);

        //sprawdza czy nazwa isnieje, tylko jeśli jest inna niż edytowana
        if (!user.getUsername().equals(username)) {
            if (isUsernameUsed(username)) {
                throw new IllegalStateException();
            }
        }
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setUsername(username);
        user.setPassword(password);

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByName(restaurant);
        if (optionalRestaurant.isPresent()) {
            user.setRestaurant(optionalRestaurant.get());
        }
        userRepository.save(user);
    }

    @Override
    public void editUser(User user) {
        userRepository.deleteById(user.getId());
        userRepository.save(user);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isUsernameUsed(String username) {
        Optional<User> n = userRepository.findByUsername(username);
        if (n.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
