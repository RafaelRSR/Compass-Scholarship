package rafael.rocha.compasschallenge.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.classroom.ClassDTOResponse;
import rafael.rocha.compasschallenge.dtos.squad.SquadDTORequest;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.entity.*;
import rafael.rocha.compasschallenge.enums.ClassStatus;
import rafael.rocha.compasschallenge.exceptions.*;
import rafael.rocha.compasschallenge.repository.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SquadRepository squadRepository;

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ScrumMasterRepository scrumMasterRepository;

    public Class findById(Long id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Class not found"));
    }

    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    public void startClass(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        validateClassSize(classEntity);
        validateClassStaff(classEntity);
        classEntity.setStatus(ClassStatus.STARTED);
    }

    public void validateClassSize(Class classDTORequest) {
        int currentSize = classDTORequest.getStudentList().size();

        if (!(currentSize >= 15 && currentSize < 30)) {
            throw new AmountStudentsException("Class size not accepted");
        }
    }

    public void validateClassStaff(Class classDTORequest) {
        Coordinator coordinatorAssigned = classDTORequest.getCoordinatorAssigned();
        ScrumMaster scrumMasterAssigned = classDTORequest.getScrumMasterAssigned();
        List<Instructor> instructorsAssigned = classDTORequest.getInstructorsAssigned();

        if (coordinatorAssigned == null || scrumMasterAssigned == null || instructorsAssigned.size() < 3) {
            throw new IllegalArgumentException("Staff size not enough!");
        }
    }


    public ClassDTOResponse getClassMembers(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find class with id: " + classId));
        return modelMapper.map(classEntity, ClassDTOResponse.class);
    }

    public void createClass(ClassDTOResponse classDTORequest) {
        Class classEntity = modelMapper.map(classDTORequest, Class.class);
        classEntity.setStatus(ClassStatus.WAITING);
        classRepository.save(classEntity);
    }

    public void finishClass(Long classId) {
        Optional<Class> optionalClass = classRepository.findById(classId);
        if (optionalClass.isPresent()) {
            Class classToFinish = optionalClass.get();
            if (classToFinish.getStatus() == ClassStatus.STARTED) {
                classToFinish.setStatus(ClassStatus.FINISHED);
                classRepository.save(classToFinish);
            } else {
                throw new IllegalStateException("Class is not in the 'started' status.");
            }
        } else {
            throw new ClassroomNotFoundException("Class not found with ID: " + classId);
        }
    }

    @Transactional
    public void addStudentToClass(Long classId, Long studentId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        Student newStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Couldn't find student with id:" + studentId));

        newStudent.setClassAssigned(classId);
        classEntity.getStudentList().add(newStudent);
        classRepository.save(classEntity);
    }

    @Transactional
    public void deleteStudentFromClass(Long classId, Long studentId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        List<Student> studentList = classEntity.getStudentList();
        studentList.removeIf(student -> student.getId().equals(studentId));
        classRepository.save(classEntity);
    }

    @Transactional
    public void addSquadToClass(Long classId, SquadDTORequest squadDTORequest) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        Squad newSquad = modelMapper.map(squadDTORequest, Squad.class);
        newSquad.setClassAssigned(classId);

        classEntity.getSquadList().add(newSquad);
        classRepository.save(classEntity);
    }

    @Transactional
    public void deleteSquadFromClass(Long classId, Long squadId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        Squad squadToRemove = classEntity.getSquadList().stream()
                .filter(squad -> squad.getId().equals(squadId))
                .findFirst()
                .orElseThrow(() -> new SquadNotFoundException("Squad not found"));

        classEntity.getSquadList().remove(squadToRemove);
        classRepository.save(classEntity);
    }


    @Transactional
    public void addCoordinatorToClass(Long classId, Long coordinatorId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        Coordinator coordinator = coordinatorRepository.findById(coordinatorId)
                .orElseThrow(() -> new CoordinatorNotFoundException("Coordinator not found with id: " + coordinatorId));

        coordinator.setClassAssigned(classId);
        classEntity.setCoordinatorAssigned(coordinator);
        classRepository.save(classEntity);
    }

    @Transactional
    public void deleteCoordinatorFromClass(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        classEntity.setCoordinatorAssigned(null);
        classRepository.save(classEntity);
    }

    @Transactional
    public void addInstructorsToClass(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        long availableInstructors = instructorRepository.count();
        if (availableInstructors < 3) {
            throw new InstructorNotFoundException("Not enough instructors available");
        }

        List<Instructor> instructors = instructorRepository.findThreeInstructors();
        classEntity.getInstructorsAssigned().addAll(instructors);
        classRepository.save(classEntity);
    }

    @Transactional
    public void deleteInstructorFromClass(Long classId, Long instructorId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found with id: " + instructorId));
        classRepository.save(classEntity);

        classEntity.getInstructorsAssigned().remove(instructor);
        classRepository.save(classEntity);
    }

    @Transactional
    public void addScrumMasterToClass(Long classId, Long scrumMasterId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        ScrumMaster scrumMaster = scrumMasterRepository.findById(scrumMasterId)
                .orElseThrow(() -> new ScrumMasterNotFoundException("Scrum Master not found with id: " + scrumMasterId));

        scrumMaster.setClassAssigned(classId);
        classEntity.setScrumMasterAssigned(scrumMaster);
        classRepository.save(classEntity);
    }

    @Transactional
    public void deleteScrumMasterFromClass(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        classEntity.setScrumMasterAssigned(null);
        classRepository.save(classEntity);
    }

    @Transactional
    public void populateStaff(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        if (classEntity.getScrumMasterAssigned() == null) {
            ScrumMaster availableScrumMaster = scrumMasterRepository.findOneScrumMaster();
            if (availableScrumMaster != null) {
                availableScrumMaster.setClassAssigned(classId);
                classEntity.setScrumMasterAssigned(availableScrumMaster);
            }
        }

        List<Instructor> availableInstructors = instructorRepository.findThreeInstructors();
        if (availableInstructors.size() < 3) {
            throw new InstructorNotFoundException("Not enough instructors available");
        }

        for (Instructor instructor : availableInstructors) {
            if (!classEntity.getInstructorsAssigned().contains(instructor)) {
                instructor.setClassAssigned(classId);
                classEntity.getInstructorsAssigned().add(instructor);
            }
        }

        if (classEntity.getCoordinatorAssigned() == null) {
            Coordinator availableCoordinator = coordinatorRepository.findOneCoordinator();
            if (availableCoordinator != null) {
                availableCoordinator.setClassAssigned(classId);
                classEntity.setCoordinatorAssigned(availableCoordinator);
            }
        }
        classRepository.save(classEntity);
    }

    @Transactional
    public void populateClassWithStudents(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        List<Student> existingStudents = classEntity.getStudentList();

        List<Student> studentsToAdd = studentRepository.findAll();
        studentsToAdd.removeAll(existingStudents);

        int maxStudentsToAdd = Math.min(30 - existingStudents.size(), studentsToAdd.size());

        if (maxStudentsToAdd <= 0) {
            throw new MaxStudentsException("No more students to add to the class");
        }
        List<Student> studentsToAddToClass = studentsToAdd.subList(0, maxStudentsToAdd);

        classEntity.getStudentList().addAll(studentsToAddToClass);
        for (Student student : studentsToAddToClass) {
            if (classEntity.getStudentList().contains(student)) {
                student.setClassAssigned(classId);
            }
        }
        classRepository.save(classEntity);
    }
    @Transactional
    public void addSquadsToClassWithStudents(Long classId) {
        int maxStudentsPerSquad = 5;
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        List<Student> students = classEntity.getStudentList();
        Collections.shuffle(students);

        int numOfSquads = (int) Math.ceil((double) students.size() / maxStudentsPerSquad);

        List<Squad> squadsToAddStudents = new ArrayList<>();

        for (int i = 1; i <= numOfSquads; i++) {
            Squad newSquad = new Squad();
            newSquad.setClassAssigned(classId);
            squadRepository.save(newSquad);

            int fromIndex = (i - 1) * maxStudentsPerSquad;
            int toIndex = Math.min(i * maxStudentsPerSquad, students.size());
            List<Student> studentsToAdd = students.subList(fromIndex, toIndex);

            newSquad.getStudentList().addAll(studentsToAdd);
            squadsToAddStudents.add(newSquad);
        }

        classEntity.getSquadList().addAll(squadsToAddStudents);
        classRepository.save(classEntity);
    }
}
