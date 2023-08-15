package rafael.rocha.compasschallenge.controller;



import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rafael.rocha.compasschallenge.dtos.classroom.ClassDTOResponse;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.service.ClassService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClassController.class)
class ClassControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassService classService;

    @Test
    void getAllClasses() throws Exception {
        Class class1 = new Class();
        class1.setId(1L);


        Class class2 = new Class();
        class2.setId(2L);

        List<Class> classes = new ArrayList<>();
        classes.add(class1);
        classes.add(class2);

        when(classService.getAllClasses()).thenReturn(classes);

        mockMvc.perform(get("/v1/classes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(classService, times(1)).getAllClasses();
    }

    @Test
    void getClassMembers() throws Exception {
        Long classId = 1L;

        ClassDTOResponse classMembersResponse = new ClassDTOResponse();
        classMembersResponse.setId(classId);

        when(classService.getClassMembers(classId)).thenReturn(classMembersResponse);

        mockMvc.perform(get("/v1/classes/get/{classId}", classId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(classId));


        verify(classService, times(1)).getClassMembers(classId);
    }

    @Test
    void createClass() throws Exception {
        ClassDTOResponse classDTO = new ClassDTOResponse();

        doNothing().when(classService).createClass(classDTO);

        mockMvc.perform(post("/v1/classes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Class created!"));

        verify(classService, times(1)).createClass(classDTO);
    }

    @Test
    void startClass() throws Exception {
        Long classId = 1L;
        doNothing().when(classService).startClass(classId);

        mockMvc.perform(post("/v1/classes/{classId}/start", classId))
                .andExpect(status().isOk())
                .andExpect(content().string("Class started successfully."));

        verify(classService, times(1)).startClass(classId);
    }

    @Test
    void finishClass() throws Exception {
        Long classId = 1L;

        doNothing().when(classService).finishClass(classId);

        mockMvc.perform(post("/v1/classes/{classId}/finish", classId))
                .andExpect(status().isOk())
                .andExpect(content().string("Class finished successfully."));

        verify(classService, times(1)).finishClass(classId);
    }

    @Test
    void populateStaff() throws Exception {
        Long classId = 1L;

        // Mock the service method to indicate successful population
        doNothing().when(classService).populateStaff(classId);

        // Perform the POST request to the endpoint
        mockMvc.perform(post("/v1/classes/{classId}/populateStaff", classId))
                .andExpect(status().isOk())
                .andExpect(content().string("Staff populated for class: " + classId));

        // Verify that the service method was called once with the provided classId
        verify(classService, times(1)).populateStaff(classId);
    }

    @Test
    void populateStudents() throws Exception {
        Long classId = 1L;

        doNothing().when(classService).populateClassWithStudents(classId);

        mockMvc.perform(post("/v1/classes/{classId}/populateStudents", classId))
                .andExpect(status().isOk())
                .andExpect(content().string("Students added to class: " + classId));

        verify(classService, times(1)).populateClassWithStudents(classId);
    }

    @Test
    void addSquadsToClassWithStudents() throws Exception {
        Long classId = 1L;

        doNothing().when(classService).addSquadsToClassWithStudents(classId);

        mockMvc.perform(post("/v1/classes/{classId}/addSquads", classId))
                .andExpect(status().isCreated())
                .andExpect(content().string("Squads added to class with students"));

        verify(classService, times(1)).addSquadsToClassWithStudents(classId);
    }
}