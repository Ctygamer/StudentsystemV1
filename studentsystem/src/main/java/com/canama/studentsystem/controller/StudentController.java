package com.canama.studentsystem.controller;

import com.canama.studentsystemcommon.DTO.StudentDto;
import com.canama.studentsystem.rabbitmq.StudentRpcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRpcClient rpcClient;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody StudentDto studentDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            StudentDto savedStudent = rpcClient.saveStudentViaRpc(studentDto);
            return ResponseEntity.ok(savedStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        try {
            List<StudentDto> students = rpcClient.getAllStudentsViaRpc(); // Aufruf ohne Parameter
            if (students == null) {
                students = List.of();
            }
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        try {
            String msg = rpcClient.deleteStudentByIdViaRpc(id);
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/update-courses")
    public ResponseEntity<?> updateStudentCourses(@PathVariable Integer id,
                                                  @RequestBody List<Integer> courseIds) {
        try {
            StudentDto updatedStudent = rpcClient.updateStudentCoursesViaRpc(id, courseIds);
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
