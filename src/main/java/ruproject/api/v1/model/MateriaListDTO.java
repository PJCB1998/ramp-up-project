package ruproject.api.v1.model;


import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MateriaListDTO {
    List<MateriaDTO> materias;
}
