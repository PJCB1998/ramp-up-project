package ruproject.exception;

public class CarreraNotFoundException extends RuntimeException {

    public CarreraNotFoundException(String name){

        super(String.format("Carrera with Name: %d not found",name));

    }

}
