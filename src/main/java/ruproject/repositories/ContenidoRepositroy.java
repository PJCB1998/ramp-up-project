package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ruproject.domain.Contenido;


@Repository
public interface ContenidoRepositroy extends JpaRepository<Contenido,Long> {

    @Query("SELECT contenido FROM Contenido contenido JOIN contenido.materia materia WHERE contenido.id = :id and materia.id = :materia_id")
    Contenido findContenidoByIdAndMateriaId(
            @Param("id") Long id,
            @Param("materia_id") Long materia_id);

}
