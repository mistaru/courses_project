package Restaurant.Restaurant.NewPart.dto;

import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportDTO {
    private Dish dish;
    private Integer price;
    private Integer count;
    private Integer sum;
}
