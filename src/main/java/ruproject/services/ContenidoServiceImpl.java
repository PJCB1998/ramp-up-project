package ruproject.services;

import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.ContenidoMapper;
import ruproject.api.v1.mapper.CycleAvoidingMappingContext;
import ruproject.api.v1.mapper.LibroMapper;
import ruproject.api.v1.mapper.MateriaMapper;
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.domain.Contenido;
import ruproject.domain.Materia;
import ruproject.repositories.ContenidoRepositroy;
import ruproject.repositories.MateriaRepositroy;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContenidoServiceImpl implements ContenidoService {

    private final ContenidoMapper contenidoMapper;

    private final MateriaMapper materiaMapper;

    private final LibroMapper libroMapper;

    private final ContenidoRepositroy contenidoRepositroy;

    private final MateriaRepositroy materiaRepositroy;


    public ContenidoServiceImpl(ContenidoMapper contenidoMapper, MateriaMapper materiaMapper, LibroMapper libroMapper, ContenidoRepositroy contenidoRepositroy, MateriaRepositroy materiaRepositroy) {
        this.contenidoMapper = contenidoMapper;
        this.materiaMapper = materiaMapper;
        this.libroMapper = libroMapper;
        this.contenidoRepositroy = contenidoRepositroy;
        this.materiaRepositroy = materiaRepositroy;
    }

    @Override
    public List<ContenidoDTO> getAllContenidosFromMateria(String name) {
        if(materiaRepositroy.existsByName(name)){
            return materiaRepositroy
                .findByName(name)
                .getContenidos()
                .stream()
                .map(contenido -> contenidoMapper.contenidoToContendidoDTO(contenido, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
        }
        throw new RuntimeException("Materia With Name:" + name + " not found");
    }

    @Override
    public ContenidoDTO getContenidoById(Long id,String name) {

        /*if(materiaRepositroy.existsByName(name)) {

            List<ContenidoDTO> contenidoDTOList = materiaRepositroy
                    .findByName(name)
                    .getContenidos()
                    .stream()
                    .map(contenidoMapper::contenidoToContendidoDTO)
                    .collect(Collectors.toList());

            ContenidoDTO contenidoDTO = contenidoMapper
                    .contenidoToContendidoDTO(contenidoRepositroy
                            .findById(id)
                            .orElseThrow(()-> new RuntimeException("Contenido wiht id: "+ id +" not found")));

            if(contenidoDTOList.contains(contenidoDTO)){
                return contenidoDTO;
            }

            throw new RuntimeException("Contenido " + id + " Does  not belong to Materia "+ name);
        }
        throw new RuntimeException("Materia with Name:" + name + " not found");*/


        Long materia_id;

        if(materiaRepositroy.existsByName(name)){
            materia_id = materiaRepositroy.findByName(name).getId();
            return contenidoMapper.contenidoToContendidoDTO(contenidoRepositroy.findContenidoByIdAndMateriaId(id,materia_id), new CycleAvoidingMappingContext());

        }

        throw new RuntimeException("Materia with Name:" + name + " not found");


    }

    @Override
    public ContenidoDTO saveContenido(ContenidoDTO contenidoDTO, String name) {
        Contenido contenido = contenidoMapper.contenidoDTOToContenido(contenidoDTO, new CycleAvoidingMappingContext());
        Materia materia = materiaRepositroy.findByName(name);
        contenido.setMateria(materia);
        return contenidoMapper.contenidoToContendidoDTO(contenidoRepositroy.save(contenido), new CycleAvoidingMappingContext());
    }

    @Override
    public ContenidoDTO updateContenido(Long id, ContenidoDTO contenidoDTO) {

        if(existsById(id)){

            Contenido contenido = contenidoMapper.contenidoDTOToContenido(contenidoDTO, new CycleAvoidingMappingContext());
            contenido.setId(contenidoDTO.getId());

            if(contenidoDTO.getCursos() != null) {
                contenido.setCursos(contenidoDTO.getCursos());
            }
            if(contenidoDTO.getLibros() !=null) {

                contenido.setLibros(contenidoDTO
                        .getLibros()
                        .stream()
                        .map(libroDTO -> libroMapper.libroDTOTOLibro(libroDTO,new CycleAvoidingMappingContext()))
                        .collect(Collectors.toList()));
            }

            if( contenidoDTO.getMateria() != null) {
                contenido.setMateria(materiaMapper.materiaDTOToMateria(contenidoDTO.getMateria(), new CycleAvoidingMappingContext()));
            }
            return contenidoMapper.contenidoToContendidoDTO(contenidoRepositroy.save(contenido), new CycleAvoidingMappingContext());

        }

        throw new IllegalArgumentException("No Contenido with id:" + id + " found");

    }

    @Override
    public boolean existsById(Long id) {

        return contenidoRepositroy.existsById(id);
    }

    @Override
    public void deleteContenido(Long id) {

        contenidoRepositroy.deleteById(id);

    }
}
