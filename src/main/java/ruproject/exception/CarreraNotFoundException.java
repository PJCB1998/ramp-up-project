package ruproject.exception;

public class CarreraNotFoundException extends RuntimeException {

    public CarreraNotFoundException(String name){

        super("Carrera with Name: " + name +" not found");

    }

}
