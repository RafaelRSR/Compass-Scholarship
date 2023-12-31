package rafael.rocha.compasschallenge.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.student.StudentDTORequest;
import rafael.rocha.compasschallenge.dtos.student.StudentDTOResponse;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.repository.StudentRepository;


import java.util.List;


@Service
public class StudentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    public StudentDTOResponse createStudent(StudentDTORequest studentDTORequest) {
        Student studentEntity = modelMapper.map(studentDTORequest, Student.class);
        Student savedStudent = studentRepository.save(studentEntity);
        return modelMapper.map(savedStudent, StudentDTOResponse.class);
    }

    public Student findById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found!"));
    }

    public Student updateStudent(Long studentId, StudentDTORequest studentDTORequest) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Couldn't find a student with id: " + studentId));

        modelMapper.map(studentDTORequest, student);
        return studentRepository.save(student);
    }

    public void deleteStudentFromStudents(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Couldn't find a student with id: " + studentId));

        studentRepository.delete(student);
    }
}

