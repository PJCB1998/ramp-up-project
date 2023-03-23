package ruproject.controllers.v1;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    @ResponseStatus(HttpStatus.OK)
    public CarreraListDTO getAllCarreras(){
        return new CarreraListDTO(carreraService.getAllCarreras());
    }

    @GetMapping("{name}/")
    @ResponseStatus(HttpStatus.OK)
    public CarreraDTO getCarreraByName(@PathVariable String name){
        return carreraService.getCarreraByName(name);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CarreraDTO createCarrera(@Valid @RequestBody CarreraDTO carrera) {
        return carreraService.saveCarrera(carrera);
    }

    @PutMapping(path = "{name}/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CarreraDTO updateCarrera(@PathVariable String name, @Valid @RequestBody CarreraDTO carreraDTO) {
        return carreraService.updateCarrera(name,carreraDTO);
    }

    @DeleteMapping("{name}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCarrera(@PathVariable String name) {
        carreraService.deleteCarrera(name);
    }

}
