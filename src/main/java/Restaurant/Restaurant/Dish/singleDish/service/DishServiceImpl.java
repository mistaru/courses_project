package Restaurant.Restaurant.Dish.singleDish.service;

import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import Restaurant.Restaurant.Dish.singleDish.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Override
    public Dish addDish(String nazwa, Integer cena) {
        Dish dish = new Dish();
        dish.setName(nazwa);
        dish.setPrice(cena);
        return dishRepository.save(dish);
    }

    @Override
    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    @Override
    public Dish getByName(String dishName) {
        Optional<Dish> optionalDish = dishRepository.findByName(dishName);
        return optionalDish.orElse(null);

    }

    @Override
    public Optional<Dish> getById(Long id) {
        return dishRepository.findById(id);
    }

    @Override
    public void editDish(Long id, String name, Integer price) {
        Dish dish = dishRepository.getOne(id);

        if (!dish.getName().equals(name)) {
            if (isNameUsed(name)) {
                throw new IllegalStateException();
            }
        }
        dish.setName(name);
        dish.setPrice(price);
        dishRepository.save(dish);
    }

    @Override
    public void removeDish(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public boolean isNameUsed(String name) {
        Optional<Dish> n = dishRepository.findByName(name);
        return n.isPresent();
    }
}

