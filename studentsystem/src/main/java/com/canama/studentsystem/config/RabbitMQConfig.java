package com.canama.studentsystem.config;

import com.canama.studentsystemcommon.config.RabbitMQBaseConfig;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RabbitMQBaseConfig.class)
public class RabbitMQConfig {
    // Exchange und Routing Keys für alle Operationen:
    public static final String STUDENT_EXCHANGE = "student.exchange";
    public static final String STUDENT_SAVE = "student.save";
    public static final String STUDENT_GET_ALL = "student.getAll";
    public static final String STUDENT_DELETE = "student.delete";
    public static final String STUDENT_UPDATE = "student.update";

    // Exchange und Routing Keys für Courses:
    public static final String COURSE_EXCHANGE = "course.exchange";
    public static final String COURSE_SAVE = "course.save";
    public static final String COURSE_GET_ALL = "course.getAll";
    public static final String COURSE_DELETE = "course.delete";
    public static final String COURSE_ADD_STUDENT = "course.addStudent";

}
