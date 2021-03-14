package Restaurant.Restaurant.Order.service;

import Restaurant.Restaurant.Order.Model.OrderModel;
import Restaurant.Restaurant.Order.repository.OrderRepository;
import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    @Override
    public void addOrder(OrderModel order) {
        repository.save(order);
    }

    @Override
    public List<OrderModel> getAll() { return repository.findAll(); }

    @Override
    public List<OrderModel> getRestaurantOrders(Restaurant restaurant) {
        return repository.findOrderByRestaurant(restaurant.getId());
    }

    @Override
    public Optional<OrderModel> getOrderById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void finish(Long id) {
        repository.finish(id);
    }
}
