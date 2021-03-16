package Restaurant.Restaurant.NewPart.controller;

import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import Restaurant.Restaurant.Dish.singleDish.repository.DishRepository;
import Restaurant.Restaurant.NewPart.dto.IngredientDTO;
import Restaurant.Restaurant.NewPart.dto.OverallDTO;
import Restaurant.Restaurant.NewPart.dto.ReportDTO;
import Restaurant.Restaurant.NewPart.enume.EnumTable;
import Restaurant.Restaurant.NewPart.model.Composition;
import Restaurant.Restaurant.NewPart.model.Ingredients;
import Restaurant.Restaurant.NewPart.model.Report;
import Restaurant.Restaurant.NewPart.repository.IngredientsRepository;
import Restaurant.Restaurant.NewPart.repository.ReportRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Controller
public class ScoreController {

    ReportRepository reportRepository;
    DishRepository dishesRepository;
    IngredientsRepository ingredientsRepository;

    public ScoreController(ReportRepository reportRepository, DishRepository dishesRepository, IngredientsRepository ingredientsRepository) {
        this.reportRepository = reportRepository;
        this.dishesRepository = dishesRepository;
        this.ingredientsRepository = ingredientsRepository;
    }

    @GetMapping("/score")
    public ModelAndView listScore() {
        return new ModelAndView("score")
                .addObject("Dishes", dishesRepository.findAll())
                .addObject("ingredientsList", ingredientsRepository.findAll())
                .addObject("reportList", reportRepository.findAll());
    }

    @PostMapping("/score")
    public String scoreMethod(@RequestParam EnumTable table, Map<String, Object> model) {

        Map<Dish, List<Report>> reportList = reportRepository.findAll().stream()
                .filter(rep -> rep.getTable() == table)
                .collect(groupingBy(Report::getDish));

        List<ReportDTO> reports = new ArrayList<>();
        int overallSum = 0;
        for (Dish d : reportList.keySet()) {
            Integer price = d.getPrice();
            Integer currCount = reportList.get(d).stream().mapToInt(Report::getCount).sum();
            int sum = currCount * d.getPrice();

            overallSum = overallSum + sum;
            reports.add(new ReportDTO(d, price, currCount, sum));
        }

        Map<Ingredients, Integer> ingCount = new HashMap<>();
        for (ReportDTO rep : reports) {
            for (Composition comp : rep.getDish().getCompositions()) {
                Integer c = rep.getCount();
                Ingredients ing = comp.getIngredients();
                if (ingCount.get(ing) != null) ingCount.put(ing, c * (ingCount.get(ing) + comp.getCount()));
                else ingCount.put(ing, c * comp.getCount());
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
        overallDto.setOverallSum(overallSum);
        overallDto.setOverallIngSum(overallIngPrice);

        List<Report> listReports = reportRepository.findAll().stream()
                .filter(rep -> rep.getTable() == table).collect(Collectors.toList());

        Report rep = listReports.get(0);
        for (Report report : listReports) {
            if (rep.getDate().getSecond() > report.getDate().getSecond())
                rep = report;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm:ss");

        LocalTime time = rep.getDate().toLocalTime();
        LocalDate date = rep.getDate().toLocalDate();

        model.put("date", date);
        model.put("time", dtf.format(time));

        model.put("overall1", overallDto);
        model.put("Reports", reports);
        model.put("ingredients", ingredientsDTO);

        return "score";
    }
}

