package Restaurant.Restaurant.DailyReport.Controller;

import Restaurant.Restaurant.DailyReport.Model.DailyReport;
import Restaurant.Restaurant.DailyReport.Service.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Controller
@RequestMapping("/admin/report")
public class DaliyReportController {

    @Autowired
    DailyReportService dailyReportService;

    @GetMapping("/dailyReportPage")
    public String daliyReportPage(Model model){

        model.addAttribute("allDailyReports", dailyReportService.getAll());
        model.addAttribute("currentUserName", this.getUsername());

        return "report/daily_homepage";
    }

    @GetMapping("/getDailyReport/{id}")
    public String getDailyReport(@PathVariable Long id,
                                       Model model){

        Optional<DailyReport> optDailyReport = dailyReportService.getDailyReportById(id);
        DailyReport currentDailyReport = null;

        if(optDailyReport.isPresent()){
            currentDailyReport = optDailyReport.get();
            model.addAttribute("dailyReport",currentDailyReport);
        }
        model.addAttribute("allDailyReports", dailyReportService.getAll());
        return "report/daily_homepage";

    }

    @PostMapping("/getDailyReportByDate")
    public String getDailyReportByDate(@RequestParam String datee,
                                 Model model){

        LocalDate date = LocalDate.parse(datee);
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIN);

        Optional<DailyReport> optDailyReport = Optional.ofNullable(dailyReportService.getDailyReportByDay(dateTime));
        DailyReport currentDailyReport = null;

        model.addAttribute("currentUserName", this.getUsername());

        if(optDailyReport.isPresent()){
            currentDailyReport = optDailyReport.get();
            model.addAttribute("dailyReport",currentDailyReport);
            System.out.println(currentDailyReport.getDish_price().get("Frytki"));
        }
        else{
            model.addAttribute("reportNotExist",true);
        }
        model.addAttribute("allDailyReports", dailyReportService.getAll());
        return "report/daily_homepage";

    }

    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;

        }
        return null;
    }


}
