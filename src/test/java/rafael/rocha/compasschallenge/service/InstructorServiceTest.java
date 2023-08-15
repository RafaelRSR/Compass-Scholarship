package rafael.rocha.compasschallenge.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import rafael.rocha.compasschallenge.dtos.instructor.InstructorDTORequest;
import rafael.rocha.compasschallenge.entity.Instructor;
import rafael.rocha.compasschallenge.repository.InstructorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class InstructorServiceTest {

    @InjectMocks
    private InstructorService instructorService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private InstructorRepository instructorRepository;


    @Test
    void getAllInstructors() {
        Instructor instructor1 = new Instructor();
        instructor1.setId(1L);
        Instructor instructor2 = new Instructor();
        instructor2.setId(2L);

        when(instructorRepository.findAll()).thenReturn(Arrays.asList(instructor1, instructor2));

        List<Instructor> result = instructorService.getAllInstructors();

        assertEquals(2, result.size());
        assertEquals(instructor1.getId(), result.get(0).getId());
        assertEquals(instructor2.getId(), result.get(1).getId());
    }

    @Test
    void findById() {
        Instructor existingInstructor = new Instructor();
        existingInstructor.setId(1L);

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(existingInstructor));

        Instructor foundInstructor = instructorService.findById(1L);

        verify(instructorRepository, times(1)).findById(1L);

        assertEquals(existingInstructor, foundInstructor);
    }

    @Test
    void createInstructor() {
        InstructorDTORequest instructorDTORequest = new InstructorDTORequest();
        instructorDTORequest.setFirstName("John Doe");

        Instructor createdInstructor = new Instructor();
        createdInstructor.setId(1L);
        createdInstructor.setFirstName("John Doe");

        when(instructorRepository.save(any(Instructor.class))).thenReturn(createdInstructor);

        instructorService.createInstructor(instructorDTORequest);

        verify(instructorRepository, times(1)).save(any(Instructor.class));
    }

    @Test
    void updateInstructor() {
        InstructorDTORequest instructorDTORequest = new InstructorDTORequest();
        instructorDTORequest.setFirstName("Updated Name");

        Instructor existingInstructor = new Instructor();
        existingInstructor.setId(1L);
        existingInstructor.setFirstName("Original Name");

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(existingInstructor));

        when(instructorRepository.save(any(Instructor.class))).thenReturn(existingInstructor);

        Instructor updatedInstructor = instructorService.updateInstructor(1L, instructorDTORequest);

        verify(instructorRepository, times(1)).findById(1L);

        verify(instructorRepository, times(1)).save(any(Instructor.class));

        assertEquals("Updated Name", updatedInstructor.getFirstName());
    }

    @Test
    void deleteInstructor() {
        Instructor existingInstructor = new Instructor();
        existingInstructor.setId(1L);
        existingInstructor.setFirstName("John Doe");

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(existingInstructor));

        assertDoesNotThrow(() -> instructorService.deleteInstructor(1L));

        verify(instructorRepository, times(1)).findById(1L);

        verify(instructorRepository, times(1)).delete(existingInstructor);
    }
}