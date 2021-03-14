package Restaurant.Restaurant.DailyReport.Service;

import Restaurant.Restaurant.DailyReport.Model.DailyReport;
import Restaurant.Restaurant.DailyReport.Repository.DailyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyReportServiceImpl implements DailyReportService {
    private final DailyReportRepository dailyReportRepository;

    @Override
    public Optional<DailyReport> getDailyReportById(Long id) {
        return dailyReportRepository.findById(id);
    }

    @Override
    public DailyReport getDailyReportByDay(LocalDateTime localDateTime) {
        return dailyReportRepository.findByDate(localDateTime.toLocalDate());
    }

    @Override
    public List<DailyReport> getDailyReportBetween(LocalDate beginTemp, LocalDate endTemp) {

        return dailyReportRepository.findByDateBetween(beginTemp, endTemp);
    }

    @Override
    public List<DailyReport> getAll() {
        return dailyReportRepository.findAll();
    }

    @Override
    public void addDailyReport(DailyReport dailyReport) {
        dailyReportRepository.save(dailyReport);
    }
}
