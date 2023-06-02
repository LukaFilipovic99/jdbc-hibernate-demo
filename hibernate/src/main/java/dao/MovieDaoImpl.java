package dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Movie;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.CommonUtilClass;
import util.HibernateUtil;

import java.time.LocalDate;
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

    public List<Movie> getAllBetweenStartAndEndDateUsingHQLQuery(LocalDate startDate, LocalDate endDate) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        Query<Movie> query = session.createQuery("""
                FROM Movie AS m
                WHERE m.releaseDate BETWEEN :startDate AND :endDate
                """, Movie.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        // Add pagination
        // query.setFirstResult(5)
        // query.setMaxResult(10)

        List<Movie> movies = query.list();

        session.getTransaction().commit();

        return movies;
    }

    @Override
    public void deleteAll() {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        Query<Movie> query = session.createQuery("DELETE FROM Movie", null);
        query.executeUpdate();

        session.getTransaction().commit();
    }

    @Override
    public void getByIdCacheExample(Long id) throws InterruptedException {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        Movie movie;
        System.out.println("Fetching movie for the first time and printing sql query...");
        System.out.println("-----------------------------------------------------------------------");
        movie = session.get(Movie.class, id);
        System.out.println("Movie: " + movie);

        Thread.sleep(1000);
        System.out.println("\n");

        System.out.println("Fetching movie for the second time - USING CACHE - no sql query should be printed");
        System.out.println("-----------------------------------------------------------------------");
        movie = session.get(Movie.class, id);
        System.out.println("Movie" + movie);
    }

    @Override
    public void measurePerformanceForGet(Integer num) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        long startTime = System.currentTimeMillis();

        Query<Movie> query = session.createQuery("FROM Movie", Movie.class);
        query.setMaxResults(num);

        List<Movie> movies = query.list();

        long endTime = System.currentTimeMillis();

        System.out.println("Number of records: " + movies.size() + " | "
                + "Time elapsed: " + (endTime - startTime) + " ms");

        session.getTransaction().commit();
    }

    @Override
    public void measurePerformanceForSave(Integer num) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < num; i++) {
            Movie movie = new Movie(
                    CommonUtilClass.generateRandomString(10),
                    CommonUtilClass.generateRandomString(15),
                    CommonUtilClass.generateRandomLocalDate());

            session.persist(movie);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Number of records: " + num + " | "
                + "Time elapsed: " + (endTime - startTime) + " ms");

        session.getTransaction().commit();
    }
}
