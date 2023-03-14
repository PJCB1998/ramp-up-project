package ruproject.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.api.v1.model.ContenidoListDTO;
import ruproject.services.ContenidoService;

@RestController
@RequestMapping("/api/v1/materias/{name}/contenidos/")
public class ContenidoController {

    private final ContenidoService contenidoService;

    public ContenidoController(ContenidoService contenidoService) {

        this.contenidoService = contenidoService;

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ContenidoListDTO getAllContenidos(){

        return new ContenidoListDTO(contenidoService.getAllContenidos());

    }

    @GetMapping("{id}/")
    @ResponseStatus(HttpStatus.OK)
    public ContenidoDTO getContenidoById(@PathVariable Long id){

        return contenidoService.getContenidoById(id);

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ContenidoDTO createContenido(@RequestBody ContenidoDTO contenido) {

        return contenidoService.saveContenido(contenido);

    }

    @PutMapping("{id}/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ContenidoDTO updateContenido(@PathVariable Long id, @RequestBody ContenidoDTO contenido) {

        return contenidoService.updateContenido(id,contenido);

    }

    @DeleteMapping("{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContenido(@PathVariable Long id) {

        contenidoService.deleteContenido(id);

    }
}
