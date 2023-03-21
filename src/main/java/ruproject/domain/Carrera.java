package ruproject.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CARRERAS")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Carrera_Materia",
            joinColumns = { @JoinColumn(name = "carrera_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "materia_id", referencedColumnName = "id") })
    private List<Materia> materias = new ArrayList<>();

}
