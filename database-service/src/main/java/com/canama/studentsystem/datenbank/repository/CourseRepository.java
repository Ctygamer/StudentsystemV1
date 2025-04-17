package com.canama.studentsystem.datenbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.canama.studentsystem.datenbank.entity.Course;
import org.springframework.stereotype.Repository;

/**
 * Repository zur Verwaltung von Kursen.
 *
 * Diese Schnittstelle erweitert JpaRepository, um CRUD-Operationen für
 * die {@code Course}-Entität bereitzustellen.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}