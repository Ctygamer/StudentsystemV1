package com.canama.studentsystem.mapper;

import com.canama.studentsystem.DTO.CourseDto;
import com.canama.studentsystem.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    CourseDto toDto(Course course);
    Course toEntity(CourseDto courseDto);
}