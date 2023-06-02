package dao.impl;

import dao.MovieGenreDao;

import java.sql.*;

public class MovieGenreDaoImpl implements MovieGenreDao {
    private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/jdbc_hibernate_demo";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "database99";

    @Override
    public void createTable() {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {

            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS movies_genres (
                    movie_id BIGINT NOT NULL,
                    genre_id BIGINT NOT NULL,
                    PRIMARY KEY (movie_id, genre_id));
                    """);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Could not connect to the database!");
        }
    }

    @Override
    public void save(Long movieId, Long genreId) {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO movies_genres (movie_id, genre_id) VALUES (?, ?)");
            statement.setLong(1, movieId);
            statement.setLong(2, genreId);

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
