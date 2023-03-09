package ruproject.services;



import ruproject.api.v1.model.CarreraDTO;

import java.util.List;


public interface CarreraService {
    List<CarreraDTO> getAllCarreras();
    CarreraDTO getCarreraByName(String name);
    CarreraDTO saveCarrera(CarreraDTO carreraDTO);
    CarreraDTO updateCarrera(String name, CarreraDTO carreraDTO);
    Boolean existsByName(String name);
    void deleteCarrera(String name);
}
