package Restaurant.Restaurant.Order.repository;

import Restaurant.Restaurant.Order.Model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    @Query(value = "SELECT * from orderr o where o.restaurant_id = :id", nativeQuery = true)
    List<OrderModel> findOrderByRestaurant(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orderr  SET status = 'zakonczone' WHERE id = :id", nativeQuery = true)
    int finish(@Param("id") Long id);

}
