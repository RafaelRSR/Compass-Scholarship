package rafael.rocha.compasschallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rafael.rocha.compasschallenge.entity.ScrumMaster;

@Repository
public interface ScrumMasterRepository extends JpaRepository<ScrumMaster, Long> {
    @Query(value = "SELECT * FROM scrum_master ORDER BY RAND() LIMIT 1", nativeQuery = true)
    ScrumMaster findOneScrumMaster();
}
