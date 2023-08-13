package rafael.rocha.compasschallenge.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.CoordinatorDTORequest;
import rafael.rocha.compasschallenge.dtos.CoordinatorDTOResponse;
import rafael.rocha.compasschallenge.entity.Coordinator;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.exceptions.CoordinatorNotFoundException;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.repository.CoordinatorRepository;

import java.util.List;

@Service
public class CoordinatorService {

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Coordinator> getAllCoordinators() {
        return coordinatorRepository.findAll();
    }

    public Coordinator findById(Long coordinatorId) {
        return coordinatorRepository.findById(coordinatorId)
                .orElseThrow(() -> new CoordinatorNotFoundException("Coordinator not found with id: " + coordinatorId));
    }

    public CoordinatorDTOResponse createCoordinator(CoordinatorDTORequest coordinatorDTORequest) {
        Coordinator coordinator = modelMapper.map(coordinatorDTORequest, Coordinator.class);
        Coordinator newCoordinator = coordinatorRepository.save(coordinator);
        return modelMapper.map(newCoordinator, CoordinatorDTOResponse.class);
    }

    public Coordinator updateCoordinator(Long coordinatorId, CoordinatorDTORequest coordinatorDTORequest) {
        Coordinator coordinator = coordinatorRepository.findById(coordinatorId)
                .orElseThrow(() -> new CoordinatorNotFoundException("Coordinator not found with id: " + coordinatorId));

        modelMapper.map(coordinatorDTORequest, coordinator);
        return coordinatorRepository.save(coordinator);
    }

    public void deleteCoordinator(Long coordinatorId) {
        Coordinator coordinator = coordinatorRepository.findById(coordinatorId)
                .orElseThrow(() -> new CoordinatorNotFoundException("Coordinator not found with id: " + coordinatorId));

        coordinatorRepository.delete(coordinator);
    }
}
