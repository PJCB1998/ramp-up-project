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
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "materia")
    private List<Contenido> contenidos = new ArrayList<>();
    @ManyToMany
    @JoinTable(name="carreras_materias",
        joinColumns = @JoinColumn(name="materias_id",referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name="carreras_id",referencedColumnName = "id"))
    private List<Carrera> carreras = new ArrayList<>();

}
