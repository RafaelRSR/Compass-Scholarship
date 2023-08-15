package rafael.rocha.compasschallenge.entity;

import jakarta.validation.constraints.Email;
import rafael.rocha.compasschallenge.entity.Class;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instructor")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Email cannot be empty!")
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "class_assigned_id")
    private Long classAssigned;
}
