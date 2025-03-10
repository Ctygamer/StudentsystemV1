package com.canama.studentsystem.DTO;

import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

        @NotNull(message = "Kurse dürfen nicht null sein")
        List<CourseDto> courses
) implements Serializable {
}
