package ruproject.services;

import org.springframework.stereotype.Service;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.domain.Materia;


import java.util.List;
@Service
public interface MateriaService {
    List<MateriaDTO> getAllMaterias();
    MateriaDTO getMateriaByName(String name);
    MateriaDTO saveMateria(Materia materia);
    MateriaDTO updateMateria(Materia materia);
    Boolean existsByName(String name);
    void deleteMateria(String name);

}
