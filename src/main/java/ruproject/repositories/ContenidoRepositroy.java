package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruproject.domain.Contenido;

@Repository
public interface ContenidoRepositroy extends JpaRepository<Contenido,Long> {
}
