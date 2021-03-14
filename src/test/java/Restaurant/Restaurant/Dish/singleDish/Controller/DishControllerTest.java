package Restaurant.Restaurant.Dish.singleDish.Controller;


import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import Restaurant.Restaurant.Dish.singleDish.repository.DishRepository;
import Restaurant.Restaurant.Dish.singleDish.service.DishService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DishControllerTest {

    @Autowired
    private DishService dishService;

    @MockBean
    private DishRepository dishRepository;

    @Test
    public void getDishesTest() {
        when(dishRepository.findAll()).thenReturn(Stream
                .of(new Dish(), new Dish()).collect(Collectors.toList()));
        assertEquals(2, dishService.getAll().size());
    }

    @Test
    public void saveDishTest() {
        Dish dish = new Dish("Frytki",10);
        when(dishRepository.save(dish)).thenReturn(dish);
        assertEquals(dish, dishService.addDish("Frytki",10));
    }

    @Test
    public void removeDishTest() {
        Long id = new Long(1);
        dishService.removeDish(id);
        verify(dishRepository, times(1)).deleteById(id);
    }

}