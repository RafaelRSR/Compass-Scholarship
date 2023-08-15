package rafael.rocha.compasschallenge.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rafael.rocha.compasschallenge.dtos.student.StudentDTORequest;
import rafael.rocha.compasschallenge.dtos.student.StudentDTOResponse;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.service.StudentService;

import java.util.ArrayList;
import java.util.List;



@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllStudents() throws Exception {
        List<Student> students = new ArrayList<>();

        Student student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("John");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Jane");

        students.add(student1);
        students.add(student2);

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/v1/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void findById() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        when(studentService.findById(1L)).thenReturn(student);

        mockMvc.perform(get("/v1/students/get/{studentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(studentService, times(1)).findById(1L);
    }

    @Test
    void createStudent() throws Exception {
        StudentDTORequest request = new StudentDTORequest();
        request.setFirstName("John");

        StudentDTOResponse response = new StudentDTOResponse();
        response.setId(1L);
        response.setFirstName("John");

        when(studentService.createStudent(any())).thenReturn(response);

        mockMvc.perform(post("/v1/students/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(studentService, times(1)).createStudent(any());
    }

    @Test
    void updateStudent() throws Exception {
        StudentDTORequest request = new StudentDTORequest();
        request.setFirstName("Student");

        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setFirstName("Student");

        when(studentService.updateStudent(eq(1L), any())).thenReturn(updatedStudent);

        mockMvc.perform(put("/v1/students/update/{studentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Student\"}"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Student"));

        verify(studentService, times(1)).updateStudent(eq(1L), any());
    }

    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete("/v1/students/delete/{studentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")) // Adjusted expectation
                .andExpect(content().string("Deleted student with id: 1"));
        verify(studentService, times(1)).deleteStudentFromStudents(1L);
    }
}