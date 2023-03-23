package ruproject.exception;

public class ContenidoNotFoundException extends RuntimeException {

    public ContenidoNotFoundException(Long id ){

        super(String.format("Contenido with Id: %d not found",id));

    }

}
