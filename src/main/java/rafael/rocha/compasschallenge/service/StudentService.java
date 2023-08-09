package rafael.rocha.compasschallenge.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.StudentDTO;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

}
