package rafael.rocha.compasschallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.ClassDTO;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.entity.Coordinator;
import rafael.rocha.compasschallenge.entity.Instructor;
import rafael.rocha.compasschallenge.entity.ScrumMaster;
import rafael.rocha.compasschallenge.enums.ClassStatus;
import rafael.rocha.compasschallenge.repository.ClassRepository;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public Class findById(Long id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Class not found"));
    }

    public void startClass(ClassDTO classDTO) {
        validateClassSize(classDTO);
        validateClassStaff(classDTO);
        classDTO.setStatus(ClassStatus.STARTED);
        System.out.println("Class STARTED!");
    }

    public void validateClassSize(ClassDTO classDTO) {
        int currentSize = classDTO.getStudentList().size();

        if (!(currentSize >= 15 && currentSize < 30)) {
            throw new IllegalArgumentException("Class size not accepted");
        }
    }

    public void validateClassStaff(ClassDTO classDTO) {
        Coordinator coordinatorAssigned = classDTO.getCoordinatorAssigned();
        ScrumMaster scrumMasterAssigned = classDTO.getScrumMasterAssigned();
        List<Instructor> instructorsAssigned = classDTO.getInstructorsAssigned();

        if (coordinatorAssigned == null || scrumMasterAssigned == null || instructorsAssigned.size() < 3) {
            throw new IllegalArgumentException("Staff size not enough!");
        }
    }

    public ClassDTO getClassMembers(Long classId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find class"));

        ClassDTO classDTO = new ClassDTO();
        classDTO.setId(classEntity.getId());
        classDTO.setStudentList(classEntity.getStudentList());
        classDTO.setCoordinatorAssigned(classEntity.getCoordinatorAssigned());
        classDTO.setInstructorsAssigned(classEntity.getInstructorsAssigned());
        classDTO.setScrumMasterAssigned(classEntity.getScrumMasterAssigned());

        return classDTO;
    }

    public void createClass(ClassDTO classDTO) {

        Class classEntity = new Class();
        classRepository.save(classEntity);
    }
}