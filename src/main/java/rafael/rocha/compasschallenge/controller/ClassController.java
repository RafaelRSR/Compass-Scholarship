package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.ClassDTO;
import rafael.rocha.compasschallenge.service.ClassService;

@RestController
@RequestMapping(value = "/v1/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/get/{classId}")
    public ResponseEntity<ClassDTO> getClassMembers(@PathVariable Long classId) {
        ClassDTO classMembers = classService.getClassMembers(classId);
        return ResponseEntity.ok(classMembers);
    }

    @PostMapping("/post")
    public ResponseEntity<String> createClass(@RequestBody ClassDTO classDTO) {
        classService.createClass(classDTO);
        return ResponseEntity.ok("Class created!");
    }
}
