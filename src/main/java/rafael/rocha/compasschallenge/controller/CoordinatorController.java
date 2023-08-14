package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.CoordinatorDTORequest;
import rafael.rocha.compasschallenge.dtos.CoordinatorDTOResponse;
import rafael.rocha.compasschallenge.entity.Coordinator;
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

    @PostMapping
    public ResponseEntity<Coordinator> createCoordinator(@PathVariable Long coordinatorId, @RequestBody CoordinatorDTORequest coordinatorDTORequest) {
        Coordinator updatedCoordinator = coordinatorService.updateCoordinator(coordinatorId, coordinatorDTORequest);
        return ResponseEntity.ok(updatedCoordinator);
    }

    @DeleteMapping("/{coordinatorId}")
    public ResponseEntity<String> deleteCoordinator(@PathVariable Long coordinatorId) {
        coordinatorService.deleteCoordinator(coordinatorId);
        return ResponseEntity.ok("Deleted coordinator with id: " + coordinatorId);
    }
}
