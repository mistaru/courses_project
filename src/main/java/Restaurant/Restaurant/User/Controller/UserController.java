package Restaurant.Restaurant.User.Controller;

import Restaurant.Restaurant.DailyReport.Model.DailyReport;
import Restaurant.Restaurant.DailyReport.Service.DailyReportService;
import Restaurant.Restaurant.Dish.Product.model.Product;
import Restaurant.Restaurant.Dish.singleDish.Model.Dish;
import Restaurant.Restaurant.Dish.singleDish.service.DishService;
import Restaurant.Restaurant.Order.Model.OrderModel;
import Restaurant.Restaurant.Order.service.OrderService;
import Restaurant.Restaurant.Restaurant.Model.Restaurant;
import Restaurant.Restaurant.Restaurant.service.RestaurantService;
import Restaurant.Restaurant.User.Model.User;
import Restaurant.Restaurant.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@SessionAttributes("actCart")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final DishService dishService;
    private final DailyReportService dailyReportService;

    @GetMapping("/homepage")
    public String userHomePage(Model model) {

        Restaurant actRestaurant = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("currentUserName", currentUserName);
            Optional<User> user = userService.getByUsername(currentUserName);
            if (user.isPresent()) {
                actRestaurant = user.get().getRestaurant();
                model.addAttribute("currentUserRestaurant", actRestaurant.getName());
            }
        }

        model.addAttribute("message", "testmessage");
        //add Dishes to model
        List<Dish> dishes = dishService.getAll();
        model.addAttribute("dishes", dishes);

        return "homepage";
    }

    @GetMapping("/newOrder")
    public String newOrder(Model model) {
        model.addAttribute("currentUserName", this.getUsername());

        List<Dish> listAllDishes = dishService.getAll();
        model.addAttribute("listAllDishes", listAllDishes);
        return "order/newOrder";
    }


    @RequestMapping(value = "addProduct/{id}", method = RequestMethod.GET)
    public String addProduct(@PathVariable("id") String id,
                             HttpSession session,
                             Model model) {

        if (session.getAttribute("cart") == null) {
            List<Product> cart = new ArrayList<>();

            //if exist add to cart
            Optional<Dish> tempDish = dishService.getById(Long.valueOf(id));

            if (tempDish.isPresent()) {
                cart.add(new Product(tempDish.get()));
            }

            session.setAttribute("cart", cart);
        } else {
            List<Product> cart = (List<Product>) session.getAttribute("cart");

            //if exist add to cart
            Optional<Dish> tempDish = dishService.getById(Long.valueOf(id));

            int index = this.exists(id, cart);

            if (index == -1) {
                Optional<Dish> tempDish2 = dishService.getById(Long.valueOf(id));

                if (tempDish.isPresent()) {
                    cart.add(new Product(tempDish2.get()));
                }
            } else {
                int quantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(quantity);
                cart.get(index).calcPrice();
            }
            session.setAttribute("cart", cart);
        }

        session.setAttribute("total", this.calcTotalPrice((List<Product>) session.getAttribute("cart")));

        //getRestaurant
        Restaurant actRestaurant = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("currentUserName", currentUserName);
            Optional<User> user = userService.getByUsername(currentUserName);
            if (user.isPresent()) {
                actRestaurant = user.get().getRestaurant();
                model.addAttribute("currentUserRestaurant", actRestaurant.getName());
            }
        }

        List<Dish> listAllDishes = dishService.getAll();
        model.addAttribute("listAllDishes", listAllDishes);
        return "order/newOrder";
    }

    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable("id") String id, HttpSession session) {

        List<Product> cart = (List<Product>) session.getAttribute("cart");
        int index = this.exists(id, cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
        session.setAttribute("total", this.calcTotalPrice((List<Product>) session.getAttribute("cart")));
        return new ModelAndView("redirect:/user/newOrder");
    }

    @GetMapping("confirmAddOrder")
    public ModelAndView confirmAddOrder(HttpSession session, Model model) {
        OrderModel order = new OrderModel();

        Optional<User> user = userService.getByUsername(this.getUsername());

        if (session.getAttribute("cart") == null) {
            return new ModelAndView("redirect:/user/newOrder");
        }

        if (user.isPresent()) {
            order.setUser(user.get());
            order.setRestaurant(user.get().getRestaurant());
        }

        order.setDate(LocalDateTime.now());
        order.setProducts((List<Product>) session.getAttribute("cart"));
        order.setPrice(this.calcTotalPrice(order.getProducts()));
        order.setStatus("nowe");

        this.addOrderToDailyReport(order);
        orderService.addOrder(order);
        session.removeAttribute("cart");
        session.removeAttribute("total");
        model.addAttribute("add", true);

        System.out.println(dailyReportService.getDailyReportByDay(order.getDate()).getDish_price());

        return new ModelAndView("redirect:/user/homepage");
    }

    private void addOrderToDailyReport(OrderModel order) {
        DailyReport currentDay = dailyReportService.getDailyReportByDay(order.getDate());
        if (currentDay == null) {   //jeśli nie ma raportu z dzisija, stwórz go
            currentDay = new DailyReport();
            currentDay.setDate(order.getDate().toLocalDate());
            currentDay.setUser(order.getUser());
        }

        currentDay.addOrder(order);
        order.setDailyReport(currentDay);
        dailyReportService.addDailyReport(currentDay);
    }

    @GetMapping("orderList/{restaurant}")
    public String orderList(@PathVariable String restaurant,
                            Model model) {

        Restaurant actRestaurant = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("currentUserName", currentUserName);
            Optional<User> user = userService.getByUsername(currentUserName);
            if (user.isPresent()) {
                actRestaurant = user.get().getRestaurant();
                model.addAttribute("currentUserRestaurant", actRestaurant.getName());
            }
        }

        List<OrderModel> actRestaurantOrders = orderService.getRestaurantOrders(actRestaurant);

        model.addAttribute("currentOrdersActive", actRestaurantOrders.stream().filter(p -> p.getStatus().equals("nowe")).collect(Collectors.toList()));
        model.addAttribute("currentOrdersFinished", actRestaurantOrders.stream().filter(p -> p.getStatus().equals("zakonczone")).collect(Collectors.toList()));

        return "order/orderList";
    }

    @GetMapping("selectOrder/{id}")
    public ModelAndView selectOrder(@PathVariable Long id,
                                    Model model,
                                    HttpSession session) {
        OrderModel actOrder = null;
        Optional<OrderModel> OptActOrder = orderService.getOrderById(id);

        if (OptActOrder.isPresent()) {
            actOrder = OptActOrder.get();
        }

        Restaurant actRestaurant = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("currentUserName", currentUserName);
            Optional<User> user = userService.getByUsername(currentUserName);
            if (user.isPresent()) {
                actRestaurant = user.get().getRestaurant();
                model.addAttribute("currentUserRestaurant", actRestaurant.getName());
            }
        }

        System.out.println(actOrder);
        model.addAttribute("restaurant", userService.getByUsername(this.getUsername()).get().getRestaurant().getName());

        List<Product> products = actOrder.getProducts();
        session.setAttribute("currentSelectedOrder", actOrder);
        session.setAttribute("currentSelectedProductsInOrder", products);
        session.setAttribute("total", actOrder.getPrice());
        model.addAttribute("edit", true);

        return new ModelAndView("redirect:/user/orderList/{restaurant}");
    }

    @GetMapping("finishOrder")
    public ModelAndView finishOrder(
            Model model,
            HttpSession session) {
        OrderModel orderToFinish = (OrderModel) session.getAttribute("currentSelectedOrder");

        orderService.finish(orderToFinish.getId());

        model.addAttribute("restaurant", userService.getByUsername(this.getUsername()).get().getRestaurant().getName());

        return new ModelAndView("redirect:/user/orderList/{restaurant}");
    }

    private float calcTotalPrice(List<Product> products) {
        float sum = 0;
        for (Product xx : products) {
            sum += xx.getPrice();
        }
        return sum;
    }

    private int exists(String id, List<Product> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getDish().getId().toString().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }

}
