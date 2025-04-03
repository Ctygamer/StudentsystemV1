package com.canama.studentsystem;

import com.canama.studentsystem.DTO.CourseDto;
import com.canama.studentsystem.DTO.StudentDto;
import com.canama.studentsystem.controller.StudentController;
import com.canama.studentsystem.rabbitmq.StudentRpcClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRpcClient rpcClient;

    @Test
    void getAllStudents_ShouldReturnList() throws Exception {
        List<StudentDto> mockStudents = List.of(
                new StudentDto(1, "Max", "Musterland", List.of())
        );

        // Verwende die Überladung ohne Parameter
        Mockito.when(rpcClient.getAllStudentsViaRpc()).thenReturn(mockStudents);

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Max")));
    }

    @Test
    void addStudent_ShouldReturnCreatedStudent() throws Exception {
        StudentDto studentDto = new StudentDto(null, "Max", "Musterland", new ArrayList<>());
        StudentDto savedStudentDto = new StudentDto(2, "Max", "Musterland", new ArrayList<>());

        Mockito.when(rpcClient.saveStudentViaRpc(Mockito.any(StudentDto.class)))
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

    @Test
    void deleteStudent_ShouldReturnConfirmation() throws Exception {
        Mockito.when(rpcClient.deleteStudentByIdViaRpc(1))
                .thenReturn("Student gelöscht!");

        mockMvc.perform(delete("/student/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Student gelöscht!"));
    }

    @Test
    void updateStudentCourse_ShouldReturnUpdatedStudent() throws Exception {
        StudentDto updatedStudentDto = new StudentDto(1, "Max", "Musterland", List.of(new CourseDto(1, "Math", "Algebra")));

        Mockito.when(rpcClient.updateStudentCoursesViaRpc(1, List.of(1)))
                .thenReturn(updatedStudentDto);

        String courseIdsJson = "[1]";

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
