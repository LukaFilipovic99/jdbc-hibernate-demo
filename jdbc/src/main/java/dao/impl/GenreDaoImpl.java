package dao.impl;

import dao.GenreDao;
import model.Genre;
import model.Movie;

import java.sql.*;

public class GenreDaoImpl implements GenreDao {
    private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/jdbc_hibernate_demo";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "database99";

    public void createTable() {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {

            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS genres (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(50) NOT NULL);
                    """);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Could not connect to the database!");
        }
    }

    @Override
    public Genre getById(Long id) {
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM genres WHERE id=?");

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getLong(1));
                genre.setName(resultSet.getString(2));

                return genre;
            } else {
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

}
