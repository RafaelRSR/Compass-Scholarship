package rafael.rocha.compasschallenge.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafael.rocha.compasschallenge.entity.Class;
import rafael.rocha.compasschallenge.entity.Student;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SquadDTOResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Max(5)
    private List<Student> studentList;

    private Class classAssigned;
}
