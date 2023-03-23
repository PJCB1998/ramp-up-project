package ruproject.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="LIBROS")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String autor;

    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.ALL})
    @JoinTable(
            name = "Contenido_Libro",
            joinColumns = { @JoinColumn(name = "libro_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "contenido_id", referencedColumnName = "id") }
    )
    private List<Contenido> contenidos = new ArrayList<>();

}
