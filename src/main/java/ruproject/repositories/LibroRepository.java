package ruproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruproject.domain.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

}
