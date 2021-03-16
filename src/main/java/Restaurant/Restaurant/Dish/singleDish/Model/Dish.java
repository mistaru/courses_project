package Restaurant.Restaurant.Dish.singleDish.Model;
import Restaurant.Restaurant.NewPart.model.Composition;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dish")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;

    @OneToMany(mappedBy = "dish")
    private Set<Composition> compositions;

    public Dish(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public Dish(Long id, String name, Integer price, Set<Composition> compositions) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.compositions = compositions;
    }
}
