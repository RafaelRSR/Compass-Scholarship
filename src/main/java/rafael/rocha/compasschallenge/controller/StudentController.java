package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.StudentDTORequest;
import rafael.rocha.compasschallenge.dtos.StudentDTOResponse;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.service.StudentService;

@RestController
@RequestMapping("/v2/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/get/{studentId}")
    public ResponseEntity<Object> findById(@PathVariable Long studentId) {
        try {
            Student student = studentService.findById(studentId);
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found!");
        }
    }

    @PostMapping("/post")
    public ResponseEntity<StudentDTOResponse> createStudent(@RequestBody StudentDTORequest studentDTORequest) {
        StudentDTOResponse newStudent = studentService.createStudent(studentDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }
}