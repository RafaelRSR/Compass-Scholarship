package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.instructor.InstructorDTORequest;
import rafael.rocha.compasschallenge.entity.Instructor;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.service.InstructorService;

import java.util.List;

@RestController
@RequestMapping("/v1/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        List<Instructor> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<Object> getInstructorById(@PathVariable Long instructorId) {
        try {
            Instructor instructor = instructorService.findById(instructorId);
            return ResponseEntity.ok(instructor);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found!");
        }
    }

    @PostMapping
    public ResponseEntity<String> createInstructor(@RequestBody InstructorDTORequest instructorDTORequest) {
        instructorService.createInstructor(instructorDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created a new instructor successfully!");
    }

    @PutMapping("/{instructorId}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long instructorId, @RequestBody InstructorDTORequest instructorDTORequest) {
        Instructor updatedInstructor = instructorService.updateInstructor(instructorId, instructorDTORequest);
        return ResponseEntity.ok(updatedInstructor);
    }

    @DeleteMapping("/{instructorId}")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long instructorId) {
        instructorService.deleteInstructor(instructorId);
        return ResponseEntity.ok("Deleted instructor with id: " + instructorId);
    }
}