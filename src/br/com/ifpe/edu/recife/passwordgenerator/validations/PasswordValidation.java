package br.com.ifpe.edu.recife.passwordgenerator.validations;

public interface PasswordValidation {
    void validatePasssword(String password) throws ValidationError;
}
