package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruproject.domain.Carrera;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera,Long> {
    Carrera findByName(String name);

    Boolean existsByName(String name);

    Carrera deleteByName(String name);
}
