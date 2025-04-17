package com.canama.studentsystem.datenbank.service;

import com.canama.studentsystemcommon.DTO.StudentDto;

import java.util.List;


/**
 * Dienst zur Verwaltung von Studenten.
 *
 * Enthält Methoden zum Speichern, Abrufen, Löschen und Aktualisieren von
 * Studentendaten.
 */
public interface StudentService {

    /**
     * Speichert die Studentendetails in der Datenbank.
     *
     * @param studentDto das Datenübertragungsobjekt eines Studenten
     * @return das gespeicherte StudentDto-Objekt
     */
    StudentDto saveStudent(StudentDto studentDto);

    /**
     * Ruft alle Studenten ab.
     *
     * @return eine Liste aller StudentDto-Objekte
     */
    List<StudentDto> getAllStudents();

    /**
     * Löscht einen Studenten anhand der eindeutigen ID.
     *
     * @param id die eindeutige ID des Studenten
     */
    void deleteStudentById(Integer id);

    /**
     * Aktualisiert die Kurse eines Studenten anhand der übergebenen Kurs-IDs.
     *
     * @param id die eindeutige ID des Studenten
     * @param courseIds eine Liste der Kurs-IDs, die dem Studenten zugeordnet werden sollen
     * @return das aktualisierte StudentDto-Objekt
     */
    StudentDto updateStudentCourses(Integer id, List<Integer> courseIds);
}