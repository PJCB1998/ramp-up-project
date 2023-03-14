package ruproject.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="MATERIAS")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "materia")
    private List<Contenido> contenidos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "materias")
    private List<Carrera> carreras = new ArrayList<>();

}
