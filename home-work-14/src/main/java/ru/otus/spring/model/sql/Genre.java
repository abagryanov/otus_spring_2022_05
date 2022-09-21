package ru.otus.spring.model.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "genre")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    @EqualsAndHashCode.Include
    private long id;
    @Column(name = "name")
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(long id) {
        this.id = id;
    }
}
