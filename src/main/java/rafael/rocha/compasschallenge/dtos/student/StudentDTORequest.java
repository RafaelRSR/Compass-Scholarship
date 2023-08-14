package rafael.rocha.compasschallenge.dtos.student;

import lombok.*;
import rafael.rocha.compasschallenge.entity.Class;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTORequest {

    private String firstName;
    private String lastName;
    private String email;

    private Long classAssigned;
}
