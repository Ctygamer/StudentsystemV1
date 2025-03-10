package com.canama.studentsystem;

import com.canama.studentsystem.DTO.CourseDto;
import com.canama.studentsystem.controller.CourseController;
import com.canama.studentsystem.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Ersetzt eine vorhandene Bean durch ein Mock-Objekt (Mockito).
    @MockBean
    private CourseService courseService;

    // Testet, ob die Methode getAllCourses() des CourseControllers eine Liste von Kursen zurückgibt.
    @Test
    void getAllCourses_ShouldReturnList() throws Exception {
        Mockito.when(courseService.getAllCourses())
                .thenReturn(List.of(new CourseDto(1, "Math", "Algebra")));

        mockMvc.perform(get("/course"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    // Testet, ob die Methode addCourse() des CourseControllers einen Kurs hinzufügt und den hinzugefügten Kurs zurückgibt.
    @Test
    void addCourse_ShouldReturnCreatedCourse() throws Exception {
        CourseDto courseDto = new CourseDto(null, "Science", "Physics");
        CourseDto savedCourseDto = new CourseDto(2, "Science", "Physics");

        Mockito.when(courseService.addCourse(Mockito.any(CourseDto.class)))
                .thenReturn(savedCourseDto);

        String courseJson = """
                {
                    "id": null,
                    "name": "Science",
                    "description": "Physics"
                }
                """;

        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Science")))
                .andExpect(jsonPath("$.description", is("Physics")));
    }

    // Testet, ob die Methode deleteCourse() des CourseControllers einen Kurs löscht und den Statuscode 204 zurückgibt.
    @Test
    void deleteCourse_ShouldReturnNoContent() throws Exception {
        // Die Methode deleteCourseById() des CourseService wird aufgerufen, ohne dass etwas zurückgegeben wird.
        Mockito.doNothing().when(courseService).deleteCourseById(1);

        mockMvc.perform(delete("/course/{id}", 1))
                .andExpect(status().isNoContent());
    }

    // Testet, ob die Methode addStudentToCourse() des CourseControllers einen Studenten zu einem Kurs hinzufügt und eine Erfolgsmeldung zurückgibt.
    @Test
    void addStudentToCourse_ShouldReturnSuccessMessage() throws Exception {
        Mockito.when(courseService.addStudentToCourse(1, 42))
                .thenReturn("Student zum Kurs hinzugefügt!");

        mockMvc.perform(put("/course/{courseId}/add-student/{studentId}", 1, 42))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Student zum Kurs hinzugefügt!")));
    }
}