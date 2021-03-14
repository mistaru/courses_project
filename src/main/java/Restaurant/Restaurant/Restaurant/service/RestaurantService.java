package Restaurant.Restaurant.Restaurant.service;

import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RestaurantService {

    void addRestaurant(String name, String address);

    List<Restaurant> getAll();

    void editRestaurant(Long id, String name, String address);

    void removeRestaurant(Long id);

    Optional<Restaurant> getById(Long id);

    boolean isNameUsed(String name);

    Restaurant addRestaurant(Restaurant restaurant);

    Optional<Restaurant> getByName(String restauracja);
}
