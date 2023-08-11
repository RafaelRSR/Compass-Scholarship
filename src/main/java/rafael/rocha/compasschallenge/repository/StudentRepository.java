package rafael.rocha.compasschallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafael.rocha.compasschallenge.entity.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
