package Restaurant.Restaurant.Dish.singleDish.service;


import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import Restaurant.Restaurant.Dish.singleDish.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService{


    @Autowired
    DishRepository dishRepository;

    @Override
    public Dish addDish(String nazwa, float cena) {
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
    public Dish getByName(String dishname){

        Optional<Dish> optionalDish = dishRepository.findByName(dishname);
        if(optionalDish.isPresent()) {
            return optionalDish.get();
        }
        else {
            return null;
        }

    }

    @Override
    public Optional<Dish> getById(Long id) {
        return dishRepository.findById(id);
    }


    @Override
    public void editDish(Long id, String name, float price) {
        Dish dish = dishRepository.getOne(id);

        //sprawdza czy nazwa isnieje, tylko jeśli jest inna niż edytowana
        if(!dish.getName().equals(name)){
            if(isNameUsed(name)){
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
            if(n.isPresent()){
                return true;
            }
            else{
                return false;
            }

        }
    }

