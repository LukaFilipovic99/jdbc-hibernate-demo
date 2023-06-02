import dao.GenreDao;
import dao.MovieDao;
import dao.impl.GenreDaoImpl;
import dao.impl.MovieDaoImpl;
import model.Genre;
import model.Movie;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class HibernateDemoApp {

    public static void main(String[] args) {
        MovieDao movieDao = new MovieDaoImpl();
        GenreDao genreDao = new GenreDaoImpl();

        // SAVE MANY TO MANY
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

        // GET OBJECT WITH CHILD OBJECTS
        System.out.println(movieDao.getById(1L));

        // HQL QUERY EXAMPLE
        System.out.println(movieDao.getAllBetweenDatesUsingHQL(
                LocalDate.of(2000, 1, 1), LocalDate.of(2020, 1, 1)));

    }

}
