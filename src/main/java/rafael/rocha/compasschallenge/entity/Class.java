package rafael.rocha.compasschallenge.entity;

import jakarta.persistence.*;
import lombok.*;
import rafael.rocha.compasschallenge.enums.ClassStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classe")
    private Long id;

    @Enumerated(EnumType.STRING)
    public ClassStatus status;

    public String name;
    public List<Student> studentList;

    public Coordinator coordinatorAssigned;
    public List<Instructor> instructorsAssigned;

    public ScrumMaster scrumMasterAssigned;

    public List<Squad> squadList;

}
