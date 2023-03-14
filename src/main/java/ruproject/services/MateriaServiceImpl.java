package ruproject.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.CarreraMapper;
import ruproject.api.v1.mapper.ContenidoMapper;
import ruproject.api.v1.mapper.MateriaMapper;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.domain.Carrera;
import ruproject.domain.Materia;
import ruproject.repositories.CarreraRepository;
import ruproject.repositories.MateriaRepositroy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements MateriaService {

    private final MateriaMapper materiaMapper;
    private final MateriaRepositroy materiaRepositroy;
    private final CarreraMapper carreraMapper;

    private final CarreraRepository carreraRepository;

    private final ContenidoMapper contenidoMapper;


    public MateriaServiceImpl(MateriaMapper materiaMapper, MateriaRepositroy materiaRepositroy, CarreraMapper carreraMapper, CarreraRepository carreraRepository, ContenidoMapper contenidoMapper) {
        this.materiaMapper = materiaMapper;
        this.materiaRepositroy = materiaRepositroy;

        this.carreraMapper = carreraMapper;
        this.carreraRepository = carreraRepository;
        this.contenidoMapper = contenidoMapper;
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
    public MateriaDTO saveMateria(MateriaDTO materiaDTO) {
        Materia materia = materiaMapper.materiaDTOToMateria(materiaDTO);
        return materiaMapper.materiaToMateriaDTO(materiaRepositroy.save(materia));

    }

    @Override
    public MateriaDTO updateMateria(String name, MateriaDTO materiaDTO) {
        if(existsByName(name)) {
            Materia savedMateria = materiaRepositroy.findByName(materiaDTO.getName());
            Materia materia = materiaMapper.materiaDTOToMateria(materiaDTO);
            materia.setName(savedMateria.getName());
            materia.setId(savedMateria.getId());

            List<Carrera> savedCarreraList = savedMateria.getCarreras();
            List<Carrera> returnCarreras = materia.getCarreras();
            List<Carrera> carreraList = new ArrayList<>();

            if(savedCarreraList.size()>0){
                carreraList.addAll(savedCarreraList);
            }

            if(materia.getCarreras()!=null){

                carreraList.addAll(returnCarreras
                        .stream()
                        .filter(carrera -> carreraRepository.existsByName(carrera.getName()))
                        .map(carrera -> carreraRepository.findByName(carrera.getName()))
                        .collect(Collectors.toList()));

                materia.setCarreras(carreraList);

            }
            if(materia.getContenidos() != null){
                materia.setContenidos(materiaDTO.getContenidos().stream().map(contenidoMapper::contenidoDTOToContenido).collect(Collectors.toList()));
            }

            return materiaMapper.materiaToMateriaDTO(materiaRepositroy.save(materia));
        }
        throw new IllegalArgumentException("Materia with name: "+ name + " not found");

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
