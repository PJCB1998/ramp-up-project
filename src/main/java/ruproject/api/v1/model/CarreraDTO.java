package ruproject.api.v1.model;

import lombok.Data;
import ruproject.domain.Materia;

import java.util.ArrayList;
import java.util.List;
@Data
public class CarreraDTO {
    private Long id;
    private String name;
    private List<Materia> materias = new ArrayList<>();

}
