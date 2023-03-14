package ruproject.api.v1.model;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;


import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CarreraListDTO {

    List<CarreraDTO> carreras;
}
