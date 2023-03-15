package ruproject.services;

import ruproject.api.v1.model.LibroDTO;

import java.util.List;


public interface LibroService {

    List<LibroDTO> getAllLibrosFromContenido(Long contenido_id, String name);

    LibroDTO getLibroByIdFromContenido(Long id, Long contenido_id, String name);

    List<LibroDTO> getAllLibros();

    LibroDTO getLibroById(Long id);

    LibroDTO saveLibro(LibroDTO libroDTO);

    LibroDTO updateLibro(Long id, LibroDTO libroDTO);

    boolean exsitsById(Long id);

    void deleteLibro(Long id);

}
