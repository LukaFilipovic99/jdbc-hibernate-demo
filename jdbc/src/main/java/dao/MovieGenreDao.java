package dao;

public interface MovieGenreDao {

    void createTable();

    void save(Long movieId, Long genreId);

}
