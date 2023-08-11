package rafael.rocha.compasschallenge.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.ClassDTOResponse;
import rafael.rocha.compasschallenge.dtos.StudentDTORequest;
import rafael.rocha.compasschallenge.entity.*;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.enums.ClassStatus;
import rafael.rocha.compasschallenge.exceptions.ClassroomNotFoundException;
import rafael.rocha.compasschallenge.repository.ClassRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ModelMapper modelMapper;


    public Class findById(Long id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Class not found"));
    }

    public void startClass(Long classId) {
        Class classEntity = classRepository.findById(classId)
                        .orElseThrow(() -> new ClassroomNotFoundException("Classroom not found!"));
        classEntity.setStatus(ClassStatus.STARTED);
        System.out.println("Class STARTED!");
    }

    public void validateClassSize(Class classDTORequest) {
        int currentSize = classDTORequest.getStudentList().size();

        if (!(currentSize >= 15 && currentSize < 30)) {
            throw new IllegalArgumentException("Class size not accepted");
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
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find class"));
        return modelMapper.map(classEntity, ClassDTOResponse.class);
    }

    public void createClass(ClassDTOResponse classDTORequest) {
        Class classEntity = modelMapper.map(classDTORequest, Class.class);
        classRepository.save(classEntity);
    }

    public Class finishClass(Long classId) {
        Optional<Class> optionalClass = classRepository.findById(classId);
        if (((Optional<?>) optionalClass).isPresent()) {
            Class classToFinish = optionalClass.get();
            if (classToFinish.getStatus() == ClassStatus.STARTED) {
                classToFinish.setStatus(ClassStatus.FINISHED);
                return classRepository.save(classToFinish);
            } else {
                throw new IllegalStateException("Class is not in the 'started' status.");
            }
        } else {
            throw new ClassroomNotFoundException("Class not found with ID: " + classId);
        }
    }

    @Transactional
    public void addStudentToClass(Long classId, StudentDTORequest studentDTORequest) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        Student newStudent = modelMapper.map(studentDTORequest, Student.class);
        newStudent.setClassAssigned(classEntity);

        classEntity.getStudentList().add(newStudent);
        classRepository.save(classEntity);
    }

    public void deleteStudentFromClass(Long classId, Long studentId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        List<Student> studentList = classEntity.getStudentList();
        studentList.removeIf(student -> student.getId().equals(studentId));
        classRepository.save(classEntity);
    }
}