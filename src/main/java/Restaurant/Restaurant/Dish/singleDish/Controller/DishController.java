package Restaurant.Restaurant.Dish.singleDish.Controller;

import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import Restaurant.Restaurant.Dish.singleDish.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("admin/dish")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @GetMapping("/listDishes")
    public String listDishes(Model model) {

        model.addAttribute("currentUserName", getCurrentUserName());
        model.addAttribute("dishes", dishService.getAll());

        return "dishes/listDishes";
    }

    @GetMapping("/newDish")
    public String newRestaurant(Model model) {
        model.addAttribute("currentUserName", getCurrentUserName());
        return "dishes/newDish";
    }

    @PostMapping("/confirmAddDish")
    @ResponseBody
    public ModelAndView addDish(@RequestParam("nazwa") String nazwa,
                                @RequestParam("cena") float cena,
                                Model model) {

        //check username is used
        if (dishService.isNameUsed(nazwa)) {
            model.addAttribute("nameIsUsed", true);
            return new ModelAndView("redirect:/admin/dish/newDish");
        } else {
            dishService.addDish(nazwa, cena);
            model.addAttribute("add", true);
            return new ModelAndView("redirect:/admin/dish/listDishes");
        }
    }

    @GetMapping("/removeDish/{id}")
    public ModelAndView removeDish(@PathVariable Long id,
                                   Model model) {

        dishService.removeDish(id);
        model.addAttribute("remove", true);

        return new ModelAndView("redirect:/admin/dish/listDishes");
    }

    @GetMapping("/editDish/{id}")
    public String editDish(@PathVariable Long id, Model model) {

        model.addAttribute("currentUserName", getCurrentUserName());

        Optional<Dish> optionalDish = dishService.getById(id);

        if (optionalDish.isPresent()) {
            Dish dish = optionalDish.get();

            model.addAttribute("name", dish.getName());
            model.addAttribute("price", dish.getPrice());
            model.addAttribute("ajdi", dish.getId());
        }
        return "dishes/editDish";
    }

    @PostMapping("/confirmEditDish/{id}")
    public ModelAndView confirmEditDish(@RequestParam("nazwa") String name,
                                        @RequestParam("cena") float price,
                                        @PathVariable Long id,
                                        Model model) {

        try {
            dishService.editDish(id, name, price);
            model.addAttribute("update", true);
            return new ModelAndView("redirect:/admin/dish/listDishes");
        } catch (IllegalStateException ex) {
            model.addAttribute("nameIsUsed", true);
            return new ModelAndView("redirect:/admin/dish/editDish/{id}");
        }

    }


    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        } else {
            return "default";
        }
    }

}
