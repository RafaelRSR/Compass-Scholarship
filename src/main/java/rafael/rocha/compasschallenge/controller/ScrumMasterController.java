package rafael.rocha.compasschallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.compasschallenge.dtos.scrummaster.ScrumMasterDTORequest;
import rafael.rocha.compasschallenge.dtos.scrummaster.ScrumMasterDTOResponse;
import rafael.rocha.compasschallenge.entity.ScrumMaster;
import rafael.rocha.compasschallenge.service.ScrumMasterService;

import java.util.List;

@RestController
@RequestMapping("/scrum-masters")
public class ScrumMasterController {

    @Autowired
    private ScrumMasterService scrumMasterService;

    @GetMapping
    public ResponseEntity<List<ScrumMaster>> getAllScrumMasters() {
        List<ScrumMaster> scrumMasters = scrumMasterService.getAllScrumMasters();
        return ResponseEntity.ok(scrumMasters);
    }

    @GetMapping("/{scrumMasterId}")
    public ResponseEntity<ScrumMaster> getScrumMasterById(@PathVariable Long scrumMasterId) {
        ScrumMaster scrumMaster = scrumMasterService.findById(scrumMasterId);
        return ResponseEntity.ok(scrumMaster);
    }

    @PostMapping
    public ResponseEntity<ScrumMasterDTOResponse> createScrumMaster(@RequestBody ScrumMasterDTORequest scrumMasterDTORequest) {
        ScrumMasterDTOResponse createdScrumMaster = scrumMasterService.createScrumMaster(scrumMasterDTORequest);
        return ResponseEntity.ok(createdScrumMaster);
    }

    @PutMapping("/{scrumMasterId}")
    public ResponseEntity<ScrumMaster> updateScrumMaster(
            @PathVariable Long scrumMasterId, @RequestBody ScrumMasterDTORequest scrumMasterDTORequest) {
        ScrumMaster updatedScrumMaster = scrumMasterService.updateScrumMaster(scrumMasterId, scrumMasterDTORequest);
        return ResponseEntity.ok(updatedScrumMaster);
    }

    @DeleteMapping("/{scrumMasterId}")
    public ResponseEntity<Void> deleteScrumMaster(@PathVariable Long scrumMasterId) {
        scrumMasterService.deleteScrumMaster(scrumMasterId);
        return ResponseEntity.noContent().build();
    }
}

