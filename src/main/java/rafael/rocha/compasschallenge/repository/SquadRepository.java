package rafael.rocha.compasschallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafael.rocha.compasschallenge.entity.Squad;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Long> {
}
