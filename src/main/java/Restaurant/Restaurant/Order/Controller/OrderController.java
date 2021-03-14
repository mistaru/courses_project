package Restaurant.Restaurant.Order.Controller;

import Restaurant.Restaurant.Order.Model.OrderModel;
import Restaurant.Restaurant.Order.service.OrderServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public void addOrder(@RequestBody OrderModel order){
        orderService.addOrder(order);
    }
}
