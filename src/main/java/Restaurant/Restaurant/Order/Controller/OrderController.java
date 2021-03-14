package Restaurant.Restaurant.Order.Controller;

import Restaurant.Restaurant.Order.Model.OrderModel;
import Restaurant.Restaurant.Order.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @PostMapping("/add")
    public void addOrder(@RequestBody OrderModel order){
        orderService.addOrder(order);
    }
}
