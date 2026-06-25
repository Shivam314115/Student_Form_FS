package com.example.StudentForm.controller;
import com.example.StudentForm.exception.DuplicateFieldException;
import com.example.StudentForm.model.Student;
import com.example.StudentForm.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// for telling Spring that this class is a REST controller and handle all the 
// incoming requests  for this perticualr endpoint.
//Shorthand for @Controller and @ResponseBody.

@RequestMapping("/api/students")
//set the base path for all the endpoints


public class StudentController {
private final StudentRepository studentRepository;
//dependency injection of StudentRepository to access the DB.
//this attr store the ref of the repository 
public StudentController(StudentRepository studentRepository) {
this.studentRepository = studentRepository;
}
// GET all students (For your list view)
    @GetMapping
//Get endpoint
//fetch all students in the form of a List<Student>.
public List<Student> getAllStudents() {
return studentRepository.findAll();
}
// POST a new student 
    @PostMapping
//Post endpoint
public Student createStudent(@Valid @RequestBody Student student) {

// Check each unique field individually BEFORE saving, so we know
// exactly which one(s) caused the conflict instead of relying on
// the generic DataIntegrityViolationException from the DB.
Map<String, String> duplicateErrors = new HashMap<>();
if (studentRepository.existsByEmail(student.getEmail())) {
            duplicateErrors.put("email", "Duplicate email entered");
}
if (studentRepository.existsByMobileNumber(student.getMobileNumber())) {
            duplicateErrors.put("mobileNumber", "Duplicate mobile number entered");
}
if (studentRepository.existsByEnrollmentNumber(student.getEnrollmentNumber())) {
            duplicateErrors.put("enrollmentNumber", "Duplicate enrollment number entered");
}
if (!duplicateErrors.isEmpty()) {
throw new DuplicateFieldException(duplicateErrors);
}
return studentRepository.save(student);
//this returns a Json of the Student we just posted. 
//if posted successfully.
}
}