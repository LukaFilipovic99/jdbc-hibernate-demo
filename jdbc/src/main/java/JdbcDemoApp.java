import dao.GenreDao;
import dao.MovieDao;
import dao.MovieGenreDao;
import dao.impl.GenreDaoImpl;
import dao.impl.MovieDaoImpl;
import dao.impl.MovieGenreDaoImpl;
import model.Genre;
import model.Movie;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class JdbcDemoApp {

    public static void main(String[] args) {
        MovieDao movieDao = new MovieDaoImpl();
        GenreDao genreDao = new GenreDaoImpl();
        MovieGenreDao movieGenreDao = new MovieGenreDaoImpl();

        // CREATE TABLE
        movieDao.createTable();
        genreDao.createTable();
        movieGenreDao.createTable();

        //SAVE MANY TO MANY
        Genre genreAkcijski = genreDao.getById(1L);
        Genre genrePustolovni = genreDao.getById(3L);

        Set<Genre> genres = new HashSet<>();
        genres.add(genreAkcijski);
        genres.add(genrePustolovni);

        Movie movie = new Movie(
                "The Lord Of The Rings: The Return Of The King",
                "Peter Jackson",
                LocalDate.of(2003, 12, 1),
                genres);
        movieDao.save(movie);
        Movie savedMovie = movieDao.getById(1L);

        movieGenreDao.save(savedMovie.getId(), genreAkcijski.getId());
        movieGenreDao.save(savedMovie.getId(), genrePustolovni.getId());

        System.out.println("Fetching movie from database...");
        System.out.println(movieDao.getById(1L));

        // SQL EXAMPLE
        movieDao.getAllBetweenDatesUsingSQL(
                LocalDate.of(2000, 1, 1), LocalDate.of(2020, 1, 1));

    }
}
