package dao;

import model.Genre;

public interface GenreDao {
    void createTable();

    Genre getById(Long id);
}
