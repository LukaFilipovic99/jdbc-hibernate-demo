import dao.MovieDao;
import dao.MovieDaoImpl;
import model.Movie;

import java.time.LocalDate;

public class JdbcDemoApp {

    public static void main(String[] args) {
        MovieDao movieDao = new MovieDaoImpl();

        // CREATE TABLE
        movieDao.createTable();

        // SAVE
        movieDao.save(new Movie("The Lord Of The Rings", "Peter Jackson", LocalDate.of(2001, 12, 15)));

        // GET ALL
        System.out.println(movieDao.getAll());

        // UPDATE
        movieDao.update(new Movie("The Lord Of The Rings: The Return Of The King", "Peter Jackson", LocalDate.of(2003, 12, 15)), 1L);

        // GET BY ID
        System.out.println(movieDao.getById(1000L));

        // DELETE
        movieDao.delete(1000L);

    }

}
