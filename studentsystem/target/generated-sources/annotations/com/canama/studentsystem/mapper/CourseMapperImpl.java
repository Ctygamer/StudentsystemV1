package com.canama.studentsystem.mapper;

import com.canama.studentsystem.DTO.CourseDto;
import com.canama.studentsystem.entity.Course;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-11T12:12:10+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDto toDto(Course course) {
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

    @Override
    public Course toEntity(CourseDto courseDto) {
        if ( courseDto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.id( courseDto.id() );
        course.name( courseDto.name() );
        course.description( courseDto.description() );

        return course.build();
    }
}
