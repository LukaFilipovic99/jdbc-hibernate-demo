package model;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long id;
    private String title;
    private String directorName;
    private LocalDate releaseDate;
    private Set<Genre> genres;

    public Movie(String title, String directorName, LocalDate releaseDate) {
        this.title = title;
        this.directorName = directorName;
        this.releaseDate = releaseDate;
    }

    public Movie(String title, String directorName, LocalDate releaseDate, Set<Genre> genres) {
        this.title = title;
        this.directorName = directorName;
        this.releaseDate = releaseDate;
        this.genres = genres;
    }
}
