package com.example.StudentForm.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;


@Entity

@Data
//  I used this annotation to avoid writing getters and setters for each field. It will automatically generate them for me.
// it will also generate toString(), equals(), and hashCode() methods for the class
// in case if we want to compare two objects of this class or print the object in a readable format.


@AllArgsConstructor
@NoArgsConstructor


@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Name is mandatory")
    @Size( max = 30, message = "Name cannot exceed 50 characters")
    @Column(nullable = false)
    private String name;
    

    @Email(message = "Invalid Email format  ")
    @Column(unique = true)
    private String email;


    @NotBlank(message = "Mobile Number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number must be exactly 10 digits")
    @Column(nullable = false, unique = true)
    private String mobileNumber;




    @NotBlank(message = "Branch is mandatory")
    @Size(max = 50, message = "Branch name too long")
    @Column(nullable = false)
    private String branch;





    @NotBlank(message = "Enrollment Number is mandatory")
    @Pattern(regexp = "^\\d{4}[A-Z]{2}\\d{6}$", message = "Enrollment number must follow the format  0801AA2YXXXX")
    @Column(unique = true, nullable = false)
    private String enrollmentNumber;


@PrePersist
@PreUpdate
private void normalizeFields() {
    if (email != null) {
        email = email.trim().toLowerCase();
    }
    if (enrollmentNumber != null) {
        enrollmentNumber = enrollmentNumber.trim().toUpperCase();
    }
    if (mobileNumber != null) {
        mobileNumber = mobileNumber.trim();
    }
    if (name != null) {
        name = name.trim();
    }
    if (branch != null) {
        branch = branch.trim();
    }
}


}
