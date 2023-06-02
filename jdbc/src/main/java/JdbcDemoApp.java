import dao.MovieDao;
import dao.MovieDaoImpl;

public class JdbcDemoApp {

    public static void main(String[] args) {
        MovieDao movieDao = new MovieDaoImpl();

        // PERFORMANCE TEST
        movieDao.measurePerformanceForGet(10000);
        movieDao.measurePerformanceForSave(10000);
    }

}
