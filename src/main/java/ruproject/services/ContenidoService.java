package ruproject.services;

import org.springframework.stereotype.Service;
import ruproject.api.v1.model.ContenidoDTO;

import java.util.List;
import java.util.Optional;

@Service
public interface ContenidoService {

    List<ContenidoDTO> getAllContenidos();

    ContenidoDTO getContenidoById(Long id);

    ContenidoDTO saveContenido(ContenidoDTO contenidoDTO);

    ContenidoDTO updateContenido(Long id, ContenidoDTO contenidoDTO);

    boolean existsById(Long id);

    void deleteContenido(Long id);


}
