package rafael.rocha.compasschallenge.dtos.student;

import lombok.*;


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


