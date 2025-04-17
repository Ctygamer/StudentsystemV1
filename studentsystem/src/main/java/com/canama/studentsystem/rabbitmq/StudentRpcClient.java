package com.canama.studentsystem.rabbitmq;

import com.canama.studentsystem.config.RabbitMQConfig;
import com.canama.studentsystemcommon.DTO.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentRpcClient {

    private final RabbitTemplate rabbitTemplate;

    public StudentDto saveStudentViaRpc(StudentDto studentDto) {
        Object response = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.STUDENT_EXCHANGE, RabbitMQConfig.STUDENT_SAVE, studentDto);
        if (response == null) throw new RuntimeException("Keine Antwort vom RPC-Service erhalten.");
        return (StudentDto) response;
    }

    // Überladung: Aufruf ohne Parameter, sendet automatisch "getAll"
    public List<StudentDto> getAllStudentsViaRpc() {
        return getAllStudentsViaRpc("getAll");
    }

    public List<StudentDto> getAllStudentsViaRpc(String command) {
        Object response = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.STUDENT_EXCHANGE, RabbitMQConfig.STUDENT_GET_ALL, command);
        if (response == null) throw new RuntimeException("Keine Antwort vom RPC-Service erhalten.");
        return (List<StudentDto>) response;
    }

    public String deleteStudentByIdViaRpc(Integer id) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("deleteId", id);
        Object response = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.STUDENT_EXCHANGE, RabbitMQConfig.STUDENT_DELETE, payload);
        if (response == null) throw new RuntimeException("Keine Antwort vom RPC-Service erhalten.");
        return (String) response;
    }

    public StudentDto updateStudentCoursesViaRpc(Integer id, List<Integer> courseIds) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("updateId", id);
        payload.put("courseIds", courseIds);
        Object response = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.STUDENT_EXCHANGE, RabbitMQConfig.STUDENT_UPDATE, payload);
        if (response == null) throw new RuntimeException("Keine Antwort vom RPC-Service erhalten.");
        return (StudentDto) response;
    }
}
