package ruproject.controllers.v1;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.api.v1.model.CarreraListDTO;
import ruproject.domain.Carrera;
import ruproject.services.CarreraService;

import java.util.UUID;

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

    @GetMapping("{name}/")
    public ResponseEntity<CarreraDTO> getCarreraByName(@PathVariable String name){
        return new ResponseEntity<CarreraDTO>(carreraService.getCarreraByName(name),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CarreraDTO> createCarrera(@RequestBody Carrera carrera) {
        return new ResponseEntity<>(carreraService.saveCarrera(carrera), HttpStatus.CREATED);
    }

    @PutMapping("{name}/")
    public ResponseEntity<CarreraDTO> updateCarrera(@PathVariable String name, @RequestBody Carrera carrera) {
        if (carreraService.existsByName(name)) {
            return new ResponseEntity<>(carreraService.saveCarrera(carrera), HttpStatus.ACCEPTED);
        }
        throw new IllegalArgumentException("Carrera with name " + name + "not found");
    }

    @DeleteMapping("{name}/")
    public ResponseEntity<HttpStatus> deleteCarrera(@PathVariable String name) {
        carreraService.deleteCarrera(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
