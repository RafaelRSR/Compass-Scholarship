package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.classroom.ClassDTOResponse;
import rafael.rocha.compasschallenge.dtos.squad.SquadDTORequest;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.exceptions.ClassroomNotFoundException;
import rafael.rocha.compasschallenge.exceptions.MaxStudentsException;
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

    @PostMapping("/{classId}/start")
    public ResponseEntity<String> startClass(@PathVariable Long classId) {
        try {
            classService.startClass(classId);
            return ResponseEntity.ok("Class started successfully.");
        } catch (ClassroomNotFoundException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't start this class!");
        }
    }

    @PostMapping("/{classId}/finish")
    public ResponseEntity<String> finishClass(@PathVariable Long classId) {
        try {
            classService.finishClass(classId);
            return ResponseEntity.ok("Class finished successfully.");
        } catch (ClassroomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't finish this class!");
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

    @PostMapping("/{classId}/squads")
    public ResponseEntity<String> addSquadToClass(@PathVariable Long classId, @RequestBody SquadDTORequest squadDTORequest) {
        classService.addSquadToClass(classId, squadDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Squad added to class successfully.");
    }

    @DeleteMapping("/{classId}/squads/{squadId}")
    public ResponseEntity<String> deleteSquadFromClass(@PathVariable Long classId, @PathVariable Long squadId) {
        classService.deleteSquadFromClass(classId, squadId);
        return ResponseEntity.ok("Deleted squad with id: " + squadId);
    }

    @PostMapping("{classId}/coordinators/{coordinatorId}")
    public ResponseEntity<String> addCoordinatorToClass(@PathVariable Long classId, @PathVariable Long coordinatorId) {
        classService.addCoordinatorToClass(classId, coordinatorId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added coordinator with id: " + coordinatorId);
    }

    @DeleteMapping("{classId}/coordinators/{coordinatorId}")
    public ResponseEntity<String> deleteCoordinatorFromClass(@PathVariable Long classId, @PathVariable Long coordinatorId) {
        classService.deleteCoordinatorFromClass(classId);
        return ResponseEntity.ok("Deleted coordinator with id: " + coordinatorId);
    }

    @PostMapping("/{classId}/addInstructors")
    public ResponseEntity<String> addInstructorsToClass(@PathVariable Long classId) {
        classService.addInstructorsToClass(classId);
        return ResponseEntity.status(HttpStatus.OK).body("Instructors added to class with ID: " + classId);
    }

    @DeleteMapping("/{classId}/instructors/{instructorId}")
    public ResponseEntity<String> deleteInstructorFromClass(@PathVariable Long classId, @PathVariable Long instructorId) {
        classService.deleteInstructorFromClass(classId, instructorId);
        return ResponseEntity.ok("Deleted instructor with id: " + instructorId);
    }

    @PostMapping("/{classId}/addScrumMaster/{scrumMasterId}")
    public ResponseEntity<String> addScrumMasterToClass(@PathVariable Long classId, @PathVariable Long scrumMasterId) {
        classService.addScrumMasterToClass(classId, scrumMasterId);
        return ResponseEntity.status(HttpStatus.OK).body("Scrum Master added to class with ID: " + classId);
    }

    @DeleteMapping("/{classId}/scrumMaster")
    public ResponseEntity<String> deleteScrumMasterFromClass(@PathVariable Long classId) {
        classService.deleteScrumMasterFromClass(classId);
        return ResponseEntity.ok("Removed Scrum Master from class: " + classId);
    }

    @PostMapping("/{classId}/populateStaff")
    public ResponseEntity<String> populateStaff(@PathVariable Long classId) {
        classService.populateStaff(classId);
        return ResponseEntity.ok("Staff populated for class: " + classId);
    }

    @PostMapping("/{classId}/populateStudents")
    public ResponseEntity<String> populateStudents(@PathVariable Long classId) {
        try {
            classService.populateClassWithStudents(classId);
            return ResponseEntity.ok("Students added to class: " + classId);
        } catch (MaxStudentsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No more students to add!");
        }
    }
    @PostMapping("/{classId}/addSquads")
    public ResponseEntity<String> addSquadsToClassWithStudents(@PathVariable Long classId) {
        classService.addSquadsToClassWithStudents(classId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Squads added to class with students");
    }
}
