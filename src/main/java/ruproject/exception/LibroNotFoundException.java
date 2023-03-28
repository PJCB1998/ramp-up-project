package ruproject.exception;

public class LibroNotFoundException extends RuntimeException {

    public LibroNotFoundException(Long id){
        super("Libro with Id: " + id + " not found");
    }

}
