package rafael.rocha.compasschallenge.dtos.classroom;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import rafael.rocha.compasschallenge.entity.*;
import rafael.rocha.compasschallenge.enums.ClassStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassDTORequest {

    @Enumerated(EnumType.STRING)
    public ClassStatus status;
    public List<Student> studentList;
    public Coordinator coordinatorAssigned;
    public List<Instructor> instructorsAssigned;
    public ScrumMaster scrumMasterAssigned;

    public List<Squad> squadList;
}

