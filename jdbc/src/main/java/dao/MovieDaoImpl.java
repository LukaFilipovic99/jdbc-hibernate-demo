package dao;

import model.Movie;
import util.CommonUtilClass;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoImpl implements MovieDao {
    private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/jdbc_hibernate_demo";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "database99";

    public void createTable() {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {

            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS movies (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    title VARCHAR(100) NOT NULL,
                    director_name VARCHAR(50) NOT NULL,
                    release_date DATE NOT NULL);
                    """);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Could not connect to the database!");
        }
    }

    public void save(Movie movie) {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO movies (title, director_name, release_date) VALUES (?, ?, ?)");
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDirectorName());
            statement.setDate(3, Date.valueOf(movie.getReleaseDate()));

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Movie> getAll() {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {
            List<Movie> movies = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM movies");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getLong(1));
                movie.setTitle(resultSet.getString(2));
                movie.setDirectorName(resultSet.getString(3));
                movie.setReleaseDate(resultSet.getDate(4).toLocalDate());

                movies.add(movie);
            }

            return movies;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public Movie getById(Long id) {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM movies WHERE id=?");

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getLong(1));
                movie.setTitle(resultSet.getString(2));
                movie.setDirectorName(resultSet.getString(3));
                movie.setReleaseDate(resultSet.getDate(4).toLocalDate());
                return movie;
            } else {
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM movies WHERE id=?");

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Movie movie, Long id) {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("""
                    UPDATE movies
                    SET title = ?, director_name = ?, release_date = ? WHERE id = ?
                    """);

            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDirectorName());
            statement.setDate(3, Date.valueOf(movie.getReleaseDate()));
            statement.setLong(4, id);

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Movie> getAllBetweenStartDateAndEndDateUsingSQLQuery(LocalDate startDate, LocalDate endDate) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            List<Movie> movies = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(
                    """
                            SELECT * FROM movies
                            WHERE movies.release_date BETWEEN ? AND ?;
                            """);
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getLong(1));
                movie.setTitle(resultSet.getString(2));
                movie.setDirectorName(resultSet.getString(3));
                movie.setReleaseDate(resultSet.getDate(4).toLocalDate());

                movies.add(movie);
            }

            return movies;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM movies");

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void measurePerformanceForGet(Integer num) {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {
            List<Movie> movies = new ArrayList<>();

            long startTime = System.currentTimeMillis();

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM movies LIMIT ?");
            statement.setInt(1, num);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getLong(1));
                movie.setTitle(resultSet.getString(2));
                movie.setDirectorName(resultSet.getString(3));
                movie.setReleaseDate(resultSet.getDate(4).toLocalDate());

                movies.add(movie);
            }

            long endTime = System.currentTimeMillis();

            System.out.println("Number of records: " + num + " | "
                    + "Time elapsed: " + (endTime - startTime) + " ms");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void measurePerformanceForSave(Integer num) {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {

            long startTime = System.currentTimeMillis();

            for (int i = 0; i < num; i++) {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO movies (title, director_name, release_date) VALUES (?, ?, ?)");
                Movie movie = new Movie(
                        CommonUtilClass.generateRandomString(10),
                        CommonUtilClass.generateRandomString(15),
                        CommonUtilClass.generateRandomLocalDate());

                statement.setString(1, movie.getTitle());
                statement.setString(2, movie.getDirectorName());
                statement.setDate(3, Date.valueOf(movie.getReleaseDate()));

                statement.executeUpdate();
            }

            long endTime = System.currentTimeMillis();

            System.out.println("Number of saved records: " + num + " | "
                    + "Time elapsed: " + (endTime - startTime) + " ms");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
