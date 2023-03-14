package ruproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "libros")
@EqualsAndHashCode(exclude = "libros")
@Table(name="CONTENIDOS")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinTable(
            name = "Contenido_Libors",
            joinColumns = { @JoinColumn(name = "contenido_id") },
            inverseJoinColumns = { @JoinColumn(name = "libro_id") }
    )
    private List<Libro> libros = new ArrayList<>();

    private List<String> examenes = new ArrayList<>();

    private List<String> cursos = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="materia_id")
    private Materia materia;
}
