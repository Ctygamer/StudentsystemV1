package com.canama.studentsystem.rabbitmq;

import com.canama.studentsystemcommon.DTO.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.canama.studentsystem.datenbank.config.CourseRabbitMQConfig.*;

@Service
@RequiredArgsConstructor
public class CourseRpcClient {

    private final RabbitTemplate rabbitTemplate;

    public CourseDto saveCourseViaRpc(CourseDto courseDto) {
        Object response = rabbitTemplate.convertSendAndReceive(EXCHANGE, SAVE_ROUTING_KEY, courseDto);
        if (response == null) {
            throw new RuntimeException("Keine Antwort vom Course-RPC-Service erhalten.");
        }
        return (CourseDto) response;
    }

    public List<CourseDto> getAllCoursesViaRpc() {
        Object response = rabbitTemplate.convertSendAndReceive(EXCHANGE, GETALL_ROUTING_KEY, "getAll");
        if (response == null) {
            throw new RuntimeException("Keine Antwort vom Course-RPC-Service erhalten.");
        }
        return (List<CourseDto>) response;
    }

    public void deleteCourseByIdViaRpc(Integer id) {
        Object response = rabbitTemplate.convertSendAndReceive(EXCHANGE, DELETE_ROUTING_KEY, id);
        if (response == null) {
            throw new RuntimeException("Keine Antwort vom Course-RPC-Service erhalten.");
        }
    }

    public String saveStudentToCourseViaRpc(Integer courseId, Integer studentId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("courseId", courseId);
        payload.put("studentId", studentId);
        Object response = rabbitTemplate.convertSendAndReceive(EXCHANGE, ADD_STUDENT_ROUTING_KEY, payload);
        if (response == null) {
            throw new RuntimeException("Keine Antwort vom Course-RPC-Service erhalten.");
        }
        return (String) response;
    }
}
