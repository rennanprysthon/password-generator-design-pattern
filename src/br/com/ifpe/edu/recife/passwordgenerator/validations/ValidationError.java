package br.com.ifpe.edu.recife.passwordgenerator.validations;

public class ValidationError extends RuntimeException{
    public ValidationError(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationError(String message) {
        super(message);
    }
}
