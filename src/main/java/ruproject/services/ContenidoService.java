package ruproject.services;

import org.springframework.stereotype.Service;
import ruproject.api.v1.model.ContenidoDTO;

import java.util.List;

@Service
public interface ContenidoService {

    List<ContenidoDTO> getAllContenidosFromMateria(String name);

    ContenidoDTO getContenidoById(Long id, String name);

    ContenidoDTO saveContenido(ContenidoDTO contenidoDTO, String name);

    ContenidoDTO updateContenido(Long id,String name, ContenidoDTO contenidoDTO);

    boolean existsById(Long id);

    void deleteContenido(Long id);


}
