package Restaurant.Restaurant.DailyReport.Controller;

import Restaurant.Restaurant.DailyReport.Model.DailyReport;
import Restaurant.Restaurant.DailyReport.Service.DailyReportService;
import Restaurant.Restaurant.Dish.Product.model.Product;
import Restaurant.Restaurant.NewPart.dto.IngredientDTO;
import Restaurant.Restaurant.NewPart.dto.OverallDTO;
import Restaurant.Restaurant.NewPart.model.Composition;
import Restaurant.Restaurant.NewPart.model.Ingredients;
import Restaurant.Restaurant.NewPart.repository.IngredientsRepository;
import Restaurant.Restaurant.Order.Model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/report")
@RequiredArgsConstructor
public class DailyReportController {
    private final DailyReportService dailyReportService;
    private final IngredientsRepository ingredientsRepository;

    @GetMapping("/dailyReportPage")
    public String dailyReportPage(Model model) {

        model.addAttribute("allDailyReports", dailyReportService.getAll());
        model.addAttribute("currentUserName", this.getUsername());

        return "report/daily_homepage";
    }

    @GetMapping("/getDailyReport/{id}")
    public String getDailyReport(@PathVariable Long id,
                                 Model model) {

        Optional<DailyReport> optDailyReport = dailyReportService.getDailyReportById(id);
        DailyReport currentDailyReport = null;

        if (optDailyReport.isPresent()) {
            currentDailyReport = optDailyReport.get();
            model.addAttribute("dailyReport", currentDailyReport);
        }
        model.addAttribute("allDailyReports", dailyReportService.getAll());
        return "report/daily_homepage";

    }

    @PostMapping("/getDailyReportByDate")
    public String getDailyReportByDate(@RequestParam String datee,
                                       Model model) {

        LocalDate date = LocalDate.parse(datee);
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIN);

        Optional<DailyReport> optDailyReport = Optional.ofNullable(dailyReportService.getDailyReportByDay(dateTime));
        DailyReport currentDailyReport = null;

        model.addAttribute("currentUserName", this.getUsername());

        if (optDailyReport.isPresent()) {
            currentDailyReport = optDailyReport.get();
            model.addAttribute("dailyReport", currentDailyReport);
            System.out.println(currentDailyReport.getDish_price().get("Frytki"));
        } else {
            model.addAttribute("reportNotExist", true);
        }

        if (optDailyReport.isPresent()) {
            HashMap<Ingredients, Integer> ingCount = new HashMap<>();

            for (OrderModel order : currentDailyReport.getOrders()) {
                for (Product prodakt : order.getProducts()) {
                    for (Composition comp : prodakt.getDish().getCompositions()) {
                        int c = prodakt.getQuantity();
                        Ingredients ing = comp.getIngredients();
                        if (ingCount.get(ing) != null) ingCount.put(ing, c * (ingCount.get(ing) + comp.getCount()));
                        else ingCount.put(ing, c * comp.getCount());
                    }
                }
            }

            List<IngredientDTO> ingredientsDTO = new ArrayList<>();
            ingCount.keySet()
                    .forEach(ing -> ingredientsDTO.add(new IngredientDTO(ing.getProductName(), ingCount.get(ing))));

            int overallIngPrice = 0;
            for (IngredientDTO ingDto : ingredientsDTO) {
                Ingredients ing = ingredientsRepository.findByProductName(ingDto.getName());
                overallIngPrice += (ing.getPrice() * ingDto.getCount());
            }

            OverallDTO overallDto = new OverallDTO();
            overallDto.setOverallIngSum(overallIngPrice);


            model.addAttribute("overall1", overallDto);
            model.addAttribute("ingredients", ingredientsDTO);

        } else {
            model.addAttribute("reportNotExist", true);
        }


        model.addAttribute("allDailyReports", dailyReportService.getAll());
        return "report/daily_homepage";

    }

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();

        }
        return null;
    }


}
