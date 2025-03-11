package com.canama.studentsystem;

import com.canama.studentsystem.DTO.CourseDto;
import com.canama.studentsystem.DTO.StudentDto;
import com.canama.studentsystem.controller.StudentController;
import com.canama.studentsystem.service.CourseService;
import com.canama.studentsystem.service.StudentService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// @ExtendWith(SpringExtension.class) ist eine Annotation, die JUnit mit Spring-Unterstützung aktiviert.
// @WebMvcTest(StudentController.class) ist eine Annotation, die den Test auf den StudentController beschränkt.
@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    // @Autowired ist eine Annotation, die die automatische Verdrahtung von Spring-Beans aktiviert.
    // private MockMvc mockMvc; ist eine Instanzvariable, die die MockMvc-Instanz enthält.
    @Autowired
    private MockMvc mockMvc;

    // @MockBean ist eine Annotation, die eine vorhandene Bean durch ein Mock-Objekt ersetzt.
    @MockBean
    private StudentService studentService;

    // @Test ist eine Annotation, die eine Methode als Testmethode kennzeichnet.
    @Test
    void getAllStudents_ShouldReturnList() throws Exception {
        Mockito.when(studentService.getAllStudents())
                .thenReturn(List.of(new StudentDto(1, "Max", "Musterland", List.of())));

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    // Testet, ob die Methode addStudent() des StudentControllers einen Studenten hinzufügt und den hinzugefügten Studenten zurückgibt.
    @Test
    void addStuden_ShouldReturnCreatedStudent() throws Exception {
        StudentDto studentDto = new StudentDto(null, "Max", "Musterland", new ArrayList<>());
        StudentDto savedStudentDto = new StudentDto(2, "Max", "Musterland", new ArrayList<>());

        Mockito.when(studentService.saveStudent(Mockito.any(StudentDto.class)))
                .thenReturn(savedStudentDto);

        String studentJson = """
                {
                    "id": null,
                    "name": "Max",
                    "address": "Musterland",
                    "courses": []
                }
                """;

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Max")))
                .andExpect(jsonPath("$.address", is("Musterland")))
                .andExpect(jsonPath("$.courses", hasSize(0)));
    }

    // Testet, ob die Methode deleteStudent() des StudentControllers einen Studenten löscht.
    @Test
    void deleteStudent_ShouldReturnDeletedStudent() throws Exception {
        Mockito.doNothing().when(studentService).deleteStudentById(1);

        mockMvc.perform(delete("/student/{id}", 1))
                .andExpect(status().isOk());
    }

    // Testet, ob die Methode updateStudentCourses() des StudentControllers die Kurse eines Studenten aktualisiert.
    @Test
    void updateStudentCourse_ShouldReturnUpdatedStudent() throws Exception {
        StudentDto updatedStudentDto = new StudentDto(1, "Max", "Musterland", List.of(new CourseDto(1, "Math", "Algebra")));

        Mockito.when(studentService.updateStudentCourses(1, List.of(1)))
                .thenReturn(updatedStudentDto);

        String courseIdsJson = """
            [1]
            """;

        mockMvc.perform(put("/student/{id}/update-courses", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseIdsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Max")))
                .andExpect(jsonPath("$.address", is("Musterland")))
                .andExpect(jsonPath("$.courses", hasSize(1)));
    }

}
