package ruproject.exception;

public class MateriaNotFoundException extends RuntimeException {

    public MateriaNotFoundException(String name){
        super("Materia with Name: "+ name + " not found");
    }

}
