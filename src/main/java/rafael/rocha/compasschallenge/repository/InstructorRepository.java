package rafael.rocha.compasschallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rafael.rocha.compasschallenge.entity.Instructor;
import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query(value = "SELECT * FROM instructor ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Instructor> findThreeInstructors();
}
