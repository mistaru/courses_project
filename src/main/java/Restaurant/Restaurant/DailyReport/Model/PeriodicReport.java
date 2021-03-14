package Restaurant.Restaurant.DailyReport.Model;


import Restaurant.Restaurant.Dish.Product.model.Product;
import Restaurant.Restaurant.Order.Model.OrderModel;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PeriodicReport {

    private List<DailyReport> listDailyReports;

    private LocalDate begin;

    private LocalDate end;

    private HashMap<String, Float> dish_price;

    private HashMap<String, Integer> dish_quantity;

    private float profits;

    public PeriodicReport(LocalDate begin, LocalDate end) {
        this.begin = begin;
        this.end = end;
    }

    public void update(){
       this.updateDish_price();
       this.updateDish_quantity();
       this.updateProfits();
    }

    private void updateDish_price() {
        if (dish_price == null) {
            dish_price = new HashMap<>();
        }
        for(DailyReport dailyReport : listDailyReports){
            for(OrderModel order : dailyReport.getOrders()){
                for(Product prodakt : order.getProducts()){
                    if (dish_price.containsKey(prodakt.getDish().getName())) {
                        float oldValue = dish_price.get(prodakt.getDish().getName());
                        oldValue += prodakt.getPrice();
                        dish_price.put(prodakt.getDish().getName(), oldValue);
                    }
                    else {
                        dish_price.put(prodakt.getDish().getName(), prodakt.getPrice());
                    }
                }
            }
        }
    }

    private void updateDish_quantity() {
        if (dish_quantity == null) {
            dish_quantity = new HashMap<>();
        }
        for(DailyReport dailyReport : listDailyReports){
            for(OrderModel order : dailyReport.getOrders()){
                for(Product prodakt : order.getProducts()){
                    if (dish_quantity.containsKey(prodakt.getDish().getName())) {
                        int oldValue = dish_quantity.get(prodakt.getDish().getName());
                        oldValue += prodakt.getQuantity();
                        dish_quantity.put(prodakt.getDish().getName(), oldValue);
                    }
                    else {
                        dish_quantity.put(prodakt.getDish().getName(), prodakt.getQuantity());
                    }
                }
            }
        }
    }

    private void updateProfits() {
        for(DailyReport raporty : this.listDailyReports){
            this.profits += raporty.getProfits();
        }
    }
}
