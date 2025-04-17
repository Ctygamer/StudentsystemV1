package com.canama.studentsystem.datenbank.service;


import com.canama.studentsystem.datenbank.entity.Course;
import com.canama.studentsystem.datenbank.mapper.CourseMapper;
import com.canama.studentsystem.datenbank.repository.CourseRepository;
import com.canama.studentsystemcommon.DTO.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseDto saveCourse(CourseDto courseDto) {
        Course course = courseMapper.toEntity(courseDto);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    @Override
    @Transactional
    public void deleteCourseById(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs mit der Id: " + courseId + " nicht gefunden"));
        if (!course.getStudents().isEmpty()) {
            throw new RuntimeException("Kurs kann nicht gelöscht werden wenn Studenten eingeschrieben sind");
        }
        courseRepository.delete(course);
    }

    @Override
    @Transactional
    public String addStudentToCourse(Integer courseId, Integer studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs nicht gefunden"));
        // Logik zum Hinzufügen eines Studenten zum Kurs
        return "Student zum Kurs hinzugefügt!";
    }
}