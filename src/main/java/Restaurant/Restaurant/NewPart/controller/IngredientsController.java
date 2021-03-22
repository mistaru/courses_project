package Restaurant.Restaurant.NewPart.controller;

import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import Restaurant.Restaurant.Dish.singleDish.repository.DishRepository;
import Restaurant.Restaurant.NewPart.model.Composition;
import Restaurant.Restaurant.NewPart.model.Ingredients;
import Restaurant.Restaurant.NewPart.repository.CompositionRepository;
import Restaurant.Restaurant.NewPart.repository.IngredientsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/ingredients")
public class IngredientsController {

    private final IngredientsRepository ingredientsRepository;
    private final CompositionRepository compositionRepository;
    private final DishRepository dishesRepository;

    public IngredientsController(IngredientsRepository ingredientsRepository, DishRepository dishesRepository, CompositionRepository compositionRepository) {
        this.ingredientsRepository = ingredientsRepository;
        this.dishesRepository = dishesRepository;
        this.compositionRepository = compositionRepository;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String main(Model model) {
        List<Ingredients> ingredients = ingredientsRepository.findAll().stream()
                .sorted()
                .collect(Collectors.toList());
        model.addAttribute("Ingredients", ingredients);
        return "ingredients/listIngredients";
    }


    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newIngredient(Model model) {
        model.addAttribute("newIngredient", new Ingredients());
        return "ingredients/newIngredients";
    }


    @RequestMapping(value = "/new_ingredient/add", method = RequestMethod.POST)
    public String addIngredient(@Valid Ingredients ingredients, Model model) {
        ingredientsRepository.save(ingredients);
        model.addAttribute("add", true);
        return "redirect:/admin/ingredients/list";
    }


    @RequestMapping(value = "/new_component/{id}", method = RequestMethod.GET)
    public String newComponent(@PathVariable Long id, Model model) {
        Dish dish = dishesRepository.findById(id).get();
        List<Ingredients> ingredientsList = ingredientsRepository.findAll();
        Iterable<Composition> compositions = compositionRepository.findAll();
        model.addAttribute("Dish", dish);
        model.addAttribute("dishName", dish.getName());
        model.addAttribute("dishPrice", dish.getPrice());
        model.addAttribute("ingredientsList", ingredientsList);
        model.addAttribute("compositions", compositions);

        return "/ingredients/newComponent";
    }


    @RequestMapping(value = "/new_component/add", method = RequestMethod.POST)
    public String newComponentAdd(
            @RequestParam String nameDish,
            @RequestParam String productName,
            @RequestParam Integer count,
            Map<String, Object> model) {

        Ingredients ingredientsList = ingredientsRepository.findByProductName(productName);
        Dish dish = dishesRepository.findByName(nameDish).get();
        Composition composition = new Composition();
        composition.setDish(dish);
        composition.setIngredients(ingredientsList);
        composition.setCount(count);

        compositionRepository.save(composition);

        Iterable<Dish> dishesIterable = dishesRepository.findAll();
        dishesRepository.save(dish);

        Iterable<Composition> compositions = compositionRepository.findAll();

        model.put("addIngredient", dishesIterable);
        model.put("compositions", compositions);

        return "redirect:/admin/dish/editDish/" + dish.getId();
    }


    @RequestMapping(value = "/ingredient/delete/{id}", method = RequestMethod.GET)
    @Transactional
    public String deleteIngredients(@PathVariable Long id, Model model) {
        ingredientsRepository.deleteById(id);
        model.addAttribute("remove", true);
        return "redirect:/admin/ingredients/list";
    }

}