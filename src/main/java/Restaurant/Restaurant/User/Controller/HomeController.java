package Restaurant.Restaurant.User.Controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class HomeController {

    @GetMapping(value={"", "/", "login"})
    public String login(Model model){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }

    @GetMapping("/CheckLogin")
    public ModelAndView checkLogin(){

        Collection<SimpleGrantedAuthority> authorities
                = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        //is ROLE_USER or ROLE_ADMIN
        if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN" ))) {
            return new ModelAndView("redirect:/admin/homepage");
        }
        else{
            return new ModelAndView("redirect:/user/homepage");
        }


    }


}
