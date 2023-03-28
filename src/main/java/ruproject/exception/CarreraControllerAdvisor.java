package ruproject.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.time.LocalDate;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class CarreraControllerAdvisor{

    @ExceptionHandler(CarreraNotFoundException.class)
    protected ResponseEntity <Object> handleCarreraNotFoundException(CarreraNotFoundException ex, WebRequest request){

        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("message","Carrera Not Found");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

    }


}