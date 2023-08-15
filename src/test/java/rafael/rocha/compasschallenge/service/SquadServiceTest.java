package rafael.rocha.compasschallenge.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import rafael.rocha.compasschallenge.dtos.squad.SquadDTORequest;
import rafael.rocha.compasschallenge.entity.Squad;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.repository.SquadRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
class SquadServiceTest {


    @InjectMocks
    private SquadService squadService;

    @Mock
    private SquadRepository squadRepository;

    @Spy
    private ModelMapper modelMapper;


    @Test
    void createSquad() {
        SquadDTORequest squadDTORequest = new SquadDTORequest();
        Squad squadEntity = new Squad();
        squadEntity.setId(1L);

        when(squadRepository.save(any())).thenReturn(squadEntity);

        Squad createdSquad = squadService.createSquad(squadDTORequest);

        assertNotNull(createdSquad);
        assertEquals(1L, createdSquad.getId());

        verify(squadRepository, times(1)).save(any());
    }

    @Test
    void findSquadById() {
        Squad squadEntity = new Squad();
        squadEntity.setId(1L);

        when(squadRepository.findById(1L)).thenReturn(Optional.of(squadEntity));

        Squad foundSquad = squadService.findSquadById(1L);

        assertNotNull(foundSquad);
        assertEquals(1L, foundSquad.getId());

        verify(squadRepository, times(1)).findById(1L);
    }

    @Test
    void deleteSquad() {
        Squad squadEntity = new Squad();
        squadEntity.setId(1L);

        when(squadRepository.findById(1L)).thenReturn(Optional.of(squadEntity));

        squadService.deleteSquad(1L);

        verify(squadRepository, times(1)).delete(squadEntity);
    }

    @Test
    void addStudentsToSquad() {
        Squad squadEntity = new Squad();
        squadEntity.setId(1L);
        squadEntity.setStudentList(new ArrayList<>());

        Student student = new Student();
        student.setId(2L);

        when(squadRepository.findById(1L)).thenReturn(Optional.of(squadEntity));

        squadService.addStudentsToSquad(1L, student);

        verify(squadRepository, times(1)).save(squadEntity);
        assertTrue(squadEntity.getStudentList().contains(student));
    }

    @Test
    void deleteStudentFromSquad() {
        Squad squadEntity = new Squad();
        squadEntity.setId(1L);
        squadEntity.setStudentList(new ArrayList<>());

        Student student = new Student();
        student.setId(2L);

        squadEntity.getStudentList().add(student);

        when(squadRepository.findById(1L)).thenReturn(Optional.of(squadEntity));

        squadService.deleteStudentFromSquad(1L, 2L);

        verify(squadRepository, times(1)).save(squadEntity);
        assertFalse(squadEntity.getStudentList().contains(student));
    }
}