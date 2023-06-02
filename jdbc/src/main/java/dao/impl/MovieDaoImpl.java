package dao.impl;

import dao.MovieDao;
import model.Genre;
import model.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieDaoImpl implements MovieDao {
    private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/jdbc_hibernate_demo";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "database99";

    @Override
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

    @Override
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

    @Override
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

    @Override
    public Movie getById(Long id) {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    """
                            SELECT movies.id, movies.title, movies.director_name, movies.release_date,
                            genres.id, genres.name
                            FROM movies
                            JOIN movies_genres ON movies.id = movies_genres.movie_id
                            JOIN genres ON genres.id = movies_genres.genre_id
                            WHERE movies.id=?;
                            """);

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Movie movie = new Movie();
            Set<Genre> genres = new HashSet<>();
            if (resultSet.next()) {
                movie.setId(resultSet.getLong(1));
                movie.setTitle(resultSet.getString(2));
                movie.setDirectorName(resultSet.getString(3));
                movie.setReleaseDate(resultSet.getDate(4).toLocalDate());

                Genre genre = new Genre();
                genre.setId(resultSet.getLong(5));
                genre.setName(resultSet.getString(6));

                genres.add(genre);
            }

            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getLong(5));
                genre.setName(resultSet.getString(6));

                genres.add(genre);
            }

            movie.setGenres(genres);

            return movie;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
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

    @Override
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
    public void getAllBetweenDatesUsingSQL(LocalDate startDate, LocalDate endDate) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement(
                    """
                            SELECT movies.id, movies.title, movies.director_name, movies.release_date,
                            genres.id, genres.name
                            FROM movies
                            JOIN movies_genres ON movies.id = movies_genres.movie_id
                            JOIN genres ON genres.id = movies_genres.genre_id
                            WHERE movies.release_date BETWEEN ? AND ?;
                            """);
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getLong(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
                System.out.println(resultSet.getDate(4).toLocalDate());
                System.out.println(resultSet.getLong(5));
                System.out.println(resultSet.getString(6));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
}
