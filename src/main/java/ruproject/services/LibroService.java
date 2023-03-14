package ruproject.services;

import ruproject.api.v1.model.LibroDTO;

import java.util.List;


public interface LibroService {

    List<LibroDTO> getAllLibros();

    LibroDTO getLibroById(Long Id);

    LibroDTO saveLibro(LibroDTO libroDTO);

    LibroDTO updateLibro(Long id, LibroDTO libroDTO);

    boolean exsitsById(Long id);

    void deleteLibro(Long id);

}
