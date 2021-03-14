package Restaurant.Restaurant.Order.Model;

import Restaurant.Restaurant.DailyReport.Model.DailyReport;
import Restaurant.Restaurant.Dish.Product.model.Product;
import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import Restaurant.Restaurant.User.Model.User;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orderr")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="Restaurant_id")
    private Restaurant restaurant;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="User_id")
    private User user;

    @ManyToMany(cascade = { CascadeType.ALL })
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "daily_report_id")
    private DailyReport dailyReport;


    private String status;

    private float price;

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void removeDish(Product product){
        this.products.remove(product);
    }


}
