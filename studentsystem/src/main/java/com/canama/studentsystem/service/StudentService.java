package com.canama.studentsystem.service;

import java.util.List;
import com.canama.studentsystem.DTO.StudentDto;

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
    public StudentDto saveStudent(StudentDto studentDto);

    /**
     * Ruft alle Studenten ab.
     *
     * @return eine Liste aller StudentDto-Objekte
     */
    public List<StudentDto> getAllStudents();

    /**
     * Löscht einen Studenten anhand der eindeutigen ID.
     *
     * @param id die eindeutige ID des Studenten
     */
    public void deleteStudentById(Integer id);

    /**
     * Aktualisiert die Kurse eines Studenten anhand der übergebenen Kurs-IDs.
     *
     * @param id die eindeutige ID des Studenten
     * @param courseIds eine Liste der Kurs-IDs, die dem Studenten zugeordnet werden sollen
     * @return das aktualisierte StudentDto-Objekt
     */
    public StudentDto updateStudentCourses(Integer id, List<Integer> courseIds);
}