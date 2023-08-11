package rafael.rocha.compasschallenge.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.SquadDTORequest;
import rafael.rocha.compasschallenge.dtos.SquadDTOResponse;
import rafael.rocha.compasschallenge.entity.Squad;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.service.SquadService;
import rafael.rocha.compasschallenge.service.StudentService;

import java.util.List;

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

    @DeleteMapping("/{squadId}")
    public ResponseEntity<Void> deleteSquad(@PathVariable Long squadId) {
        squadService.deleteSquad(squadId);
        return ResponseEntity.noContent().build();
    }
}
