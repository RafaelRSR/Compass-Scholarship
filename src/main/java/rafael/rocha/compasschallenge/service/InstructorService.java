package rafael.rocha.compasschallenge.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.entity.Instructor;
import rafael.rocha.compasschallenge.exceptions.InstructorNotFoundException;
import rafael.rocha.compasschallenge.repository.InstructorRepository;
import rafael.rocha.compasschallenge.dtos.instructor.InstructorDTORequest;
import rafael.rocha.compasschallenge.dtos.instructor.InstructorDTOResponse;
import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor findById(Long instructorId) {
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found with id: " + instructorId));
    }

    public void createInstructor(InstructorDTORequest instructorDTORequest) {
        Instructor instructor = modelMapper.map(instructorDTORequest, Instructor.class);
        Instructor newInstructor = instructorRepository.save(instructor);

        modelMapper.map(newInstructor, InstructorDTOResponse.class);
    }

    public Instructor updateInstructor(Long instructorId, InstructorDTORequest instructorDTORequest) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found with id: " + instructorId));

        modelMapper.map(instructorDTORequest, instructor);
        return instructorRepository.save(instructor);
    }

    public void deleteInstructor(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found with id: " + instructorId));

        instructorRepository.delete(instructor);
    }
}
