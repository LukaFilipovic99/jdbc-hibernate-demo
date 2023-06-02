import dao.MovieDao;
import dao.MovieDaoImpl;
import model.Movie;

import java.time.LocalDate;

public class HibernateDemoApp {

    public static void main(String[] args) {
        MovieDao movieDao = new MovieDaoImpl();

        // SAVE
        movieDao.save(new Movie("The Dark Knight", "Christopher Nolan", LocalDate.of(2008, 1, 1)));

        // GET ALL
        System.out.println(movieDao.getAll());

        // UPDATE
        movieDao.update(new Movie("Batman vs Superman", "Zack Snyder", LocalDate.of(2015, 1, 1)), 1L);

        // GET BY ID
        System.out.println(movieDao.getById(1L));

        // DELETE
        movieDao.delete(movieDao.getById(1L));


        movieDao.save(new Movie("The Dark Knight: Rises", "Christopher Nolan", LocalDate.of(2012, 7, 16)));
        // CACHING
        try {
            movieDao.getByIdCacheExample(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
