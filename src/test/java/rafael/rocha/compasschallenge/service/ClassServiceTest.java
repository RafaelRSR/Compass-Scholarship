package rafael.rocha.compasschallenge.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import rafael.rocha.compasschallenge.dtos.classroom.ClassDTOResponse;
import rafael.rocha.compasschallenge.dtos.squad.SquadDTORequest;
import rafael.rocha.compasschallenge.entity.*;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.enums.ClassStatus;
import rafael.rocha.compasschallenge.exceptions.ClassroomNotFoundException;
import rafael.rocha.compasschallenge.exceptions.SquadNotFoundException;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClassServiceTest {

    @InjectMocks
    private ClassService classService;

    @Mock
    private ClassRepository classRepository;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SquadRepository squadRepository;

    @Mock
    private CoordinatorRepository coordinatorRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private ScrumMasterRepository scrumMasterRepository;


    @Test
    void getAllClasses() {
        when(classRepository.findAll()).thenReturn(Arrays.asList(new Class(), new Class()));

        List<Class> classes = classService.getAllClasses();

        assertEquals(2, classes.size());
    }

    @Test
    void startClass() {
        Class sampleClass = new Class();
        sampleClass.setId(1L);
        sampleClass.setStatus(ClassStatus.WAITING);
        sampleClass.setStudentList(new ArrayList<>());
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            Student student = new Student();
            student.setId((long) i);
            students.add(student);
        }
        sampleClass.setStudentList(students);

        Coordinator coordinator = new Coordinator();
        ScrumMaster scrumMaster = new ScrumMaster();
        List<Instructor> instructors = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            instructors.add(new Instructor());
        }
        sampleClass.setCoordinatorAssigned(coordinator);
        sampleClass.setScrumMasterAssigned(scrumMaster);
        sampleClass.setInstructorsAssigned(instructors);


        List<Squad> squads = new ArrayList<>();
        Squad squad1 = new Squad();
        Squad squad2 = new Squad();
        squads.add(squad1);
        squads.add(squad2);
        sampleClass.setSquadList(squads);

        when(classRepository.findById(1L)).thenReturn(Optional.of(sampleClass));

        classService.startClass(1L);

        verify(classRepository, times(1)).findById(1L);

        assertEquals(ClassStatus.STARTED, sampleClass.getStatus());
    }

    @Test
    void startClassClassroomNotFoundException() {

        when(classRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> classService.startClass(1L));

        verify(classRepository, times(1)).findById(1L);
    }

    @Test
    void getClassMembers() {
        Class sampleClass = new Class();
        sampleClass.setId(1L);

        when(classRepository.findById(1L)).thenReturn(Optional.of(sampleClass));

        ClassDTOResponse dtoResponse = classService.getClassMembers(1L);

        verify(classRepository, times(1)).findById(1L);

        verify(modelMapper, times(1)).map(sampleClass, ClassDTOResponse.class);

        assertEquals(1L, dtoResponse.getId());
    }

    @Test
    void createClass() {
        ClassDTOResponse dtoRequest = new ClassDTOResponse();

        Class classEntity = new Class();
        classEntity.setId(1L);
        when(modelMapper.map(dtoRequest, Class.class)).thenReturn(classEntity);

        classService.createClass(dtoRequest);

        verify(modelMapper, times(1)).map(dtoRequest, Class.class);

        verify(classRepository, times(1)).save(classEntity);

        assertEquals(ClassStatus.WAITING, classEntity.getStatus());
    }

    @Test
    void finishClass() {
        ClassDTOResponse dtoRequest = new ClassDTOResponse();

        Class classEntity = new Class();
        classEntity.setId(1L);
        when(modelMapper.map(dtoRequest, Class.class)).thenReturn(classEntity);

        classService.createClass(dtoRequest);

        verify(modelMapper, times(1)).map(dtoRequest, Class.class);

        verify(classRepository, times(1)).save(classEntity);

        assertEquals(ClassStatus.WAITING, classEntity.getStatus());
    }

    @Test
    void finishClassClassroomNotFoundException() {
        when(classRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> classService.finishClass(1L));

        verify(classRepository, times(1)).findById(1L);
    }

    @Test
    void finishClassIllegalStateException() {

        Class waitingClass = new Class();
        waitingClass.setId(2L);
        waitingClass.setStatus(ClassStatus.WAITING);

        when(classRepository.findById(2L)).thenReturn(Optional.of(waitingClass));

        assertThrows(IllegalStateException.class, () -> classService.finishClass(2L));

        verify(classRepository, times(1)).findById(2L);
    }

    @Test
    void addStudentToClass() {
        Class classEntity = new Class();
        classEntity.setId(1L);
        classEntity.setStudentList(new ArrayList<>()); // Initialize the studentList

        Student student = new Student();
        student.setId(2L);

        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));
        when(studentRepository.findById(2L)).thenReturn(Optional.of(student));

        classService.addStudentToClass(1L, 2L);

        verify(classRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).findById(2L);

        assertEquals(1L, student.getClassAssigned());
        assertTrue(classEntity.getStudentList().contains(student));
        verify(classRepository, times(1)).save(classEntity);
    }
    @Test
    void addStudentToClassClassroomNotFoundException() {
        when(classRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> classService.addStudentToClass(1L, 2L));

        verify(classRepository, times(1)).findById(1L);
    }

    @Test
    void addStudentToClassStudentNotFoundException() {
        when(classRepository.findById(1L)).thenReturn(Optional.of(new Class()));

        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> classService.addStudentToClass(1L, 2L));

        verify(classRepository, times(1)).findById(1L);

        verify(studentRepository, times(1)).findById(2L);
    }

    @Test
    void deleteStudentFromClass() {
        Class classEntity = new Class();
        classEntity.setId(1L);

        List<Student> studentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(2L);
        Student student2 = new Student();
        student2.setId(3L);
        studentList.add(student1);
        studentList.add(student2);
        classEntity.setStudentList(studentList);

        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));

        classService.deleteStudentFromClass(1L, 2L);

        verify(classRepository, times(1)).findById(1L);

        verify(classRepository, times(1)).save(classEntity);

        assertFalse(classEntity.getStudentList().stream().anyMatch(student -> student.getId().equals(2L)));
    }

    @Test
    void addSquadToClass() {
        Class classEntity = new Class();
        classEntity.setId(1L);
        classEntity.setSquadList(new ArrayList<>());

        SquadDTORequest squadDTORequest = new SquadDTORequest();

        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));

        classService.addSquadToClass(1L, squadDTORequest);

        verify(classRepository, times(1)).findById(1L);
        verify(classRepository, times(1)).save(classEntity);
    }

    @Test
    void deleteSquadFromClass() {
        Class classEntity = new Class();
        classEntity.setId(1L);
        classEntity.setSquadList(new ArrayList<>());

        Squad squadToRemove = new Squad();
        squadToRemove.setId(2L);

        classEntity.getSquadList().add(squadToRemove);

        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));

        classService.deleteSquadFromClass(1L, 2L);

        verify(classRepository, times(1)).findById(1L);
        verify(classRepository, times(1)).save(classEntity);

        assertFalse(classEntity.getSquadList().stream().anyMatch(squad -> squad.getId().equals(2L)));
    }

    @Test
    void deleteSquadFromClassSquadNotFoundException() {
        Class classEntity = new Class();
        classEntity.setId(1L);
        classEntity.setSquadList(new ArrayList<>());

        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));

        assertThrows(SquadNotFoundException.class, () -> classService.deleteSquadFromClass(1L, 2L));

        verify(classRepository, times(1)).findById(1L);
    }

    @Test
    void populateStaff() {
        Class classEntity = new Class();
        classEntity.setId(1L);

        ScrumMaster availableScrumMaster = new ScrumMaster();
        availableScrumMaster.setId(1L);

        List<Instructor> availableInstructors = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Instructor instructor = new Instructor();
            instructor.setId((long) i);
            availableInstructors.add(instructor);
        }
        classEntity.setInstructorsAssigned(availableInstructors);

        Coordinator availableCoordinator = new Coordinator();
        availableCoordinator.setId(1L);

        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));

        when(scrumMasterRepository.findOneScrumMaster()).thenReturn(availableScrumMaster);

        when(instructorRepository.findThreeInstructors()).thenReturn(availableInstructors);

        when(coordinatorRepository.findOneCoordinator()).thenReturn(availableCoordinator);

        assertDoesNotThrow(() -> classService.populateStaff(1L));

        verify(classRepository, times(1)).findById(1L);

        verify(scrumMasterRepository, times(1)).findOneScrumMaster();

        verify(instructorRepository, times(1)).findThreeInstructors();

        verify(coordinatorRepository, times(1)).findOneCoordinator();

        verify(classRepository, times(1)).save(classEntity);
    }

    @Test
    void populateClassWithStudents() {
        Class classEntity = new Class();
        classEntity.setId(1L);
        classEntity.setStudentList(new ArrayList<>());

        List<Student> existingStudents = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            Student student = new Student();
            student.setId((long) i);
            existingStudents.add(student);
        }
        classEntity.setStudentList(existingStudents);

        List<Student> allStudents = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Student student = new Student();
            student.setId((long) i);
            allStudents.add(student);
        }

        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));

        when(studentRepository.findAll()).thenReturn(allStudents);

        assertDoesNotThrow(() -> classService.populateClassWithStudents(1L));

        verify(classRepository, times(1)).findById(1L);

        verify(studentRepository, times(1)).findAll();

        verify(classRepository, times(1)).save(classEntity);
    }

    @Test
    void addSquadsToClassWithStudents() {
        Class classEntity = new Class();
        classEntity.setId(1L);
        classEntity.setStudentList(new ArrayList<>());

        List<Student> existingStudents = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            Student student = new Student();
            student.setId((long) i);
            existingStudents.add(student);
        }
        classEntity.setStudentList(existingStudents);

        List<Student> allStudents = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Student student = new Student();
            student.setId((long) i);
            allStudents.add(student);
        }

        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));

        when(studentRepository.findAll()).thenReturn(allStudents);

        assertDoesNotThrow(() -> classService.populateClassWithStudents(1L));

        verify(classRepository, times(1)).findById(1L);

        verify(studentRepository, times(1)).findAll();

        verify(classRepository, times(1)).save(classEntity);
    }
}