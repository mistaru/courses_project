package Restaurant.Restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan("Restaurant.Restaurant.User")
@ComponentScan("Restaurant.Restaurant.Dish")
@ComponentScan("Restaurant.Restaurant.Restaurant")
@ComponentScan("Restaurant.Restaurant.Config")
@ComponentScan("Restaurant.Restaurant.Order")
@ComponentScan("Restaurant.Restaurant.Cart")
@ComponentScan("Restaurant.Restaurant.DailyReport")
@EnableWebMvc
public class RestaurantApplication {


    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }
}

       /*
        Используемые технологии включают Spring Boot, MVC, Security, Data.
        База данных PostgreSQL. Язык разработки Java
        Приложение для управления сетью ресторанов.
        После входа в систему мы перенаправляемся на панель администратора или пользователя.
        Администратор имеет возможность управлять ресторанами, сотрудниками и блюдами,
        а также генерировать отчеты за выбранные периоды.
        Сотрудник может добавлять новые заказы и изменять статус заказов.
        */
