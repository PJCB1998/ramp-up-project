package ruproject.api.v1.model;

import lombok.Data;
import ruproject.domain.Contenido;
import ruproject.domain.Materia;

import java.util.ArrayList;
import java.util.List;

@Data
public class MateriaDTO {
    private Long id;
    private String name;
    private List<ContenidoDTO> contenidos = new ArrayList<>();
    private List<CarreraDTO> carreras = new ArrayList<>();
}
