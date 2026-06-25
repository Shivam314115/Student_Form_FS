package com.example.StudentForm.repository;
import com.example.StudentForm.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
   boolean existsByEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);
    boolean existsByEnrollmentNumber(String enrollmentNumber);
}
//  this interface will provide us CRUD operations for Student entity.
//Spring Data Jpa will implement this automatically at runtime.
