package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movies_genres",
            joinColumns = @JoinColumn(name = "movie_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "genre_id", nullable = false)
    )
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

    @Override
    public String toString() {
        return "Movie{" +
                "\nid=" + id +
                ", \ntitle='" + title + '\'' +
                ", \ndirectorName='" + directorName + '\'' +
                ", \nreleaseDate=" + releaseDate +
                ", \ngenres=" + genres +
                "\n}";
    }
}
