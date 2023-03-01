package ruproject.api.v1.model;

import lombok.Data;
import ruproject.domain.Contenido;

import java.util.List;

@Data
public class MateriaDTO {
    private Long id;
    private String name;
    private List<Contenido> contenidoList;
}
