package ruproject.api.v1.model;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MateriaListDTO {
    List<MateriaDTO> materias;
}
