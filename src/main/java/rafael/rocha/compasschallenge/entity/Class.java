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
    @Column(name = "class_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public ClassStatus status;

    @OneToMany
    @Column(name = "student")
    public List<Student> studentList;

    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    public Coordinator coordinatorAssigned;
    @ManyToMany
    @JoinTable(name = "class_instructor",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    public List<Instructor> instructorsAssigned;

    @ManyToOne
    @JoinColumn(name = "scrum_master_id")
    public ScrumMaster scrumMasterAssigned;

    @OneToMany
    public List<Squad> squadList;

}