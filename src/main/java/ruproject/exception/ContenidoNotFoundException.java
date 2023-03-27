package ruproject.exception;

public class ContenidoNotFoundException extends RuntimeException {

    public ContenidoNotFoundException(Long id ){

        super("Contenido with Id: "+ id + " not found");

    }

}
