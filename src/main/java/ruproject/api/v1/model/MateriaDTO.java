package ruproject.api.v1.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Entity;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class MateriaDTO {
    private Long id;
    private String name;
    private List<ContenidoDTO> contenidos = new ArrayList<>();
    private List<CarreraDTO> carreras = new ArrayList<>();

}
