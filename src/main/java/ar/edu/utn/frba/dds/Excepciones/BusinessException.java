package ar.edu.utn.frba.dds.Excepciones;

public class BusinessException extends Exception {
    public BusinessException(String errorMessage) {
        super(errorMessage);
    }
}
