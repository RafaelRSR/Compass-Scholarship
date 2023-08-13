package rafael.rocha.compasschallenge.service;


import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.compasschallenge.dtos.SquadDTORequest;
import rafael.rocha.compasschallenge.entity.Squad;
import rafael.rocha.compasschallenge.entity.Student;
import rafael.rocha.compasschallenge.exceptions.SquadNotFoundException;
import rafael.rocha.compasschallenge.repository.SquadRepository;

import java.util.List;

@Service
public class SquadService {

    @Autowired
    private SquadRepository squadRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Squad createSquad(SquadDTORequest squadDTORequest) {
        Squad squadEntity = modelMapper.map(squadDTORequest, Squad.class);

        return squadRepository.save(squadEntity);
    }

    @Transactional
    public Squad findSquadById(Long squadId) {
        return squadRepository.findById(squadId)
                .orElseThrow(() -> new SquadNotFoundException("Squad not found with the id: " + squadId));
    }

    @Transactional
    public void deleteSquad(Long squadId) {
        Squad squadEntity = squadRepository.findById(squadId)
                .orElseThrow(() -> new SquadNotFoundException("Squad not found with the id: " + squadId));

        squadRepository.delete(squadEntity);
    }

    @Transactional
    public void addStudentsToSquad(Long squadId, Student student) {
        Squad squadEntity = squadRepository.findById(squadId)
                .orElseThrow(() -> new SquadNotFoundException("Squad not found with the id: " + squadId));

        squadEntity.getStudentList().add(student);
        squadRepository.save(squadEntity);
    }

    @Transactional
    public void deleteStudentFromSquad(Long squadId, Long studentId) {
        Squad squadEntity = squadRepository.findById(squadId)
                .orElseThrow(() -> new SquadNotFoundException("Squad not found with id: " + squadId));

        squadEntity.getStudentList().removeIf(student -> student.getId().equals(studentId));
        squadRepository.save(squadEntity);
    }
}
