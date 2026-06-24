package com.example.StudentForm.repository;
import com.example.StudentForm.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // You don't need to write any code here! 
    // JpaRepository provides methods like: save(), findAll(), findById(), deleteById(), etc.
}


