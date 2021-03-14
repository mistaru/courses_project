package Restaurant.Restaurant.Dish.singleDish.Model;


import Restaurant.Restaurant.Order.Model.OrderModel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dish")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float price;


    public Dish(String name, float price) {
        this.name = name;
        this.price = price;
    }
}
