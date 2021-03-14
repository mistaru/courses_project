package Restaurant.Restaurant.Dish.singleDish.service;


import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DishService {


    public List<Dish> getAll();

    public Dish addDish(String nazwa, float cena);

    public Dish getByName(String Dishname);

    public void editDish(Long id, String name,float price);

    public void removeDish(Long id);

    public Optional<Dish> getById(Long id);

    public boolean isNameUsed(String name);
}
