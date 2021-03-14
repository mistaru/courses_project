package Restaurant.Restaurant.DailyReport.Service;

import Restaurant.Restaurant.DailyReport.Model.DailyReport;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface DailyReportService {

    Optional<DailyReport> getDailyReportById(Long id);

    DailyReport getDailyReportByDay(LocalDateTime localDateTime);

     List<DailyReport> getDailyReportBetween(LocalDate begin, LocalDate end);

    List<DailyReport> getAll();

    void addDailyReport(DailyReport dailyReport);
}
