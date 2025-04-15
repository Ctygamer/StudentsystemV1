package com.canama.studentsystem.controller;

import com.canama.studentsystemcommon.DTO.CourseDto;
import com.canama.studentsystem.rabbitmq.CourseRpcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRpcClient courseRpcClient;

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseRpcClient.getAllCoursesViaRpc();
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<CourseDto> saveCourse(@RequestBody CourseDto courseDto) {
        CourseDto savedCourse = courseRpcClient.saveCourseViaRpc(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer id) {
        courseRpcClient.deleteCourseByIdViaRpc(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{courseId}/add-student/{studentId}")
    public ResponseEntity<String> saveStudentToCourse(@PathVariable Integer courseId,
                                                      @PathVariable Integer studentId) {
        try {
            String result = courseRpcClient.saveStudentToCourseViaRpc(courseId, studentId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler: " + e.getMessage());
        }
    }
}
