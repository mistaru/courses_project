package Restaurant.Restaurant.Restaurant.Controller;

import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import Restaurant.Restaurant.Restaurant.repository.RestaurantRepository;
import Restaurant.Restaurant.Restaurant.service.RestaurantService;
import Restaurant.Restaurant.Restaurant.service.RestaurantServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @Test
    public void getRestaurantsTest() {
        when(restaurantRepository.findAll()).thenReturn(Stream
                .of(new Restaurant("mcDolan", "ul.Morska 99"), new Restaurant("KFC", "ul.Ziemana 88")).collect(Collectors.toList()));
        assertEquals(2, restaurantService.getAll().size());
    }

    @Test
    public void saveRestaurantTest() {
        Restaurant restaurant = new Restaurant("Chińczyk", "ul.ooooa 99");
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        assertEquals(restaurant, restaurantService.addRestaurant(restaurant));
    }

    @Test
    public void removeRestaurantTest() {
        Long id = new Long(1);
        restaurantService.removeRestaurant(id);
        verify(restaurantRepository, times(1)).deleteById(id);
    }

}