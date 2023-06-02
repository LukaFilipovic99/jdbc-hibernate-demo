package dao;

import model.Movie;

import java.time.LocalDate;
import java.util.List;

public interface MovieDao {
    void save(Movie movie);

    List<Movie> getAll();

    Movie getById(Long id);

    void update(Movie movie, Long id);

    void delete(Movie movie);

    List<Movie> getAllBetweenStartAndEndDateUsingHQLQuery(LocalDate startDate, LocalDate endDate);

    void deleteAll();

    void getByIdCacheExample(Long id) throws InterruptedException;

    void measurePerformanceForGet(Integer num);

    void measurePerformanceForSave(Integer num);
}
