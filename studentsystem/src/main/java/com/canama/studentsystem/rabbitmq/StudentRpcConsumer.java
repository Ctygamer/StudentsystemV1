package com.canama.studentsystem.rabbitmq;

import com.canama.studentsystem.DTO.StudentDto;
import com.canama.studentsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.canama.studentsystem.config.RabbitMQConfig.*;

@Service
@RequiredArgsConstructor
public class StudentRpcConsumer {

    private final StudentService studentService;

    // 1. Student speichern (POST)
    @RabbitListener(queues = SAVE_QUEUE)
    public StudentDto handleSaveStudent(@Payload StudentDto studentDto) {
        return studentService.saveStudent(studentDto);
    }

    // 2. Alle Studenten abrufen (GET)
    // Hier erwarten wir als Payload den String "getAll"
    @RabbitListener(queues = GETALL_QUEUE)
    public List<StudentDto> handleGetAll(@Payload String command) {
        return studentService.getAllStudents();
    }

    // 3. Student löschen (DELETE)
    @RabbitListener(queues = DELETE_QUEUE)
    public String handleDelete(@Payload Map<String, Object> payload) {
        Integer id = (Integer) payload.get("deleteId");
        studentService.deleteStudentById(id);
        return "Student gelöscht!";
    }

    // 4. Studentenkurse aktualisieren (PUT)
    @RabbitListener(queues = UPDATE_QUEUE)
    public StudentDto handleUpdate(@Payload Map<String, Object> payload) {
        Integer id = (Integer) payload.get("updateId");
        @SuppressWarnings("unchecked")
        List<Integer> courseIds = (List<Integer>) payload.get("courseIds");
        return studentService.updateStudentCourses(id, courseIds);
    }
}
