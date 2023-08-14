package rafael.rocha.compasschallenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDTORequest {

    private String firstName;

    private String lastName;

    private String email;

    private Long classAssigned;
}
