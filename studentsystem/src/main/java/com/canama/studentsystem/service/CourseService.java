package com.canama.studentsystem.service;

import com.canama.studentsystem.DTO.CourseDto;

import java.util.List;

public interface CourseService {
    List<CourseDto> getAllCourses();
    CourseDto addCourse(CourseDto courseDto);
    void deleteCourseById(Integer courseId);
    String addStudentToCourse(Integer courseId, Integer studentId);
}