package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.student.StudentDTORequest;
import rafael.rocha.compasschallenge.dtos.student.StudentDTOResponse;
import rafael.rocha.compasschallenge.entity.Coordinator;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping
    public ResponseEntity<List<Student>> getAllCoordinators() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/get/{studentId}")
    public ResponseEntity<Object> findById(@PathVariable Long studentId) {
        try {
            Student student = studentService.findById(studentId);
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found!");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<StudentDTOResponse> createStudent(@RequestBody StudentDTORequest studentDTORequest) {
        StudentDTOResponse newStudent = studentService.createStudent(studentDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<Object> updateStudent(@PathVariable Long studentId, @RequestBody StudentDTORequest studentDTORequest) {
        try {
            Student updatedStudent = studentService.updateStudent(studentId, studentDTORequest);
            return ResponseEntity.ok(updatedStudent);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudentFromStudents(studentId);
        return ResponseEntity.ok("Deleted student with id: " + studentId);
    }
}
