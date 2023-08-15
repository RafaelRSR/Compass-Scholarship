package rafael.rocha.compasschallenge.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import rafael.rocha.compasschallenge.dtos.student.StudentDTORequest;
import rafael.rocha.compasschallenge.dtos.student.StudentDTOResponse;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.repository.StudentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void getAllStudents() {
        Student student1 = new Student();
        student1.setId(1L);

        Student student2 = new Student();
        student2.setId(2L);

        List<Student> expectedStudents = Arrays.asList(student1, student2);

        when(studentRepository.findAll()).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.getAllStudents();

        assertEquals(expectedStudents, actualStudents);

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void createStudent() {
        StudentDTORequest studentDTORequest = new StudentDTORequest();
        Student studentEntity = new Student();
        StudentDTOResponse studentDTOResponse = new StudentDTOResponse();

        when(modelMapper.map(studentDTORequest, Student.class)).thenReturn(studentEntity);
        when(studentRepository.save(studentEntity)).thenReturn(studentEntity);
        when(modelMapper.map(studentEntity, StudentDTOResponse.class)).thenReturn(studentDTOResponse);

        StudentDTOResponse createdStudent = studentService.createStudent(studentDTORequest);

        assertEquals(studentDTOResponse, createdStudent);

        verify(modelMapper, times(1)).map(studentDTORequest, Student.class);
        verify(studentRepository, times(1)).save(studentEntity);
        verify(modelMapper, times(1)).map(studentEntity, StudentDTOResponse.class);
    }

    @Test
    void findById() {
        Student studentEntity = new Student();
        studentEntity.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentEntity));

        Student foundStudent = studentService.findById(1L);

        assertEquals(studentEntity, foundStudent);

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void updateStudent() {
        StudentDTORequest studentDTORequest = new StudentDTORequest();
        Student studentEntity = new Student();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentEntity));
        when(studentRepository.save(studentEntity)).thenReturn(studentEntity);

        Student updatedStudent = studentService.updateStudent(1L, studentDTORequest);

        assertEquals(studentEntity, updatedStudent);

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(studentEntity);
        verify(modelMapper, times(1)).map(studentDTORequest, studentEntity);
    }

    @Test
    void deleteStudentFromStudents() {
        Student studentEntity = new Student();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentEntity));

        studentService.deleteStudentFromStudents(1L);

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).delete(studentEntity);
    }
}