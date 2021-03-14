package Restaurant.Restaurant.User.Model;

import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import lombok.*;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userr")
public class User  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;

    @ManyToOne(cascade=CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name="Restaurant_id")
    private Restaurant restaurant;

    @ManyToMany(cascade=CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Role> roles;


    public User(String firstName, String lastName, String username, String password,Restaurant restaurant) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.restaurant = restaurant;

    }

    public User(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.id = user.getId();
        this.restaurant = user.getRestaurant();

    }




}
