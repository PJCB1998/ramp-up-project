package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ruproject.domain.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    @Query("SELECT libro FROM Libro libro JOIN libro.contenidos contenido JOIN contenido.materia mateira WHERE libro.id = :id AND contenido.id = :contenido_id AND materia.id = :materia_id")
    Libro findLibroByIdAndContenidoIdAndMateriaId(
            @Param("id") Long id,
            @Param("contenido_id") Long contenido_id,
            @Param("materia_id") Long materia_id);



}

