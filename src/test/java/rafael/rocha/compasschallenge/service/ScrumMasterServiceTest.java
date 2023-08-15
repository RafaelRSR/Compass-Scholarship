package rafael.rocha.compasschallenge.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import rafael.rocha.compasschallenge.dtos.scrummaster.ScrumMasterDTORequest;
import rafael.rocha.compasschallenge.entity.ScrumMaster;
import rafael.rocha.compasschallenge.repository.ScrumMasterRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ScrumMasterServiceTest {

    @InjectMocks
    private ScrumMasterService scrumMasterService;

    @Mock
    private ScrumMasterRepository scrumMasterRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void getAllScrumMasters() {
        ScrumMaster scrumMaster1 = new ScrumMaster();
        scrumMaster1.setId(1L);
        ScrumMaster scrumMaster2 = new ScrumMaster();
        scrumMaster2.setId(2L);

        when(scrumMasterRepository.findAll()).thenReturn(Arrays.asList(scrumMaster1, scrumMaster2));

        List<ScrumMaster> allScrumMasters = scrumMasterService.getAllScrumMasters();

        verify(scrumMasterRepository, times(1)).findAll();

        assertEquals(2, allScrumMasters.size());
        assertEquals(1L, allScrumMasters.get(0).getId());
        assertEquals(2L, allScrumMasters.get(1).getId());
    }

    @Test
    void findById() {
        ScrumMaster scrumMaster = new ScrumMaster();
        scrumMaster.setId(1L);

        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.of(scrumMaster));

        ScrumMaster foundScrumMaster = scrumMasterService.findById(1L);

        verify(scrumMasterRepository, times(1)).findById(1L);

        assertEquals(1L, foundScrumMaster.getId());
    }

    @Test
    void createScrumMaster() {
        ScrumMasterDTORequest scrumMasterDTORequest = new ScrumMasterDTORequest();
        ScrumMaster newScrumMaster = new ScrumMaster();
        newScrumMaster.setId(1L);

        when(scrumMasterRepository.save(any())).thenReturn(newScrumMaster);

        scrumMasterService.createScrumMaster(scrumMasterDTORequest);

        verify(scrumMasterRepository, times(1)).save(any());
    }

    @Test
    void updateScrumMaster() {
        ScrumMasterDTORequest scrumMasterDTORequest = new ScrumMasterDTORequest();
        ScrumMaster existingScrumMaster = new ScrumMaster();
        existingScrumMaster.setId(1L);

        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.of(existingScrumMaster));
        when(scrumMasterRepository.save(any())).thenReturn(existingScrumMaster);

        ScrumMaster updatedScrumMaster = scrumMasterService.updateScrumMaster(1L, scrumMasterDTORequest);

        verify(scrumMasterRepository, times(1)).findById(1L);
        verify(scrumMasterRepository, times(1)).save(existingScrumMaster);

        assertEquals(1L, updatedScrumMaster.getId());
    }

    @Test
    void deleteScrumMaster() {
        ScrumMaster existingScrumMaster = new ScrumMaster();
        existingScrumMaster.setId(1L);

        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.of(existingScrumMaster));

        scrumMasterService.deleteScrumMaster(1L);

        verify(scrumMasterRepository, times(1)).findById(1L);
        verify(scrumMasterRepository, times(1)).delete(existingScrumMaster);
    }
}