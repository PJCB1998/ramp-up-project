package ruproject.controllers.v1;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ruproject.api.v1.model.LibroDTO;
import ruproject.api.v1.model.LibroListDTO;
import ruproject.services.LibroService;

@RestController
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping("/api/v1/libros/")
    @ResponseStatus(HttpStatus.OK)
    public LibroListDTO getAllLibros(){
        return new LibroListDTO( libroService.getAllLibros());
    }

    @GetMapping("/api/v1/libros/{id}/")
    @ResponseStatus(HttpStatus.OK)
    public LibroDTO getLibroById(@PathVariable Long id ){
        return libroService.getLibroById(id);
    }

    @GetMapping("/api/v1/materias/{name}/contenidos/{contenido_id}/libros/")
    @ResponseStatus(HttpStatus.OK)
    public LibroListDTO getAllLibrosFromContenidos(@PathVariable String name, @PathVariable Long contenido_id){

        return new LibroListDTO(libroService.getAllLibrosFromContenido(contenido_id,name));

    }

    @GetMapping("/api/v1/materias/{name}/contenidos/{contenido_id}/libros/{libro_id}/")
    @ResponseStatus(HttpStatus.OK)
    public LibroDTO getLibroByIdFromContenidos(@PathVariable String name, @PathVariable Long contenido_id, @PathVariable Long libro_id){

        return libroService.getLibroByIdFromContenido(libro_id,contenido_id,name);

    }

    @PostMapping("/api/v1/libros/")
    @ResponseStatus(HttpStatus.CREATED)
    public LibroDTO createLibro(@RequestBody LibroDTO libroDTO){
        return libroService.saveLibro(libroDTO);
    }

    @PutMapping("/api/v1/libros/{id}/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LibroDTO updateLibro(@PathVariable Long id, @RequestBody LibroDTO libroDTO ){

        return libroService.updateLibro(id,libroDTO);

    }
    @DeleteMapping("/api/v1/libros/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLibro(@PathVariable Long id){

        libroService.deleteLibro(id);

    }




}
