package Restaurant.Restaurant.Order.service;

import Restaurant.Restaurant.Order.Model.OrderModel;
import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {

    public void addOrder(OrderModel order);

    public List<OrderModel> getAll();

    public List<OrderModel> getRestaurantOrders(Restaurant restaurant);

    public Optional<OrderModel> getOrderById(Long id);

    public void finish(Long id);




}
