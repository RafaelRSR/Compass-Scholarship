package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafael.rocha.compasschallenge.dtos.ClassDTO;
import rafael.rocha.compasschallenge.service.ClassService;

@RestController
@RequestMapping(value = "/v1")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/{classId}")
    public ResponseEntity<ClassDTO> getClassMembers(@PathVariable Long classId) {
        ClassDTO classMembers = classService.getClassMembers(classId);
        return ResponseEntity.ok(classMembers);
    }
}
