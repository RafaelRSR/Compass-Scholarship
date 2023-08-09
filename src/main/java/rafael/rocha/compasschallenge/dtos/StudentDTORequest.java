package rafael.rocha.compasschallenge.dtos;

import lombok.*;
import rafael.rocha.compasschallenge.entity.Class;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDTORequest {

    private String firstName;
    private String lastName;
    private String email;

    private Class classAssigned;
}
