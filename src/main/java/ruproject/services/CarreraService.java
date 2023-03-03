package ruproject.services;



import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ruproject.api.v1.model.CarreraDTO;

import java.util.List;

@Service
public interface CarreraService {
    List<CarreraDTO> getAllCarreras();
    CarreraDTO getCarreraByName(String name);


}
