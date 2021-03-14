package Restaurant.Restaurant.Restaurant.service;

import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import Restaurant.Restaurant.Restaurant.repository.RestaurantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantServiceImplTest {

    @MockBean
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;



    @Test
    public void addRestaurant() {
        Restaurant restaurant = new Restaurant("Chińczyk","ul.ooooa 99");
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        assertEquals(restaurant, restaurantRepository.save(restaurant));
    }

    @Test
    public void getAll() {
        when(restaurantRepository.findAll()).thenReturn(Stream
                .of(new Restaurant("mcDolan","ul.Morska 99"), new Restaurant("KFC","ul.Ziemana 88")).collect(Collectors.toList()));
        assertEquals(2, restaurantRepository.findAll().size());
    }

    @Test
    public void getById() {
        Restaurant restaurant1  = new Restaurant("mcDonald","ul.Morska 99");
        restaurant1.setId(new Long(1));

        when(restaurantRepository.findAll()).thenReturn(Stream
                .of(restaurant1).collect(Collectors.toList()));

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(new Long(1));
        if(optionalRestaurant.isPresent()) {
            assertEquals(restaurant1, optionalRestaurant.get());
        }

    }

    @Test
    public void editRestaurant() {
        Restaurant restaurant1  = new Restaurant("mcDonald","ul.Morska 99");
        restaurant1.setId(new Long(1));

        Restaurant restToUpdate = new Restaurant("Subway", "ul. ddd");

        restaurant1.setName(restToUpdate.getName());
        restaurant1.setAddress(restToUpdate.getAddress());
        when(restaurantRepository.save(restaurant1)).thenReturn(restaurant1);
        assertEquals(restaurantRepository.save(restaurant1).getName(),restToUpdate.getName());
    }

    @Test
    public void removeRestaurant() {
        Long id = new Long(1);
        restaurantService.removeRestaurant(id);
        verify(restaurantRepository, times(1)).deleteById(id);
    }


}