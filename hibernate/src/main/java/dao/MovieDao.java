package dao;

import model.Movie;

import java.util.List;

public interface MovieDao {
    void save(Movie movie);

    List<Movie> getAll();

    Movie getById(Long id);

    void update(Movie movie, Long id);

    void delete(Movie movie);

    void getByIdCacheExample(Long id) throws InterruptedException;
}
