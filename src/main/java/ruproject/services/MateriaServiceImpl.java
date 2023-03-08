package ruproject.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.MateriaMapper;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.domain.Materia;
import ruproject.repositories.MateriaRepositroy;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements MateriaService {

    private final MateriaMapper materiaMapper;
    private final MateriaRepositroy materiaRepositroy;


    public MateriaServiceImpl(MateriaMapper materiaMapper, MateriaRepositroy materiaRepositroy) {
        this.materiaMapper = materiaMapper;
        this.materiaRepositroy = materiaRepositroy;
    }

    @Override
    public List<MateriaDTO> getAllMaterias() {
        return materiaRepositroy
                .findAll()
                .stream()
                .map(materiaMapper::materiaToMateriaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MateriaDTO getMateriaByName(String name) {
        return materiaMapper.materiaToMateriaDTO(materiaRepositroy.findByName(name));
    }

    @Override
    public MateriaDTO saveMateria(Materia materia) {
        return materiaMapper.materiaToMateriaDTO(materiaRepositroy.save(materia));
    }

    @Override
    public MateriaDTO updateMateria(Materia materia) {
        return materiaMapper.materiaToMateriaDTO(materiaRepositroy.save(materia));
    }

    @Override
    public Boolean existsByName(String name) {
        return materiaRepositroy.existsByName(name);
    }

    @Override
    @Transactional
    public void deleteMateria(String name) {
        materiaRepositroy.deleteByName(name);
    }
}
