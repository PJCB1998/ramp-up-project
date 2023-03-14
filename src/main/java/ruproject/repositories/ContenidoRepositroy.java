package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.domain.Contenido;

import java.util.Optional;

@Repository
public interface ContenidoRepositroy extends JpaRepository<Contenido,Long> {

@Query("SELECT Contenido FROM Contenido WHERE id = :id and materia = :materia_id")
Contenido findContenidoByIdAndMateriaId(
            @Param("id") Long id,
            @Param("materia_id") Long materia_id);

}
