package com.canama.studentsystemcommon.DTO;


import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * Datenübertragungsobjekt für einen Kurs.
 *
 * Enthält die wesentlichen Daten eines Kurses, inklusive der eindeutigen ID,
 * des Namens sowie der Beschreibung.
 */
public record CourseDto(
        Integer id,

        @NotBlank(message = "Name darf nicht leer sein")
        String name,

        @NotBlank(message = "Beschreibung darf nicht leer sein")
        String description
) implements Serializable {
}