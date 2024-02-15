package com.jeeva.LibraryManagementSystem.repository;

import com.jeeva.LibraryManagementSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {


    List<Student> findByPhoneNo(String phoneNo);
    List<Student> findByName(String name);
    List<Student> findById(int id);
    List<Student> findByEmail(String email);
}
