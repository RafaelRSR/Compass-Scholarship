package rafael.rocha.compasschallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rafael.rocha.compasschallenge.entity.Coordinator;

import java.util.List;

@Repository
public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {

    @Query(value = "SELECT * FROM coordinator ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Coordinator findOneCoordinator();
}
