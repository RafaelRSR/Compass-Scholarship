package rafael.rocha.compasschallenge.dtos.coordinator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatorDTORequest {

    private String firstName;

    private String lastName;

    private String email;

    private Long classAssigned;
}
