package ruproject.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="materias")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @OneToMany(mappedBy = "materia")
    private List<Contenido> contenidos = new ArrayList<>();
    @ManyToMany
    private List<Materia> carreras = new ArrayList<>();

}
