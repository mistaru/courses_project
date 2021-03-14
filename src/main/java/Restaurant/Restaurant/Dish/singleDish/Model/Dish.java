package Restaurant.Restaurant.Dish.singleDish.Model;
import lombok.*;

import javax.persistence.*;

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
