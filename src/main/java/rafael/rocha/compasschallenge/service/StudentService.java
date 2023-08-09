package rafael.rocha.compasschallenge.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.StudentDTORequest;
import rafael.rocha.compasschallenge.dtos.StudentDTOResponse;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.repository.StudentRepository;


@Service
public class StudentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentRepository studentRepository;

    public StudentDTOResponse createStudent(StudentDTORequest studentDTORequest) {
        Student studentEntity = modelMapper.map(studentDTORequest, Student.class);
        Student savedStudent = studentRepository.save(studentEntity);
        return modelMapper.map(savedStudent, StudentDTOResponse.class);
    }

    public Student findById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found!"));
    }

}
