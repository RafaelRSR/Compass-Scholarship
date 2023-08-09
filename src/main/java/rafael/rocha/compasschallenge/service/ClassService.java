package rafael.rocha.compasschallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.ClassDTORequest;
import rafael.rocha.compasschallenge.dtos.ClassDTOResponse;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.entity.Coordinator;
import rafael.rocha.compasschallenge.entity.Instructor;
import rafael.rocha.compasschallenge.entity.ScrumMaster;
import rafael.rocha.compasschallenge.enums.ClassStatus;
import rafael.rocha.compasschallenge.exceptions.ClassroomNotFoundException;
import rafael.rocha.compasschallenge.repository.ClassRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;


    public Class findById(Long id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Class not found"));
    }

    public void startClass(ClassDTORequest classDTORequest) {
        validateClassSize(classDTORequest);
        validateClassStaff(classDTORequest);
        Class classEntity = new Class();
        classEntity.setStatus(ClassStatus.STARTED);
        System.out.println("Class STARTED!");
    }

    public void validateClassSize(ClassDTORequest classDTORequest) {
        int currentSize = classDTORequest.getStudentList().size();

        if (!(currentSize >= 15 && currentSize < 30)) {
            throw new IllegalArgumentException("Class size not accepted");
        }
    }

    public void validateClassStaff(ClassDTORequest classDTORequest) {
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

        ClassDTOResponse classDTOResponse = new ClassDTOResponse();
        classDTOResponse.setId(classEntity.getId());
        classDTOResponse.setStudentList(classEntity.getStudentList());
        classDTOResponse.setCoordinatorAssigned(classEntity.getCoordinatorAssigned());
        classDTOResponse.setInstructorsAssigned(classEntity.getInstructorsAssigned());
        classDTOResponse.setScrumMasterAssigned(classEntity.getScrumMasterAssigned());

        return classDTOResponse;
    }

    public void createClass(ClassDTOResponse classDTORequest) {
        Class classEntity = new Class();
        classEntity.setStatus(classDTORequest.getStatus());
        classEntity.setStudentList(classDTORequest.getStudentList());
        classEntity.setCoordinatorAssigned(classDTORequest.getCoordinatorAssigned());
        classEntity.setScrumMasterAssigned(classDTORequest.getScrumMasterAssigned());
        classEntity.setInstructorsAssigned(classDTORequest.getInstructorsAssigned());
        classEntity.setSquadList(classDTORequest.getSquadList());

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
}