package Restaurant.Restaurant.DailyReport.Controller;

import Restaurant.Restaurant.DailyReport.Model.DailyReport;
import Restaurant.Restaurant.DailyReport.Model.PeriodicReport;
import Restaurant.Restaurant.DailyReport.Service.DailyReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("admin/report")
@RequiredArgsConstructor
public class PeriodicReportController {
    private final DailyReportService dailyReportService;

    @GetMapping("/periodicReportPage")
    public String getPeriodicReportHomepage(Model model) {
        model.addAttribute("currentUserName", this.getUsername());
        return "report/periodicReportHomepage";
    }

    @PostMapping("/getReportByDate")
    public String getReportByDate(@RequestParam String begin,
                                  @RequestParam String end,
                                  Model model) {

        LocalDate localDateBegin = LocalDate.parse(begin);
        LocalDate localDateEnd = LocalDate.parse(end);

        PeriodicReport currentPeriodicReport = new PeriodicReport(localDateBegin, localDateEnd);

        List<DailyReport> listDailyReportsBetweenDates = dailyReportService.getDailyReportBetween(localDateBegin, localDateEnd);
        currentPeriodicReport.setListDailyReports(listDailyReportsBetweenDates);
        currentPeriodicReport.update();

        if (listDailyReportsBetweenDates.size() > 0) {
            model.addAttribute("listReports", listDailyReportsBetweenDates);
            model.addAttribute("sumReport", currentPeriodicReport);
        }

        model.addAttribute("currentUserName", this.getUsername());
        System.out.println("profits : " + currentPeriodicReport.getProfits());

        return "report/periodicReportHomepage";
    }

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;

        }
        return null;
    }

}
