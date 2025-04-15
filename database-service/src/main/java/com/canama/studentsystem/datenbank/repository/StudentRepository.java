package com.canama.studentsystem.datenbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canama.studentsystem.datenbank.entity.Student;

/**
 * Repository zur Verwaltung von Studenten.
 *
 * Diese Schnittstelle erweitert JpaRepository, um CRUD-Operationen für
 * die {@code Student}-Entität bereitzustellen.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
