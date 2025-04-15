package com.canama.studentsystemcommon.DTO;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;



/**
 * Datenübertragungsobjekt für einen Studenten.
 *
 * Enthält alle notwendigen Daten eines Studenten, inkl.\ der eindeutigen ID, Namen,
 * Adresse und der Liste der belegteten Kurse.
 */
public record StudentDto(
        Integer id,

        @NotBlank(message = "Name ist erforderlich")
        String name,

        @NotBlank(message = "Adresse ist erforderlich")
        String address,

        @NotBlank(message = "Mindestens ein Kurs auswählen")
        List<CourseDto> courses
) implements Serializable {
}
