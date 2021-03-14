package Restaurant.Restaurant.DailyReport.Service;

import Restaurant.Restaurant.DailyReport.Repository.DailyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeriodicReportServiceImpl implements PeriodicReportService {

    private final DailyReportService dailyReportService;
    private final DailyReportRepository dailyReportRepository;

}
