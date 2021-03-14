package Restaurant.Restaurant.Dish.Product.repository;

import Restaurant.Restaurant.Dish.Product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
