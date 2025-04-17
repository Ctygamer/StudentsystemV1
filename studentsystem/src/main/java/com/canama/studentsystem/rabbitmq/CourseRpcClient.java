package com.canama.studentsystem.rabbitmq;

import com.canama.studentsystem.config.RabbitMQConfig;
import com.canama.studentsystemcommon.DTO.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CourseRpcClient {

    private final RabbitTemplate rabbitTemplate;

    public CourseDto saveCourseViaRpc(CourseDto courseDto) {
        Object response = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.COURSE_EXCHANGE,RabbitMQConfig.COURSE_SAVE, courseDto);
        if (response == null) {
            throw new RuntimeException("Keine Antwort vom Course-RPC-Service erhalten.");
        }
        return (CourseDto) response;
    }

    public List<CourseDto> getAllCoursesViaRpc() {
        Object response = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.COURSE_EXCHANGE, RabbitMQConfig.COURSE_GET_ALL, "getAll");
        if (response == null) {
            throw new RuntimeException("Keine Antwort vom Course-RPC-Service erhalten.");
        }
        return (List<CourseDto>) response;
    }

    public void deleteCourseByIdViaRpc(Integer id) {
        Object response = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.COURSE_EXCHANGE, RabbitMQConfig.COURSE_DELETE, id);
        if (response == null) {
            throw new RuntimeException("Keine Antwort vom Course-RPC-Service erhalten.");
        }
    }

    public String saveStudentToCourseViaRpc(Integer courseId, Integer studentId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("courseId", courseId);
        payload.put("studentId", studentId);
        Object response = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.COURSE_EXCHANGE, RabbitMQConfig.COURSE_ADD_STUDENT, payload);
        if (response == null) {
            throw new RuntimeException("Keine Antwort vom Course-RPC-Service erhalten.");
        }
        return (String) response;
    }
}
