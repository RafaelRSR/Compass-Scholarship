package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.scrummaster.ScrumMasterDTORequest;
import rafael.rocha.compasschallenge.dtos.scrummaster.ScrumMasterDTOResponse;
import rafael.rocha.compasschallenge.entity.Instructor;
import rafael.rocha.compasschallenge.entity.ScrumMaster;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.service.ScrumMasterService;

import java.util.List;

@RestController
@RequestMapping("/v1/scrum-masters")
public class ScrumMasterController {

    @Autowired
    private ScrumMasterService scrumMasterService;

    @GetMapping
    public ResponseEntity<List<ScrumMaster>> getAllScrumMasters() {
        List<ScrumMaster> scrumMasters = scrumMasterService.getAllScrumMasters();
        return ResponseEntity.ok(scrumMasters);
    }

    @GetMapping("/{scrumMasterId}")
    public ResponseEntity<Object> getScrumMasterById(@PathVariable Long scrumMasterId) {
        try {
            ScrumMaster scrumMaster = scrumMasterService.findById(scrumMasterId);
            return ResponseEntity.ok(scrumMaster);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found!");
        }
    }

    @PostMapping
    public ResponseEntity<String> createScrumMaster(@RequestBody ScrumMasterDTORequest scrumMasterDTORequest) {
        scrumMasterService.createScrumMaster(scrumMasterDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created a new scrum master successfully!");
    }

    @PutMapping("/{scrumMasterId}")
    public ResponseEntity<ScrumMaster> updateScrumMaster(
            @PathVariable Long scrumMasterId, @RequestBody ScrumMasterDTORequest scrumMasterDTORequest) {
        ScrumMaster updatedScrumMaster = scrumMasterService.updateScrumMaster(scrumMasterId, scrumMasterDTORequest);
        return ResponseEntity.ok(updatedScrumMaster);
    }

    @DeleteMapping("/{scrumMasterId}")
    public ResponseEntity<String> deleteScrumMaster(@PathVariable Long scrumMasterId) {
        scrumMasterService.deleteScrumMaster(scrumMasterId);
        return ResponseEntity.ok("Deleted scrum master with id: " + scrumMasterId);
    }
}

