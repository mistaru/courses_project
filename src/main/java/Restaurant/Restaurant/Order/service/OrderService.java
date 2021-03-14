package Restaurant.Restaurant.Order.service;

import Restaurant.Restaurant.Order.Model.OrderModel;
import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {

    void addOrder(OrderModel order);

    List<OrderModel> getAll();

    List<OrderModel> getRestaurantOrders(Restaurant restaurant);

    Optional<OrderModel> getOrderById(Long id);

    void finish(Long id);

}
