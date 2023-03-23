package ruproject.exception;

public class LibroNotFoundException extends RuntimeException {

    public LibroNotFoundException(Long id){
        super(String.format("Libro with Id: %d not found",id));
    }

}
