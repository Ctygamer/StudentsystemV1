package com.canama.studentsystem;

import com.canama.studentsystem.DTO.StudentDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class StudentDtoTest {

    // Validator-Objekt wird verwendet, um die Validierung durchzuführen.
    private static Validator validator;

    // @BeforeAll Annotation wird verwendet, um eine Methode auszuführen, bevor alle Testfälle ausgeführt
    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Testet, ob die Validierung fehlschlägt, wenn der Name null ist.
    @Test
    void shouldFailValidation_WhenNameIsNull() {
        StudentDto studentDto = new StudentDto(1, null, "Musterstraße", List.of());
        Set<ConstraintViolation<StudentDto>> violations = validator.validate(studentDto);
        assertFalse(violations.isEmpty(), "Es sollte eine Validierungsverletzung für den Namen geben");
    }

    // Testet, ob die Validierung fehlschlägt, wenn der Adresse leer ist.
    @Test
    void shouldFailValidation_WhenAddressIsBlank() {
        StudentDto studentDto = new StudentDto(1, "Max", "", List.of());
        Set<ConstraintViolation<StudentDto>> violations = validator.validate(studentDto);
        assertFalse(violations.isEmpty(), "Es sollte eine Validierungsverletzung für die Adresse geben");
    }

    // Testet, ob die Validierung fehlschlägt, wenn die Kurse null sind.
    @Test
    void shouldFailValidation_WhenCoursesIsNull() {
        StudentDto studentDto = new StudentDto(1, "Max", "Musterstraße", null);
        Set<ConstraintViolation<StudentDto>> violations = validator.validate(studentDto);
        assertFalse(violations.isEmpty(), "Es sollte eine Validierungsverletzung für die Kurse geben");
    }

    // Testet, ob die Validierung erfolgreich ist, wenn gültige Daten bereitgestellt werden.
    @Test
    void shouldPassValidation_WhenValidDataIsProvided() {
        StudentDto studentDto = new StudentDto(1, "Max", "Musterstraße", List.of());
        Set<ConstraintViolation<StudentDto>> violations = validator.validate(studentDto);
        assertTrue(violations.isEmpty(), "Es sollten keine Validierungsfehler auftreten");
    }
}
