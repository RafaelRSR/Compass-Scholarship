package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.CoordinatorDTORequest;
import rafael.rocha.compasschallenge.entity.Coordinator;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.service.CoordinatorService;

import java.util.List;

@RestController
@RequestMapping("/coordinators")
public class CoordinatorController {

    @Autowired
    private CoordinatorService coordinatorService;

    @GetMapping
    public ResponseEntity<List<Coordinator>> getAllCoordinators() {
        List<Coordinator> coordinators = coordinatorService.getAllCoordinators();
        return ResponseEntity.ok(coordinators);
    }

    @GetMapping("/{coordinatorId}")
    public ResponseEntity<Object> findCoordinatorById(@PathVariable Long coordinatorId) {
        try {
            Coordinator coordinator = coordinatorService.findById(coordinatorId);
            return ResponseEntity.ok(coordinator);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coordinator not found!");
        }
    }

    @PostMapping
    public ResponseEntity<String> createCoordinator(@RequestBody CoordinatorDTORequest coordinatorDTORequest) {
        coordinatorService.createCoordinator(coordinatorDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created a new coordinator successfully!");
    }

    @PutMapping("/{coordinatorId}")
    public ResponseEntity<Coordinator> updateCoordinator(@PathVariable Long coordinatorId, @RequestBody CoordinatorDTORequest coordinatorDTORequest) {
        Coordinator updatedCoordinator = coordinatorService.updateCoordinator(coordinatorId, coordinatorDTORequest);
        return ResponseEntity.ok(updatedCoordinator);
    }

    @DeleteMapping("/{coordinatorId}")
    public ResponseEntity<String> deleteCoordinator(@PathVariable Long coordinatorId) {
        coordinatorService.deleteCoordinator(coordinatorId);
        return ResponseEntity.ok("Deleted coordinator with id: " + coordinatorId);
    }
}
