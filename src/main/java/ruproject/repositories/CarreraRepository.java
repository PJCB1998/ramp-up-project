package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ruproject.domain.Carrera;

public interface CarreraRepository extends JpaRepository<Carrera,Long> {
}
