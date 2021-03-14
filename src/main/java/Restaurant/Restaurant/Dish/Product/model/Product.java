package Restaurant.Restaurant.Dish.Product.model;

import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dish dish;

    private int quantity;

    private float price;

    public Product(Dish dish) {
        this.dish = dish;
        this.setQuantity(1);
        this.calcPrice();
    }

    public void calcPrice(){
        this.price = quantity * dish.getPrice();
    }

}
