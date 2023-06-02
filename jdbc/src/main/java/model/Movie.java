package model;

import lombok.*;

import java.time.LocalDate;

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

    public Movie(String title, String directorName, LocalDate releaseDate) {
        this.title = title;
        this.directorName = directorName;
        this.releaseDate = releaseDate;
    }
}
