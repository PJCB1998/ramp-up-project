package ruproject.services;



import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.domain.Carrera;

import java.util.List;


public interface CarreraService {
    List<CarreraDTO> getAllCarreras();
    CarreraDTO getCarreraByName(String name);
    CarreraDTO saveCarrera(CarreraDTO carreraDTO);
    CarreraDTO updateCarera(String name, CarreraDTO carreraDTO);
    Boolean existsByName(String name);
    void deleteCarrera(String name);
}
