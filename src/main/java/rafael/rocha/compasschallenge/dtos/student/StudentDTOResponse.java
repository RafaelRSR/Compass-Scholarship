package rafael.rocha.compasschallenge.dtos.student;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import rafael.rocha.compasschallenge.entity.Class;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTOResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Long classAssigned;
}
