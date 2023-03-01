package ruproject.services;


import ruproject.api.v1.model.CarreraDTO;

import java.util.List;

public interface CarreraService {
    List<CarreraDTO> getAllCarreras();
    CarreraDTO getCarreraByName(String name);


}
