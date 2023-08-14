package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.ClassDTOResponse;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.exceptions.ClassroomNotFoundException;
import rafael.rocha.compasschallenge.service.ClassService;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<List<Class>> getAllClasses(){
        List<Class> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/get/{classId}")
    public ResponseEntity<ClassDTOResponse> getClassMembers(@PathVariable Long classId) {
        ClassDTOResponse classMembers = classService.getClassMembers(classId);
        return ResponseEntity.ok(classMembers);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createClass(@RequestBody ClassDTOResponse classDTO) {
        classService.createClass(classDTO);
        return ResponseEntity.ok("Class created!");
    }

    @PutMapping("/{classId}/finishClass")
    public ResponseEntity<String> finishClass(@PathVariable Long classId) {
        try {
            classService.finishClass(classId);
            return ResponseEntity.ok("Class finished successfully");
        } catch (ClassroomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{classId}/students/{studentId}")
    public ResponseEntity<String> addStudentToClass(@PathVariable Long classId, @PathVariable Long studentId) {
        classService.addStudentToClass(classId, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added student with id: " + studentId);
    }

    @DeleteMapping("{classId}/students/{studentId}")
    public ResponseEntity<String> deleteStudentFromClass(@PathVariable Long classId, @PathVariable Long studentId) {
        classService.deleteStudentFromClass(classId, studentId);
        return ResponseEntity.ok("Deleted student with id: " + studentId);
    }

    @PostMapping("{classId}/coordinators/{coordinatorId}")
    public ResponseEntity<String> addCoordinatorToClass(@PathVariable Long classId, @PathVariable Long coordinatorId) {
        classService.addCoordinatorToClass(classId, coordinatorId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added coordinator with id: " + coordinatorId);
    }

    @DeleteMapping("{classId}/coordinators/{coordinatorId}")
    public ResponseEntity<String> deleteCoordinatorFromClass(@PathVariable Long classId, @PathVariable Long coordinatorId) {
        classService.deleteCoordinatorFromClass(classId, coordinatorId);
        return ResponseEntity.ok("Deleted coordinator with id: " + coordinatorId);
    }
}
