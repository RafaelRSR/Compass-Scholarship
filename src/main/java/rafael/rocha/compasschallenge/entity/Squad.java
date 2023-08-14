package rafael.rocha.compasschallenge.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @OneToMany
    @JoinColumn(name = "student_list")
    private List<Student> studentList = new ArrayList<>();

    @Column(name = "class_assigned_id")
    private Long classAssigned;
}
