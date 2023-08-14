package rafael.rocha.compasschallenge.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.squad.SquadDTORequest;
import rafael.rocha.compasschallenge.dtos.squad.SquadDTOResponse;
import rafael.rocha.compasschallenge.dtos.student.StudentDTORequest;
import rafael.rocha.compasschallenge.entity.Squad;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.exceptions.SquadNotFoundException;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.service.SquadService;
import rafael.rocha.compasschallenge.service.StudentService;

@RestController
@RequestMapping("/v1/squads")
public class SquadController {

    @Autowired
    private SquadService squadService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentService studentService;

    @GetMapping("/{squadId}")
    public ResponseEntity<SquadDTOResponse> findSquadById(@PathVariable Long squadId) {
        Squad squad = squadService.findSquadById(squadId);
        SquadDTOResponse squadDTOResponse = modelMapper.map(squad, SquadDTOResponse.class);

        return ResponseEntity.ok(squadDTOResponse);
    }

    @PostMapping
    public ResponseEntity<SquadDTOResponse> createSquad(@RequestBody SquadDTORequest squadDTORequest) {
        Squad newSquad = squadService.createSquad(squadDTORequest);
        SquadDTOResponse squadDTOResponse = modelMapper.map(newSquad, SquadDTOResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(squadDTOResponse);
    }

    @PostMapping("/{squadId}/students")
    public ResponseEntity<String> addStudentToSquad(@PathVariable Long squadId, @RequestBody StudentDTORequest studentDTORequest) {
        Student student = modelMapper.map(studentDTORequest, Student.class);
        squadService.addStudentsToSquad(squadId, student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added student to squad successfully!");
    }

    @DeleteMapping("/{squadId}")
    public ResponseEntity<String> deleteSquad(@PathVariable Long squadId) {
        squadService.deleteSquad(squadId);
        return ResponseEntity.ok("Squad deleted!");
    }

    @DeleteMapping("/{squadId}/students/{studentId}")
    public ResponseEntity<String> deleteStudentFromSquad(@PathVariable Long squadId, @PathVariable Long studentId) {
        try {
            squadService.deleteStudentFromSquad(squadId, studentId);
            return ResponseEntity.ok("Deleted student with id: " + studentId + "from squad " + squadId);
        } catch (SquadNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Squad not found!");
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found!");
        }
    }
}
