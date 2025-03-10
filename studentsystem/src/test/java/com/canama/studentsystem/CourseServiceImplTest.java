package com.canama.studentsystem;

import com.canama.studentsystem.DTO.CourseDto;
import com.canama.studentsystem.entity.Course;
import com.canama.studentsystem.mapper.CourseMapper;
import com.canama.studentsystem.repository.CourseRepository;
import com.canama.studentsystem.service.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    // @Mock Annotation wird verwendet, um ein Mock-Objekt zu erstellen.
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    // @InjectMocks Annotation wird verwendet, um die Abhängigkeiten des zu testenden Objekts zu injizieren.
    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;
    private CourseDto courseDto;

    // @BeforeEach Annotation wird verwendet, um eine Methode auszuführen, bevor jeder Testfall ausgeführt wird.
    @BeforeEach
    void setUp() {
        // Erstellen eines Kurs-Objekts und eines KursDto-Objekts.
        course = new Course(1, "Math", "Algebra", List.of());
        courseDto = new CourseDto(1, "Math", "Algebra");
    }

    // getAlleKurse() Methode sollte eine Liste von Kursen zurückgeben.
    @Test
    void getAllCourses_ShouldReturnListOfCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(courseMapper.toDto(course)).thenReturn(courseDto);

        List<CourseDto> result = courseService.getAllCourses();

        assertThat(result).hasSize(1);
        verify(courseRepository, times(1)).findAll();
    }

    // addCourse() Methode sollte einen Kurs hinzufügen und den hinzugefügten Kurs zurückgeben.
    @Test
    void addCourse_ShouldSaveAndReturnCourseDto() {
        when(courseMapper.toEntity(courseDto)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDto(course)).thenReturn(courseDto);

        CourseDto result = courseService.addCourse(courseDto);

        assertThat(result).isEqualTo(courseDto);
        verify(courseRepository, times(1)).save(course);
    }

    // deleteCourseById() Methode sollte eine RuntimeException werfen, wenn der Kurs nicht gefunden wird.
    @Test
    void deleteCourseById_ShouldThrowException_WhenCourseNotFound() {
        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> courseService.deleteCourseById(1));
        assertThat(exception.getMessage()).isEqualTo("Kurs mit der Id: 1 nicht gefunden");
    }

    // deleteCourseById() Methode sollte einen Kurs löschen, wenn der Kurs keine Studenten hat.
    @Test
    void deleteCourseById_ShouldDelete_WhenCourseHasNoStudents() {
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        courseService.deleteCourseById(1);

        verify(courseRepository, times(1)).delete(course);
    }
}
