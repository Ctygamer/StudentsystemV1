package com.canama.studentsystem.controller;

    import com.canama.studentsystem.DTO.StudentDto;
    import com.canama.studentsystem.service.StudentService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import jakarta.validation.Valid;
    import org.springframework.validation.BindingResult;

    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors;

    /**
     * Controller zur Verwaltung von Studenten.
     */
    @RestController
    @RequestMapping("/student")
    @RequiredArgsConstructor
    public class StudentController {

        private final StudentService studentService;

        /**
         * Fügt einen neuen Studenten hinzu.
         *
         * @param studentDto das DTO des hinzuzufügenden Studenten
         * @param bindingResult enthält Validierungsfehler, falls vorhanden
         * @return den hinzugefügten Studenten oder eine Fehlermeldung
         */
        @PostMapping
        public ResponseEntity<?> add(@Valid @RequestBody StudentDto studentDto, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors().stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errors);
            }

            try {
                StudentDto savedStudent = studentService.saveStudent(studentDto);
                return ResponseEntity.ok(savedStudent);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
            }
        }

        /**
         * Gibt alle Studenten zurück.
         *
         * @return eine Liste aller Studenten
         */
        @GetMapping
        public ResponseEntity<List<StudentDto>> getAllStudents() {
            List<StudentDto> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        }

        /**
         * Löscht einen Studenten anhand der ID.
         *
         * @param id die ID des zu löschenden Studenten
         * @return eine Antwort, die das Ergebnis der Löschung angibt
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
            try {
                studentService.deleteStudentById(id);
                return ResponseEntity.ok("Student deleted successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
            }
        }

        /**
         * Aktualisiert die Kurse eines Studenten.
         *
         * @param id die ID des Studenten
         * @param courseIds die Liste der Kurs-IDs
         * @return das aktualisierte Studenten-DTO oder eine Fehlermeldung
         */
        @PutMapping("/{id}/update-courses")
        public ResponseEntity<?> updateStudentCourses(@PathVariable Integer id,
                                                      @RequestBody List<Integer> courseIds) {
            try {
                StudentDto updatedStudent = studentService.updateStudentCourses(id, courseIds);
                return ResponseEntity.ok(updatedStudent);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", e.getMessage()));
            }
        }
    }