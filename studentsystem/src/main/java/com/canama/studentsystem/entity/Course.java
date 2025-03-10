package com.canama.studentsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert einen Kurs in einem Studentensystem.
 * Die Klasse ist als JPA-Entität konfiguriert und verwendet Lombok für die automatische Generierung
 * von Boilerplate-Code wie Getter, Setter und Konstruktoren.
 *
 * Ein Kurs kann mehreren Studenten zugeordnet werden (Many-to-Many Beziehung).
 */
@Entity
@Table(name = "course")
@Data // Kombiniert @Getter, @Setter, @ToString, @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    /**
     * Die eindeutige ID des Kurses.
     * Wird automatisch von der Datenbank generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Der Name des Kurses.
     */
    private String name;

    /**
     * Eine optionale Beschreibung des Kurses.
     */
    private String description;

    /**
     * Eine Liste von Studenten, die zu diesem Kurs gehören.
     * <p>
     * Diese Beziehung ist eine Many-to-Many Beziehung, die durch die
     * `@ManyToMany`-Annotation definiert ist. Das Feld wird von der `courses`
     * Eigenschaft in der `Student`-Entität verwaltet.
     * </p>
     * <p>
     * Die Annotation `@JsonBackReference` verhindert eine Endlosschleife
     * bei der Serialisierung (z. B. bei Verwendung von Jackson).
     * </p>
     * <p>
     * Mit `@Builder.Default` wird sichergestellt, dass die Liste standardmäßig
     * initialisiert ist, auch wenn ein Builder verwendet wird.
     * </p>
     */
    @ManyToMany(mappedBy = "courses")
    @JsonBackReference // Verhindert Endlosschleife bei JSON-Parsing
    @Builder.Default // Initialisiere standardmäßig eine leere Liste
    private List<Student> students = new ArrayList<>();
}