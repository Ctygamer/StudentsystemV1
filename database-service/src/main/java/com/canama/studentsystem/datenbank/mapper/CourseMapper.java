package com.canama.studentsystem.datenbank.mapper;


import com.canama.studentsystem.datenbank.entity.Course;


import com.canama.studentsystemcommon.DTO.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    CourseDto toDto(Course course);
    Course toEntity(CourseDto courseDto);
}