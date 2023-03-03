package ruproject.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="contenidos")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private List<String> libros = new ArrayList<>();
    @Column
    private List<String> examenes = new ArrayList<>();
    @Column
    private List<String> cursos = new ArrayList<>();
    @ManyToOne
    private Materia materia;
}
