package com.canama.studentsystem.datenbank.rabbitmq;

import com.canama.studentsystem.datenbank.service.CourseService;
import com.canama.studentsystemcommon.DTO.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.canama.studentsystem.datenbank.config.CourseRabbitMQConfig.*;

@Service
@RequiredArgsConstructor
public class CourseRpcConsumer {

    private final CourseService courseService;

    // 1. Kurs speichern (analog zur Student-Version)
    @RabbitListener(queues = SAVE_QUEUE)
    public CourseDto handleSaveCourse(CourseDto courseDto) {
        return courseService.saveCourse(courseDto);
    }

    // 2. Alle Kurse abrufen (GET)
    @RabbitListener(queues = GETALL_QUEUE)
    public List<CourseDto> handleGetAllCourses(String command) {
        return courseService.getAllCourses();
    }

    // 3. Kurs löschen (DELETE)
    @RabbitListener(queues = DELETE_QUEUE)
    public String handleDeleteCourse(Integer id) {
        courseService.deleteCourseById(id);
        return "Kurs gelöscht!";
    }

    // 4. Student zu Kurs hinzufügen (PUT)
    @RabbitListener(queues = ADD_STUDENT_QUEUE)
    public String handleAddStudentToCourse(Map<String, Object> payload) {
        Integer courseId = (Integer) payload.get("courseId");
        Integer studentId = (Integer) payload.get("studentId");
        return courseService.addStudentToCourse(courseId, studentId);
    }
}
