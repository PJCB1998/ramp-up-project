package ruproject.api.v1.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotBlank;
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
public class CarreraDTO {
    private Long id;
    @NotBlank(message = "Name can not be blank")
    private String name;
    private List<MateriaDTO> materias = new ArrayList<>();
}
