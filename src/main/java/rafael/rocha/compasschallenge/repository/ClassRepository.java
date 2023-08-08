package rafael.rocha.compasschallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafael.rocha.compasschallenge.entity.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

}
