package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ruproject.domain.Contenido;

public interface ContenidoRepositroy extends JpaRepository<Contenido,Long> {
}
