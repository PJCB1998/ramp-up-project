package ruproject.services;



import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.domain.Carrera;

import java.util.List;


@Transactional
@Service
public interface CarreraService {
    List<CarreraDTO> getAllCarreras();
    CarreraDTO getCarreraByName(String name);
    CarreraDTO saveCarrera(Carrera carrera);
    CarreraDTO updateCarera(Carrera carrera);
    Boolean existsByName(String name);
    CarreraDTO deleteCarrera(String name);
}
