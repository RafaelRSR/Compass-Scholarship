package rafael.rocha.compasschallenge.dtos.squad;

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
public class SquadDTORequest {

    private String name;

    @Max(5)
    private List<Student> studentList;

    private Class classAssigned;
}
