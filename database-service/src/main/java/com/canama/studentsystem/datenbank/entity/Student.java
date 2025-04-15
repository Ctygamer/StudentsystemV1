package com.canama.studentsystem.datenbank.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert einen Studenten in einem Studentensystem.
 * Die Klasse ist als JPA-Entität konfiguriert und verwendet Lombok-Annotationen
 * für die automatische Generierung von Boilerplate-Code wie Getter, Setter und Konstruktoren.
 *
 * Ein Student kann mehreren Kursen zugewiesen sein (Many-to-Many Beziehung).
 */
@Entity
@Table(name = "student")
@Data // Generiert Getter, Setter, ToString, EqualsAndHashCode und RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    /**
     * Die eindeutige ID des Studenten.
     * Wird automatisch von der Datenbank generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Der Name des Studenten.
     */
    private String name;

    /**
     * Die Adresse des Studenten.
     */
    private String address;

    /**
     * Eine Liste von Kursen, in denen der Student eingeschrieben ist.
     * <p>
     * Diese Beziehung wird in einer Zwischentabelle "student_course" verwaltet,
     * die die Beziehung zwischen Studenten und Kursen speichert.
     * </p>
     * <p>
     * Cascade-Typen `PERSIST` und `MERGE` werden verwendet, um Änderungen an
     * der Beziehung automatisch zu synchronisieren.
     * </p>
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "student_course", // Name der Zwischentabelle
            joinColumns = @JoinColumn(name = "student_id"), // Verweist auf die Student-Id-Spalte
            inverseJoinColumns = @JoinColumn(name = "course_id") // Verweist auf die Course-Id-Spalte
    )
    @JsonManagedReference
    @Builder.Default // Initialisiert standardmäßig eine leere Liste
    private List<Course> courses = new ArrayList<>();
}