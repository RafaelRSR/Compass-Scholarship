package rafael.rocha.compasschallenge.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.scrummaster.ScrumMasterDTORequest;
import rafael.rocha.compasschallenge.dtos.scrummaster.ScrumMasterDTOResponse;
import rafael.rocha.compasschallenge.entity.ScrumMaster;
import rafael.rocha.compasschallenge.exceptions.ScrumMasterNotFoundException;
import rafael.rocha.compasschallenge.repository.ScrumMasterRepository;

import java.util.List;

@Service
public class ScrumMasterService {

    @Autowired
    private ScrumMasterRepository scrumMasterRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ScrumMaster> getAllScrumMasters() {
        return scrumMasterRepository.findAll();
    }

    public ScrumMaster findById(Long scrumMasterId) {
        return scrumMasterRepository.findById(scrumMasterId)
                .orElseThrow(() -> new ScrumMasterNotFoundException("Scrum Master not found with id: " + scrumMasterId));
    }

    public void createScrumMaster(ScrumMasterDTORequest scrumMasterDTORequest) {
        ScrumMaster scrumMaster = modelMapper.map(scrumMasterDTORequest, ScrumMaster.class);
        ScrumMaster newScrumMaster = scrumMasterRepository.save(scrumMaster);
        modelMapper.map(newScrumMaster, ScrumMasterDTOResponse.class);
    }

    public ScrumMaster updateScrumMaster(Long scrumMasterId, ScrumMasterDTORequest scrumMasterDTORequest) {
        ScrumMaster scrumMaster = scrumMasterRepository.findById(scrumMasterId)
                .orElseThrow(() -> new ScrumMasterNotFoundException("Scrum Master not found with id: " + scrumMasterId));

        modelMapper.map(scrumMasterDTORequest, scrumMaster);
        return scrumMasterRepository.save(scrumMaster);
    }

    public void deleteScrumMaster(Long scrumMasterId) {
        ScrumMaster scrumMaster = scrumMasterRepository.findById(scrumMasterId)
                .orElseThrow(() -> new ScrumMasterNotFoundException("Scrum Master not found with id: " + scrumMasterId));

        scrumMasterRepository.delete(scrumMaster);
    }
}

