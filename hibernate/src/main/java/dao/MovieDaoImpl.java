package dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Movie;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class MovieDaoImpl implements MovieDao {
    @Override
    public void save(Movie movie) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        // session.save(movie)
        session.persist(movie);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Movie> getAll() {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> rootEntry = query.from(Movie.class);
        CriteriaQuery<Movie> all = query.select(rootEntry);

        TypedQuery<Movie> allQuery = session.createQuery(all);

        List<Movie> movies = allQuery.getResultList();

        session.getTransaction().commit();

        return movies;
    }

    @Override
    public Movie getById(Long id) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Movie movie = session.load(Movie.class, id);
        Movie movie = session.get(Movie.class, id);

        session.getTransaction().commit();

        return movie;
    }

    @Override
    public void update(Movie movie, Long id) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        Movie existingMovie = session.get(Movie.class, id);
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setDirectorName(movie.getDirectorName());
        existingMovie.setReleaseDate(movie.getReleaseDate());

        session.merge(existingMovie);

        session.getTransaction().commit();
    }

    public void delete(Movie movie) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        // session.delete(movie);
        session.remove(movie);

        session.getTransaction().commit();
    }

    @Override
    public void getByIdCacheExample(Long id) throws InterruptedException {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        Movie movie;
        System.out.println("Fetching movie for the first time and printing query...");
        System.out.println("-----------------------------------------------------------------------");
        movie = session.get(Movie.class, id);
        System.out.println("Movie: " + movie);

        Thread.sleep(1000);
        System.out.println("\n");

        System.out.println("Fetching movie for the second time - USING CACHE - no query should be printed");
        System.out.println("-----------------------------------------------------------------------");
        movie = session.get(Movie.class, id);
        System.out.println("Movie: " + movie);
    }
}
