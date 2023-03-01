package ruproject.api.v1.model;

import lombok.Data;

import java.util.List;
import java.util.SplittableRandom;
@Data
public class ContenidoDTO {
    private Long id;
    private List<String> libros;
    private List<String> examenes;
    private List<String> cursos;
}
