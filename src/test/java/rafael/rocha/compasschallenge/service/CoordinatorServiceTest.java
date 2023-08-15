package rafael.rocha.compasschallenge.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import rafael.rocha.compasschallenge.dtos.coordinator.CoordinatorDTORequest;
import rafael.rocha.compasschallenge.dtos.coordinator.CoordinatorDTOResponse;
import rafael.rocha.compasschallenge.entity.Coordinator;
import rafael.rocha.compasschallenge.repository.*;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CoordinatorServiceTest {

    @InjectMocks
    private CoordinatorService coordinatorService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private CoordinatorRepository coordinatorRepository;


    @Test
    void getAllCoordinators() {

        Coordinator coordinator1 = new Coordinator();
        coordinator1.setId(1L);
        Coordinator coordinator2 = new Coordinator();
        coordinator2.setId(2L);

        when(coordinatorRepository.findAll()).thenReturn(Arrays.asList(coordinator1, coordinator2));

        List<Coordinator> result = coordinatorService.getAllCoordinators();

        assertEquals(2, result.size());
        assertEquals(coordinator1.getId(), result.get(0).getId());
        assertEquals(coordinator2.getId(), result.get(1).getId());
    }


    @Test
    void findById() {
        Coordinator coordinator = new Coordinator();
        coordinator.setId(1L);

        when(coordinatorRepository.findById(1L)).thenReturn(Optional.of(coordinator));

        Coordinator result = coordinatorService.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void createCoordinator() {
        CoordinatorDTORequest request = new CoordinatorDTORequest();

        Coordinator coordinator = new Coordinator();
        coordinator.setId(1L);

        when(modelMapper.map(request, Coordinator.class)).thenReturn(coordinator);

        when(coordinatorRepository.save(coordinator)).thenReturn(coordinator);

        coordinatorService.createCoordinator(request);

        verify(modelMapper, times(1)).map(request, Coordinator.class);

        verify(coordinatorRepository, times(1)).save(coordinator);

        verify(modelMapper, times(1)).map(coordinator, CoordinatorDTOResponse.class);
    }

    @Test
    void updateCoordinator() {
        CoordinatorDTORequest request = new CoordinatorDTORequest();
        request.setFirstName("Updated John Doe");

        Coordinator existingCoordinator = new Coordinator();
        existingCoordinator.setId(1L);
        existingCoordinator.setFirstName("John Doe");

        when(coordinatorRepository.findById(1L)).thenReturn(Optional.of(existingCoordinator));

        when(modelMapper.map(request, Coordinator.class)).thenReturn(existingCoordinator);

        when(coordinatorRepository.save(existingCoordinator)).thenReturn(existingCoordinator);

        Coordinator updatedCoordinator = coordinatorService.updateCoordinator(1L, request);

        verify(coordinatorRepository, times(1)).findById(1L);

        verify(modelMapper, times(1)).map(request, existingCoordinator);

        verify(coordinatorRepository, times(1)).save(existingCoordinator);

        assertEquals(existingCoordinator, updatedCoordinator);
    }

    @Test
    void deleteCoordinator() {
        Coordinator existingCoordinator = new Coordinator();
        existingCoordinator.setId(1L);

        when(coordinatorRepository.findById(1L)).thenReturn(Optional.of(existingCoordinator));

        coordinatorService.deleteCoordinator(1L);

        verify(coordinatorRepository, times(1)).findById(1L);

        verify(coordinatorRepository, times(1)).delete(existingCoordinator);
    }
}