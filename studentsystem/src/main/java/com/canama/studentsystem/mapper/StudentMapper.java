package com.canama.studentsystem.mapper;

import com.canama.studentsystem.DTO.StudentDto;
import com.canama.studentsystem.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    StudentDto toDto(Student student);
    Student toEntity(StudentDto studentDto);
}