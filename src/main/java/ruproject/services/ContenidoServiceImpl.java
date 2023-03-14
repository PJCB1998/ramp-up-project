package ruproject.services;

import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.ContenidoMapper;
import ruproject.api.v1.mapper.LibroMapper;
import ruproject.api.v1.mapper.MateriaMapper;
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.domain.Contenido;
import ruproject.repositories.ContenidoRepositroy;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContenidoServiceImpl implements ContenidoService {

    private final ContenidoMapper contenidoMapper;

    private final MateriaMapper materiaMapper;

    private final LibroMapper libroMapper;

    private final ContenidoRepositroy contenidoRepositroy;


    public ContenidoServiceImpl(ContenidoMapper contenidoMapper, MateriaMapper materiaMapper, LibroMapper libroMapper, ContenidoRepositroy contenidoRepositroy) {
        this.contenidoMapper = contenidoMapper;
        this.materiaMapper = materiaMapper;
        this.libroMapper = libroMapper;
        this.contenidoRepositroy = contenidoRepositroy;
    }

    @Override
    public List<ContenidoDTO> getAllContenidos() {
        return contenidoRepositroy
                .findAll()
                .stream()
                .map(contenidoMapper::contenidoToContendidoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContenidoDTO getContenidoById(Long id) {

        return contenidoRepositroy
                .findById(id)
                .map(contenidoMapper::contenidoToContendidoDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public ContenidoDTO saveContenido(ContenidoDTO contenidoDTO) {
        Contenido contenido = contenidoMapper.contenidoDTOToContenido(contenidoDTO);
        return contenidoMapper.contenidoToContendidoDTO(contenidoRepositroy.save(contenido));
    }

    @Override
    public ContenidoDTO updateContenido(Long id, ContenidoDTO contenidoDTO) {

        if(existsById(id)){

            Contenido contenido = contenidoMapper.contenidoDTOToContenido(contenidoDTO);
            contenido.setId(contenidoDTO.getId());

            if(contenidoDTO.getCursos() != null) {
                contenido.setCursos(contenidoDTO.getCursos());
            }
            if(contenidoDTO.getLibros() !=null) {

                contenido.setLibros(contenidoDTO.getLibros().stream().map(libroMapper::libroDTOTOLibro).collect(Collectors.toList()));
            }

            if( contenidoDTO.getMateria() != null) {
                contenido.setMateria(materiaMapper.materiaDTOToMateria(contenidoDTO.getMateria()));
            }
            return contenidoMapper.contenidoToContendidoDTO(contenidoRepositroy.save(contenido));

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
