package dao;

import model.Movie;

import java.time.LocalDate;
import java.util.List;

public interface MovieDao {
    void createTable();

    void save(Movie movie);

    List<Movie> getAll();

    Movie getById(Long id);

    void delete(Long id);

    void update(Movie movie, Long id);

    List<Movie> getAllBetweenStartDateAndEndDateUsingSQLQuery(LocalDate startDate, LocalDate endDate);

    void deleteAll();

    void measurePerformanceForGet(Integer num);

    void measurePerformanceForSave(Integer num);
}
