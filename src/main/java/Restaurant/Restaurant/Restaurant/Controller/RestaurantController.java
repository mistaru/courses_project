package Restaurant.Restaurant.Restaurant.Controller;

import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import Restaurant.Restaurant.Restaurant.service.RestaurantService;
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
@RequestMapping("admin/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/listRestaurants")
    public String listRestaurants(Model model){

        model.addAttribute("currentUserName", getCurrentUserName());
        model.addAttribute("restaurants",restaurantService.getAll());

        return "restaurants/listRestaurants";
    }

    @GetMapping("/newRestaurant")
    public String newRestaurant(Model model){
        model.addAttribute("currentUserName", getCurrentUserName());
        return "restaurants/newRestaurant";
    }

    @PostMapping("/confirmAddRestaurant")
    @ResponseBody
    public ModelAndView addRestaurant(@RequestParam("nazwa") String nazwa,
                                @RequestParam("adres") String adres,
                                Model model){

        //check username is used
        if(restaurantService.isNameUsed(nazwa)){
            model.addAttribute("nameIsUsed",true);
            return new ModelAndView("redirect:/admin/restaurant/newRestaurant");
        }
        else{
            restaurantService.addRestaurant(nazwa, adres);
            model.addAttribute("add", true);
            return new ModelAndView("redirect:/admin/restaurant/listRestaurants");
        }
    }

    @GetMapping("/removeRestaurant/{id}")
    public ModelAndView removeRestaurant(@PathVariable Long id,
                                 Model model){

        restaurantService.removeRestaurant(id);
        model.addAttribute("remove",true);

        return new ModelAndView("redirect:/admin/restaurant/listRestaurants");
    }

    @GetMapping("/editRestaurant/{id}")
    public String editRestaurant(@PathVariable Long id, Model model){

        model.addAttribute("currentUserName", getCurrentUserName());

        Optional<Restaurant> optionalRestaurant = restaurantService.getById(id);

        if(optionalRestaurant.isPresent()){
            Restaurant restaurant = optionalRestaurant.get();

            model.addAttribute("name", restaurant.getName());
            model.addAttribute("address",restaurant.getAddress());
            model.addAttribute("ajdi",restaurant.getId());
        }
        return "restaurants/editRestaurant";
    }


    @PostMapping("/confirmEditRestaurant/{id}")
    public ModelAndView confirmEditRestaurant(@RequestParam("nazwa") String name,
                                        @RequestParam("adres") String address,
                                        @PathVariable Long id,
                                        Model model){

        try {
            restaurantService.editRestaurant(id, name, address);
            model.addAttribute("update", true);
            return new ModelAndView("redirect:/admin/restaurant/listRestaurants");
        }
        catch (IllegalStateException ex){
            model.addAttribute("nameIsUsed", true);
            return new ModelAndView("redirect:/admin/restaurant/editRestaurant/{id}");
        }

    }









    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        else{
            return "default";
        }
    }


}
