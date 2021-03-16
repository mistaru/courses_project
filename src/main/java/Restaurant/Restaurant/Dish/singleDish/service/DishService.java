package Restaurant.Restaurant.Dish.singleDish.service;

import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DishService {
    List<Dish> getAll();
    Dish addDish(String nazwa, Integer cena);
    Dish getByName(String Dishname);
    void editDish(Long id, String name,Integer price);
    void removeDish(Long id);
    Optional<Dish> getById(Long id);
    boolean isNameUsed(String name);
}
