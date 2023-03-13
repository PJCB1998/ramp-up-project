package ruproject.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import lombok.*;
import ruproject.domain.Materia;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ContenidoDTO {
    private Long id;
    private List<LibroDTO> libros = new ArrayList<>();
    private List<String> examenes = new ArrayList<>();
    private List<String> cursos = new ArrayList<>();
    private MateriaDTO materia;
}
