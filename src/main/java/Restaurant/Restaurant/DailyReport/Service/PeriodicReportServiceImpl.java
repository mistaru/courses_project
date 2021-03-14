package Restaurant.Restaurant.DailyReport.Service;

import Restaurant.Restaurant.DailyReport.Repository.DailyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PeriodicReportServiceImpl implements PeriodicReportService {

    @Autowired
    DailyReportService dailyReportService;

    @Autowired
    DailyReportRepository dailyReportRepository;


}
