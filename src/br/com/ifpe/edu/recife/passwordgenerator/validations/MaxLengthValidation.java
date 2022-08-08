package br.com.ifpe.edu.recife.passwordgenerator.validations;

public class MaxLengthValidation implements PasswordValidation{
    private static final int MAX_LENGTH = 20;
    private static final String VALIDATION_ERROR_MESSAGE = "Password should not be greater than " + MAX_LENGTH;
    private PasswordValidation passwordValidation;

    public MaxLengthValidation() {}
    public MaxLengthValidation(PasswordValidation passwordValidation) {
        this.passwordValidation = passwordValidation;
    }

    @Override
    public void validatePasssword(String password) throws ValidationError {
        if (password.length() > MAX_LENGTH) {
            throw new ValidationError(password + VALIDATION_ERROR_MESSAGE);
        }

        if (passwordValidation != null) {
            passwordValidation.validatePasssword(password);
        }
    }
}
