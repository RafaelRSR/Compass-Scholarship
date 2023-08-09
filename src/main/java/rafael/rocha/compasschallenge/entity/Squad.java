package rafael.rocha.compasschallenge.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_squad")
    private Long id;

    @Column(name = "name_squad")
    private String name;

    @Size(min = 1, max = 5)
    @OneToMany
    @Column(name = "student_list")
    private List<Student> studentList;

    @ManyToOne
    private Class classAssigned;
}
