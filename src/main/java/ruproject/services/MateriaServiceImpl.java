package ruproject.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.CycleAvoidingMappingContext;
import ruproject.api.v1.mapper.MateriaMapper;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.domain.Carrera;
import ruproject.domain.Contenido;
import ruproject.domain.Materia;
import ruproject.exception.ContenidoNotFoundException;
import ruproject.exception.MateriaNotFoundException;
import ruproject.repositories.CarreraRepository;
import ruproject.repositories.ContenidoRepositroy;
import ruproject.repositories.MateriaRepositroy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements MateriaService {

    private final MateriaMapper materiaMapper;
    private final MateriaRepositroy materiaRepositroy;

    private final CarreraRepository carreraRepository;

    private final ContenidoRepositroy contenidoRepositroy;


    public MateriaServiceImpl(MateriaMapper materiaMapper, MateriaRepositroy materiaRepositroy, CarreraRepository carreraRepository, ContenidoRepositroy contenidoRepositroy) {
        this.materiaMapper = materiaMapper;
        this.materiaRepositroy = materiaRepositroy;
        this.carreraRepository = carreraRepository;
        this.contenidoRepositroy = contenidoRepositroy;
    }

    @Override
    public List<MateriaDTO> getAllMaterias() {
        return materiaRepositroy
                .findAll()
                .stream()
                .map(materia -> materiaMapper.materiaToMateriaDTO(materia, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public MateriaDTO getMateriaByName(String name) {
        return materiaMapper.materiaToMateriaDTO(materiaRepositroy.findByName(name), new CycleAvoidingMappingContext());
    }

    @Override
    public MateriaDTO saveMateria(MateriaDTO materiaDTO) {
        Materia materia = materiaMapper.materiaDTOToMateria(materiaDTO, new CycleAvoidingMappingContext());
        return materiaMapper.materiaToMateriaDTO(materiaRepositroy.save(materia), new CycleAvoidingMappingContext());

    }

    @Override
    public MateriaDTO updateMateria(String name, MateriaDTO materiaDTO) {
        if(existsByName(name)) {
            Materia savedMateria = materiaRepositroy.findByName(materiaDTO.getName());
            Materia materia = materiaMapper.materiaDTOToMateria(materiaDTO, new CycleAvoidingMappingContext());
            materia.setName(savedMateria.getName());
            materia.setId(savedMateria.getId());

            List<Carrera> savedCarreraList = savedMateria.getCarreras();
            List<Carrera> returnCarreras = materia.getCarreras();
            List<Carrera> carreraList = new ArrayList<>();

            if(!savedCarreraList.isEmpty()){
                carreraList.addAll(savedCarreraList);
            }

            if(!returnCarreras.isEmpty()){

                carreraList.addAll(returnCarreras
                        .stream()
                        .filter(carrera -> carreraRepository.existsByName(carrera.getName()))
                        .map(carrera -> carreraRepository.findByName(carrera.getName()))
                        .collect(Collectors.toList()));

                materia.setCarreras(carreraList);

            }

            List<Contenido> savedContenidos= savedMateria.getContenidos();
            List<Contenido> returnContenidos = materia.getContenidos();
            List<Contenido> contenidoList = new ArrayList<>();

            if(!savedContenidos.isEmpty()){
                contenidoList.addAll(savedContenidos);
            }


            if(!returnContenidos.isEmpty()){

                contenidoList.addAll(returnContenidos
                        .stream()
                        .filter(contenido -> contenidoRepositroy.existsById(contenido.getId()))
                        .map(contenido -> contenidoRepositroy
                                .findById(contenido.getId())
                                .orElseThrow(()-> new ContenidoNotFoundException(contenido.getId())))
                        .collect(Collectors.toList()));

                materia.setContenidos(contenidoList);
            }


            return materiaMapper.materiaToMateriaDTO(materiaRepositroy.save(materia),new CycleAvoidingMappingContext());
        }
        throw new MateriaNotFoundException(name);

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
