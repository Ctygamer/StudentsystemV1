package com.canama.studentsystem.controller;

    import com.canama.studentsystem.DTO.CourseDto;
    import com.canama.studentsystem.service.CourseService;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import lombok.RequiredArgsConstructor;

    import java.util.List;
    /**
     * Controller für die Verwaltung von Kursen.
     */
    @RestController
    @RequestMapping("/course")
    @RequiredArgsConstructor
    public class CourseController {

        private final CourseService courseService;

        /**
         * Gibt alle Kurse zurück.
         *
         * @return eine Liste aller Kurse
         */
        @GetMapping
        public ResponseEntity<List<CourseDto>> getAllCourses() {
            List<CourseDto> courses = courseService.getAllCourses();
            return ResponseEntity.ok(courses);
        }

        /**
         * Fügt einen neuen Kurs hinzu.
         *
         * @param courseDto der hinzuzufügende Kurs
         * @return der hinzugefügte Kurs
         */
        @PostMapping
        public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto courseDto) {
            CourseDto savedCourse = courseService.addCourse(courseDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
        }

        /**
         * Löscht einen Kurs anhand der ID.
         * @param id die ID des zu löschenden Kurses
         * @return eine Antwort, die das Ergebnis der Löschung angibt
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCourse(@PathVariable Integer id) {
            courseService.deleteCourseById(id);
            return ResponseEntity.noContent().build();
        }

        /**
         * Fügt einen Studenten zu einem Kurs hinzu.
         *
         * @param courseId die ID des Kurses
         * @param studentId die ID des hinzuzufügenden Studenten
         * @return eine Antwort, die das Ergebnis der Operation angibt
         */
        @PutMapping("/{courseId}/add-student/{studentId}")
        public ResponseEntity<String> addStudentToCourse(@PathVariable Integer courseId,
                                                         @PathVariable Integer studentId) {
            try {
                String result = courseService.addStudentToCourse(courseId, studentId);
                return ResponseEntity.ok(result);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Fehler: " + e.getMessage());
            }
        }
    }