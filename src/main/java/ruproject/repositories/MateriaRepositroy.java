package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruproject.domain.Materia;

import java.util.Optional;

@Repository
public interface MateriaRepositroy extends JpaRepository<Materia,Long> {
    Optional<Materia> findByName(String name);

    Boolean existsByName(String name);

    void deleteByName(String name);
}
