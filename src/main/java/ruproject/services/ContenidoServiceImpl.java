package ruproject.services;

import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.ContenidoMapper;
import ruproject.api.v1.mapper.CycleAvoidingMappingContext;
import ruproject.api.v1.mapper.MateriaMapper;
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.domain.Contenido;
import ruproject.domain.Libro;
import ruproject.domain.Materia;
import ruproject.exception.ContenidoNotFoundException;
import ruproject.exception.LibroNotFoundException;
import ruproject.exception.MateriaNotFoundException;
import ruproject.repositories.ContenidoRepositroy;
import ruproject.repositories.LibroRepository;
import ruproject.repositories.MateriaRepositroy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContenidoServiceImpl implements ContenidoService {

    private final ContenidoMapper contenidoMapper;

    private final MateriaMapper materiaMapper;


    private final ContenidoRepositroy contenidoRepositroy;

    private final MateriaRepositroy materiaRepositroy;

    private final LibroRepository libroRepository;

    public ContenidoServiceImpl(ContenidoMapper contenidoMapper, MateriaMapper materiaMapper, ContenidoRepositroy contenidoRepositroy, MateriaRepositroy materiaRepositroy, LibroRepository libroRepository) {
        this.contenidoMapper = contenidoMapper;
        this.materiaMapper = materiaMapper;
        this.contenidoRepositroy = contenidoRepositroy;
        this.materiaRepositroy = materiaRepositroy;
        this.libroRepository = libroRepository;
    }

    @Override
    public List<ContenidoDTO> getAllContenidosFromMateria(String name) {
        if(materiaRepositroy.existsByName(name)){
            return materiaRepositroy
                .findByName(name).orElseThrow(()-> new MateriaNotFoundException(name))
                .getContenidos()
                .stream()
                .map(contenido -> contenidoMapper.contenidoToContendidoDTO(contenido, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
        }
        throw new MateriaNotFoundException(name);
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
            materia_id = materiaRepositroy.findByName(name).orElseThrow(()-> new MateriaNotFoundException(name)).getId();
            return contenidoMapper.contenidoToContendidoDTO(contenidoRepositroy.findContenidoByIdAndMateriaId(id,materia_id), new CycleAvoidingMappingContext());

        }

        throw new MateriaNotFoundException(name);


    }

    @Override
    public ContenidoDTO saveContenido(ContenidoDTO contenidoDTO, String name) {
        Contenido contenido = contenidoMapper.contenidoDTOToContenido(contenidoDTO, new CycleAvoidingMappingContext());
        Materia materia = materiaRepositroy.findByName(name).orElseThrow(()-> new MateriaNotFoundException(name));
        contenido.setMateria(materia);
        return contenidoMapper.contenidoToContendidoDTO(contenidoRepositroy.save(contenido), new CycleAvoidingMappingContext());
    }

    @Override
    public ContenidoDTO updateContenido(Long id, String name, ContenidoDTO contenidoDTO) {


        if(existsById(id) && materiaRepositroy
                .findByName(name)
                .orElseThrow(()-> new MateriaNotFoundException(name))
                .getContenidos()
                .stream()
                .map(Contenido::getId).collect(Collectors.toList()).contains(id)){

            Contenido contenido = contenidoMapper.contenidoDTOToContenido(contenidoDTO, new CycleAvoidingMappingContext());

            Contenido savedContenido = contenidoRepositroy.findContenidoByIdAndMateriaId(id, materiaRepositroy.findByName(name)
                    .orElseThrow(()-> new MateriaNotFoundException((name))).getId());

            contenido.setId(savedContenido.getId());

            List<String> savedCursos = savedContenido.getCursos();
            List<String> retrunCursos = contenido.getCursos();
            List<String> cursos = new ArrayList<>();

            if(!savedCursos.isEmpty()){
                cursos.addAll(savedCursos);
            }

            if(!retrunCursos.isEmpty()) {

                cursos.addAll(retrunCursos);
                contenido.setCursos(cursos);

            }

            List<Libro> savedLibros = savedContenido.getLibros();
            List<Libro> returnLibros = contenido.getLibros();
            List<Libro> libros = new ArrayList<>();

            if(!savedLibros.isEmpty()){
                libros.addAll(savedLibros);
            }

            if(!returnLibros.isEmpty()) {
                libros.addAll(returnLibros
                        .stream()
                        .filter(libro -> libroRepository.existsById(libro.getId()))
                        .map(libro -> libroRepository
                                .findById(libro.getId()).orElseThrow(()->new LibroNotFoundException(libro.getId())))
                        .collect(Collectors.toList()));

                contenido.setLibros(libros);
            }

            if( contenidoDTO.getMateria() != null) {
                contenido.setMateria(materiaMapper.materiaDTOToMateria(contenidoDTO.getMateria(), new CycleAvoidingMappingContext()));
            }

            return contenidoMapper.contenidoToContendidoDTO(contenidoRepositroy.save(contenido), new CycleAvoidingMappingContext());

        }

        throw new ContenidoNotFoundException(id);

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
