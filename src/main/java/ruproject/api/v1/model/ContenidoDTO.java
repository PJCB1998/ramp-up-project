package ruproject.api.v1.model;

import lombok.Data;
import ruproject.domain.Materia;

import java.util.ArrayList;
import java.util.List;
@Data
public class ContenidoDTO {
    private Long id;
    private List<String> libros = new ArrayList<>();
    private List<String> examenes = new ArrayList<>();
    private List<String> cursos = new ArrayList<>();
    private MateriaDTO materia;
}
