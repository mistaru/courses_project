package Restaurant.Restaurant.NewPart.model;

import Restaurant.Restaurant.NewPart.enume.EnumUnit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "ingredients")
public class Ingredients implements Comparable<Ingredients> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredients_id")
    private Long id;

    @Column(unique = true)
    @Size(min = 1, max = 100)
    private String productName;

    @Column
    @Enumerated(EnumType.STRING)
    private EnumUnit enumUnit;

    @Column
    private int price;

    @OneToMany(mappedBy = "ingredients")
    private Set<Composition> compositionI;

    public Ingredients() {
    }

    @Override
    public int compareTo(Ingredients o) {
        return productName.compareTo(o.productName);
    }

}
