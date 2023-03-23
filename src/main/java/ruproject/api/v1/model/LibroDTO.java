package ruproject.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
public class LibroDTO {

    private Long id;

    @NotBlank(message = "Titluo can not be blank")
    private String titulo;

    @NotBlank(message = "Autor can not be blank")
    private String autor;
    private List<ContenidoDTO> contenidos = new ArrayList<>();


}
