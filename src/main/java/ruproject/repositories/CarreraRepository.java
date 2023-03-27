package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruproject.domain.Carrera;

import java.util.Optional;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera,Long> {
    Optional<Carrera> findByName(String name);

    Boolean existsByName(String name);

    void deleteByName(String name);
}
