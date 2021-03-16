package Restaurant.Restaurant.NewPart.model;

import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import Restaurant.Restaurant.NewPart.enume.EnumTable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "reserv")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserv_id")
    private int id;

    @Column(name = "table_number")
    @Enumerated(EnumType.STRING)
    private EnumTable table;

    @ManyToOne
    @JoinColumn(name = "dishes_id", nullable = false)
    private Dish dish;

    @Column
    private int count;

    @Column
    private LocalDateTime date;

    public Report() {
    }

    public Report(EnumTable table, Dish dish, Integer count) {
        this.table = table;
        this.dish = dish;
        this.count = count;
    }

    public Report(EnumTable table, Dish dish, int count, LocalDateTime date) {
        this.table = table;
        this.dish = dish;
        this.count = count;
        this.date = date;
    }
}
