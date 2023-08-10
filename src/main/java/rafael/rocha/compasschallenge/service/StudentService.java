package rafael.rocha.compasschallenge.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.StudentDTORequest;
import rafael.rocha.compasschallenge.dtos.StudentDTOResponse;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.exceptions.ClassroomNotFoundException;
import rafael.rocha.compasschallenge.exceptions.StudentNotFoundException;
import rafael.rocha.compasschallenge.repository.ClassRepository;
import rafael.rocha.compasschallenge.repository.StudentRepository;

import java.util.List;


@Service
public class StudentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    public StudentDTOResponse createStudent(StudentDTORequest studentDTORequest) {
        Student studentEntity = modelMapper.map(studentDTORequest, Student.class);
        Student savedStudent = studentRepository.save(studentEntity);
        return modelMapper.map(savedStudent, StudentDTOResponse.class);
    }

    public Student findById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found!"));
    }

    public void deleteStudentById(Long classId, Long studentId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassroomNotFoundException("Couldn't find class"));

        List<Student> studentList = classEntity.getStudentList();
        studentList.removeIf(student -> student.getId().equals(studentId));
        classRepository.save(classEntity);
    }

    public Student updateStudent(Long studentId, StudentDTORequest studentDTORequest) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Couldn't find a student with id: " + studentId));

        modelMapper.map(studentDTORequest, student);
        return studentRepository.save(student);
    }
}
