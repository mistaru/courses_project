package Restaurant.Restaurant.Restaurant.service;

import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import Restaurant.Restaurant.Restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public void addRestaurant(String name, String address) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurantRepository.save(restaurant);
    }
    public Restaurant addRestaurant(Restaurant restaurant){
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    @Override
    public Optional<Restaurant> getByName(String restauracja) {
        return restaurantRepository.findByName(restauracja);

    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }


    @Override
    public Optional<Restaurant> getById(Long id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public void editRestaurant(Long id, String name,String address) throws IllegalStateException
    {
        Restaurant restaurant = restaurantRepository.getOne(id);

        //sprawdza czy nazwa isnieje, tylko jeśli jest inna niż edytowana
        if(!restaurant.getName().equals(name)){
            if(isNameUsed(name)){
                throw new IllegalStateException();
            }
        }
            restaurant.setName(name);
            restaurant.setAddress(address);
            restaurantRepository.save(restaurant);
    }

    @Override
    public void removeRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public boolean isNameUsed(String name) {
        Optional<Restaurant> n =  restaurantRepository.findByName(name);
        if(n.isPresent()){
            return true;
        }
        else{
            return false;
        }

    }

}
