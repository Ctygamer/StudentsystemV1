package com.canama.studentsystem;


import com.canama.studentsystemcommon.DTO.CourseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

class CourseDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Testet, ob die Validierung fehlschlägt, wenn der Name null ist.
    @Test
    void shouldFailValidation_WhenNameIsNull() {
        CourseDto courseDto = new CourseDto(1, null, "Beschreibung");
        Set<ConstraintViolation<CourseDto>> violations = validator.validate(courseDto);
        assertFalse(violations.isEmpty(), "Es sollte eine Validierungsverletzung für den Namen geben");
    }

    // Testet, ob die Validierung fehlschlägt, wenn die Beschreibung null ist.
    @Test
    void shouldFailValidation_WhenDescriptionNull() {
        CourseDto courseDto = new CourseDto(1, "Max", null);
        Set<ConstraintViolation<CourseDto>> violations = validator.validate(courseDto);
        assertFalse(violations.isEmpty(), "Es sollte eine Validierungsverletzung für den Namen geben");
    }

    @Test
    void shouldPassValidation_WhenValidData() {
        CourseDto courseDto = new CourseDto(1, "Max", "Beschreibung");
        Set<ConstraintViolation<CourseDto>> violations = validator.validate(courseDto);
        assertTrue(violations.isEmpty(), "Es sollten keine Validierungsfehler auftreten");
    }
}
