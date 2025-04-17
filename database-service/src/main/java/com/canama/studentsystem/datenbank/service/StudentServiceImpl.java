package com.canama.studentsystem.datenbank.service;


import com.canama.studentsystem.datenbank.entity.Course;
import com.canama.studentsystem.datenbank.entity.Student;
import com.canama.studentsystem.datenbank.mapper.StudentMapper;
import com.canama.studentsystem.datenbank.repository.CourseRepository;
import com.canama.studentsystem.datenbank.repository.StudentRepository;
import com.canama.studentsystemcommon.DTO.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementierung des StudentService-Interfaces für die Verwaltung von Studenten- und Kursdaten.
 * Die Klasse nutzt Spring's Dependency Injection Framework und Transaktionsverwaltung.
 */

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;

    /**
     * Speichert einen Studenten in der Datenbank. Falls der Student bereits existiert
     * (überprüft durch die ID), werden seine Kurse aktualisiert.
     *
     * @param studentDto Der zu speichernde Student.
     * @return Der gespeicherte Student.
     */
    @Override
    @Transactional
    public StudentDto saveStudent(StudentDto studentDto) {
        // Mapping DTO zu Entity
        Student student = studentMapper.toEntity(studentDto);

        // Kurse anhand der IDs laden und setzen
        List<Course> courses = studentDto.courses().stream()
                .map(courseDto -> courseRepository.findById(courseDto.id())
                        .orElseThrow(() -> new RuntimeException("Kurs wurde nicht Gefunden " + courseDto.id())))
                .collect(Collectors.toList());

        student.setCourses(courses);

        // Student speichern
        Student savedStudent = studentRepository.save(student);

        // Mapping Entity zu DTO
        return studentMapper.toDto(savedStudent);
    }

    /**
     * Ruft alle Studenten aus der Datenbank ab.
     *
     * @return Eine Liste von Studenten.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Löscht einen Studenten aus der Datenbank anhand seiner ID. Vor dem Löschen
     * werden die Beziehungen zu seinen Kursen entfernt.
     *
     * @param id Die ID des zu löschenden Studenten.
     * @throws RuntimeException Wenn der Student mit der angegebenen ID nicht existiert.
     */
    @Override
    @Transactional
    public void deleteStudentById(Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student mit der Id: " + id + " nicht gefunden");
        }
        studentRepository.deleteById(id);
    }

    /**
     * Aktualisiert die Kurse eines Studenten.
     *
     * @param id        Die ID des Studenten.
     * @param courseIds Eine Liste von Kurs-IDs, die dem Studenten zugewiesen werden sollen.
     * @return Der aktualisierte Student.
     */
    @Override
    @Transactional
    public StudentDto updateStudentCourses(Integer id, List<Integer> courseIds) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student mit der Id: " + id + " nicht gefunden"));

        List<Course> courses = courseIds.stream()
                .map(courseId -> courseRepository.findById(courseId)
                        .orElseThrow(() -> new RuntimeException("Kurs nicht Gefunden " + courseId)))
                .collect(Collectors.toList());

        student.setCourses(courses);
        Student updatedStudent = studentRepository.save(student);
        return studentMapper.toDto(updatedStudent);
    }
}