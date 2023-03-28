package ruproject.services;

import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.CycleAvoidingMappingContext;
import ruproject.api.v1.mapper.LibroMapper;
import ruproject.api.v1.model.LibroDTO;
import ruproject.domain.Contenido;
import ruproject.domain.Libro;
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
public class LibroServiceImpl implements LibroService {

    private final ContenidoRepositroy contenidoRepositroy;

    private final LibroRepository libroRepository;


    private final LibroMapper libroMapper;

    private final MateriaRepositroy materiaRepositroy;

    public LibroServiceImpl(ContenidoRepositroy contenidoRepositroy, LibroRepository libroRepository ,MateriaRepositroy materiaRepositroy, LibroMapper libroMapper) {
        this.contenidoRepositroy = contenidoRepositroy;
        this.libroRepository = libroRepository;
        this.libroMapper = libroMapper;
        this.materiaRepositroy = materiaRepositroy;

    }

    @Override
    public List<LibroDTO> getAllLibrosFromContenido(Long contenido_id, String name) {

        if(contenidoRepositroy.existsById(contenido_id)){
            return contenidoRepositroy
                    .findContenidoByIdAndMateriaId(contenido_id, materiaRepositroy.findByName(name)
                            .orElseThrow(()-> new MateriaNotFoundException(name))
                            .getId()).orElseThrow(()-> new ContenidoNotFoundException(contenido_id))
                    .getLibros()
                    .stream()
                    .map(libro -> libroMapper.libroToLibroDTO(libro,new CycleAvoidingMappingContext()))
                    .collect(Collectors.toList());
        }

        throw new MateriaNotFoundException(name);

    }

    @Override
    public LibroDTO getLibroByIdFromContenido(Long id,Long contenido_id,String name) {
        if(materiaRepositroy.existsByName(name)){

            return libroMapper.libroToLibroDTO(
                            libroRepository.findLibroByIdAndContenidoIdAndMateriaId(id,contenido_id,materiaRepositroy
                                            .findByName(name)
                                            .orElseThrow(()-> new MateriaNotFoundException(name)).getId())
                                    .orElseThrow(()-> new LibroNotFoundException(id))
                            , new CycleAvoidingMappingContext());

        }

        throw new MateriaNotFoundException(name);
    }

    @Override
    public List<LibroDTO> getAllLibros(){

        return libroRepository
                .findAll()
                .stream()
                .map(libro -> libroMapper.libroToLibroDTO(libro,new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public LibroDTO getLibroById(Long id){

        return libroMapper.libroToLibroDTO(libroRepository.findById(id).orElseThrow(()-> new LibroNotFoundException(id)), new CycleAvoidingMappingContext());

    }

    @Override
    public LibroDTO saveLibro(LibroDTO libroDTO) {
        Libro libro = libroMapper.libroDTOTOLibro(libroDTO, new CycleAvoidingMappingContext());
        return libroMapper.libroToLibroDTO(libroRepository.save(libro),new CycleAvoidingMappingContext());

    }

    @Override
    public LibroDTO updateLibro(Long id, LibroDTO libroDTO) {

        if(exsitsById(id)){

            Libro savedLibro = libroRepository.findById(id).orElseThrow(()-> new LibroNotFoundException(id));
            Libro retrunLibro = libroMapper.libroDTOTOLibro(libroDTO,new CycleAvoidingMappingContext());

            retrunLibro.setId(savedLibro.getId());
            retrunLibro.setAutor(savedLibro.getAutor());
            retrunLibro.setTitulo(savedLibro.getTitulo());

            List<Contenido> retrunContenidos = retrunLibro.getContenidos();
            List<Contenido> savedContenidos = savedLibro.getContenidos();
            List<Contenido> contenidos = new ArrayList<>();

            if(!savedContenidos.isEmpty()){

                contenidos.addAll(savedContenidos);

            }

            if(!retrunContenidos.isEmpty()){

                contenidos.addAll(retrunContenidos
                        .stream()
                        .filter(contenido -> contenidoRepositroy.existsById(contenido.getId()))
                        .map(contenido -> contenidoRepositroy.findById(contenido.getId()).orElseThrow(()-> new ContenidoNotFoundException(contenido.getId())))
                        .collect(Collectors.toList()));


               retrunLibro.setContenidos(contenidos);

            }

            retrunLibro= libroRepository.save(retrunLibro);

            return libroMapper.libroToLibroDTO(retrunLibro, new CycleAvoidingMappingContext());



        }

        throw new LibroNotFoundException(id);


    }

    @Override
    public boolean exsitsById(Long id) {
        return libroRepository.existsById(id);
    }

    @Override
    public void deleteLibro(Long id) {

        libroRepository.deleteById(id);

    }
}
