package com.canama.studentsystem.datenbank.mapper;


import com.canama.studentsystem.datenbank.entity.Student;
import com.canama.studentsystemcommon.DTO.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    StudentDto toDto(Student student);
    Student toEntity(StudentDto studentDto);
}