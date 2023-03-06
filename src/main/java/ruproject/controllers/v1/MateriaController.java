package ruproject.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.api.v1.model.MateriaListDTO;
import ruproject.domain.Materia;
import ruproject.services.MateriaService;

@RestController
@RequestMapping("/api/v1/materias/")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping
    public ResponseEntity<MateriaListDTO> getAllMaterias(){
        return new ResponseEntity<MateriaListDTO>(
                new MateriaListDTO(materiaService.getAllMaterias()), HttpStatus.OK);
    }

    @GetMapping("{name}/")
    public ResponseEntity<MateriaDTO> getMateriaByName(@PathVariable String name){
        return new ResponseEntity<MateriaDTO>(materiaService.getMateriaByName(name),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MateriaDTO> createMateria(@RequestBody Materia materia) {
        return new ResponseEntity<>(materiaService.saveMateria(materia), HttpStatus.CREATED);
    }

    @PutMapping("{name}/")
    public ResponseEntity<MateriaDTO> updateMateria(@PathVariable String name, @RequestBody Materia materia) {
        if (materiaService.existsByName(name)) {
            return new ResponseEntity<>(materiaService.saveMateria(materia), HttpStatus.ACCEPTED);
        }
        throw new IllegalArgumentException("Materia with name " + name + "not found");
    }

    @DeleteMapping("{name}/")
    public ResponseEntity<HttpStatus> deleteMateria(@PathVariable String name) {
        materiaService.deleteMateria(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
