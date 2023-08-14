package rafael.rocha.compasschallenge.dtos.scrummaster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScrumMasterDTORequest {

    private String firstName;

    private String lastName;

    private String email;

    private Long classAssigned;
}
