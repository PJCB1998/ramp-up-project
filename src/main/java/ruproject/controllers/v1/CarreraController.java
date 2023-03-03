package ruproject.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.api.v1.model.CarreraListDTO;
import ruproject.services.CarreraService;

@RestController
@RequestMapping("/api/v1/carreras/")
public class CarreraController {
    private final CarreraService carreraService;


    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping
    public ResponseEntity<CarreraListDTO> getAllCarreras(){
        return new ResponseEntity<CarreraListDTO>(
                new CarreraListDTO(carreraService.getAllCarreras()), HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<CarreraDTO> getCarreraByName(@PathVariable String name){
        return new ResponseEntity<CarreraDTO>(carreraService.getCarreraByName(name),HttpStatus.OK);
    }
}
