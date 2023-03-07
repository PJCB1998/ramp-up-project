package ruproject.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.api.v1.model.MateriaListDTO;
import ruproject.services.MateriaService;

@RestController
@RequestMapping("/api/v1/materias/")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MateriaListDTO getAllMaterias(){
        return new MateriaListDTO(materiaService.getAllMaterias());
    }

    @GetMapping("{name}/")
    @ResponseStatus(HttpStatus.OK)
    public MateriaDTO getMateriaByName(@PathVariable String name){
        return materiaService.getMateriaByName(name);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MateriaDTO createMateria(@RequestBody MateriaDTO materia) {
        return materiaService.saveMateria(materia);
    }

    @PutMapping("{name}/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MateriaDTO updateMateria(@PathVariable String name, @RequestBody MateriaDTO materia) {
        if (materiaService.existsByName(name)) {
            return materiaService.updateMateria(name,materia);
        }
        throw new IllegalArgumentException("Materia with name " + name + " not found");
    }

    @DeleteMapping("{name}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMateria(@PathVariable String name) {
        materiaService.deleteMateria(name);
    }
}
