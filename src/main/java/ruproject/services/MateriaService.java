package ruproject.services;

import org.springframework.stereotype.Service;
import ruproject.api.v1.model.MateriaDTO;


import java.util.List;
@Service
public interface MateriaService {
    List<MateriaDTO> getAllMaterias();
    MateriaDTO getMateriaByName(String name);
    MateriaDTO saveMateria(MateriaDTO materia);
    MateriaDTO updateMateria(String name,MateriaDTO materia);
    Boolean existsByName(String name);
    void deleteMateria(String name);

}
