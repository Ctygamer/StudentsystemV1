package com.canama.studentsystem.datenbank.service;


import com.canama.studentsystemcommon.DTO.CourseDto;

import java.util.List;

public interface CourseService {
    List<CourseDto> getAllCourses();
    CourseDto saveCourse(CourseDto courseDto);
    void deleteCourseById(Integer courseId);
    String addStudentToCourse(Integer courseId, Integer studentId);
}