package Restaurant.Restaurant.Order.Controller;

import Restaurant.Restaurant.Order.Model.OrderModel;
import Restaurant.Restaurant.Order.repository.OrderRepository;
import Restaurant.Restaurant.Order.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {


    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void addOrderTest() {
        OrderModel orderModel = new OrderModel();
        when(orderRepository.save(orderModel)).thenReturn(orderModel);
        assertEquals(orderModel, orderRepository.save(orderModel));
    }

}