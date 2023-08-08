package rafael.rocha.compasschallenge.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Instructor {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
