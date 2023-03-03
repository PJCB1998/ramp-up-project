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
    private List<Contenido> contenidos = new ArrayList<>();
    private List<Materia> carreras = new ArrayList<>();
}
