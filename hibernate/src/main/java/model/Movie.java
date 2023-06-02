package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, name = "director_name", length = 50)
    private String directorName;

    @Column(nullable = false, name = "release_date")
    private LocalDate releaseDate;

    public Movie(String title, String directorName, LocalDate releaseDate) {
        this.title = title;
        this.directorName = directorName;
        this.releaseDate = releaseDate;
    }
}
