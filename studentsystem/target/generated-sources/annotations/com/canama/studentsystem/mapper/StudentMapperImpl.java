package com.canama.studentsystem.mapper;

import com.canama.studentsystem.DTO.CourseDto;
import com.canama.studentsystem.DTO.StudentDto;
import com.canama.studentsystem.entity.Course;
import com.canama.studentsystem.entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-11T12:12:10+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentDto toDto(Student student) {
        if ( student == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String address = null;
        List<CourseDto> courses = null;

        id = student.getId();
        name = student.getName();
        address = student.getAddress();
        courses = courseListToCourseDtoList( student.getCourses() );

        StudentDto studentDto = new StudentDto( id, name, address, courses );

        return studentDto;
    }

    @Override
    public Student toEntity(StudentDto studentDto) {
        if ( studentDto == null ) {
            return null;
        }

        Student.StudentBuilder student = Student.builder();

        student.id( studentDto.id() );
        student.name( studentDto.name() );
        student.address( studentDto.address() );
        student.courses( courseDtoListToCourseList( studentDto.courses() ) );

        return student.build();
    }

    protected CourseDto courseToCourseDto(Course course) {
        if ( course == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String description = null;

        id = course.getId();
        name = course.getName();
        description = course.getDescription();

        CourseDto courseDto = new CourseDto( id, name, description );

        return courseDto;
    }

    protected List<CourseDto> courseListToCourseDtoList(List<Course> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseDto> list1 = new ArrayList<CourseDto>( list.size() );
        for ( Course course : list ) {
            list1.add( courseToCourseDto( course ) );
        }

        return list1;
    }

    protected Course courseDtoToCourse(CourseDto courseDto) {
        if ( courseDto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.id( courseDto.id() );
        course.name( courseDto.name() );
        course.description( courseDto.description() );

        return course.build();
    }

    protected List<Course> courseDtoListToCourseList(List<CourseDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Course> list1 = new ArrayList<Course>( list.size() );
        for ( CourseDto courseDto : list ) {
            list1.add( courseDtoToCourse( courseDto ) );
        }

        return list1;
    }
}
