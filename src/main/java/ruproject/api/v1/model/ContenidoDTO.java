package ruproject.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty(message = "Examenes can not be empty")
    private List<String> examenes = new ArrayList<>();
    @NotEmpty(message = "Cursos can not be empty")
    private List<String> cursos = new ArrayList<>();

    @NotNull(message = "Materia con not be null")
    private MateriaDTO materia;
}
