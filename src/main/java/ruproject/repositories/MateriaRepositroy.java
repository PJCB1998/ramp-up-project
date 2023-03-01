package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ruproject.domain.Materia;

public interface MateriaRepositroy extends JpaRepository<Materia,Long> {
}
