package ruproject.services;

import org.springframework.stereotype.Service;
import ruproject.api.v1.model.LibroDTO;

import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {
    @Override
    public List<LibroDTO> getAllLibros() {
        return null;
    }

    @Override
    public LibroDTO getLibroById(Long Id) {
        return null;
    }

    @Override
    public LibroDTO saveLibro(LibroDTO libroDTO) {
        return null;
    }

    @Override
    public LibroDTO updateLibro(Long id, LibroDTO libroDTO) {
        return null;
    }

    @Override
    public boolean exsitsById(Long id) {
        return false;
    }

    @Override
    public void deleteLibro(Long id) {

    }
}
