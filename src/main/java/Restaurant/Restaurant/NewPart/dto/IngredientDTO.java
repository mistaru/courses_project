package Restaurant.Restaurant.NewPart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientDTO {
    private String name;
    private Integer count;
}
