package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.ClassDTOResponse;
import rafael.rocha.compasschallenge.dtos.StudentDTORequest;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.exceptions.ClassroomNotFoundException;
import rafael.rocha.compasschallenge.service.ClassService;

@RestController
@RequestMapping(value = "/v1/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/get/{classId}")
    public ResponseEntity<ClassDTOResponse> getClassMembers(@PathVariable Long classId) {
        ClassDTOResponse classMembers = classService.getClassMembers(classId);
        return ResponseEntity.ok(classMembers);
    }

    @PostMapping("/post")
    public ResponseEntity<String> createClass(@RequestBody ClassDTOResponse classDTO) {
        classService.createClass(classDTO);
        return ResponseEntity.ok("Class created!");
    }

    @PutMapping("/{classId}/finishClass")
    public ResponseEntity<Object> finishClass(@PathVariable Long classId) {
        try {
            Class updatedClass = classService.finishClass(classId);
            if (updatedClass != null) {
                return ResponseEntity.ok(updatedClass);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ClassroomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{classId}/students")
    public ResponseEntity<Void> addStudentToClass(@PathVariable Long classId, @RequestBody StudentDTORequest studentDTORequest) {
        classService.addStudentToClass(classId, studentDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{classId}/students/{studentId}")
    public ResponseEntity<Void> deleteStudentFromClass(@PathVariable Long classId, @PathVariable Long studentId) {
        classService.deleteStudentFromClass(classId, studentId);
        return ResponseEntity.noContent().build();
    }
}
