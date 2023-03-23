package ruproject.exception;

public class MateriaNotFoundException extends RuntimeException {

    public MateriaNotFoundException(String name){
        super(String.format("Materia with Name: %d not found ",name));
    }

}
