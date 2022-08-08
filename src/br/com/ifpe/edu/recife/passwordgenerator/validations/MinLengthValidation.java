package br.com.ifpe.edu.recife.passwordgenerator.validations;

public class MinLengthValidation implements PasswordValidation{
    private static final int MIN_LENGTH = 5;
    private static final String VALIDATION_ERROR_MESSAGE = "Password should be greater than " + MIN_LENGTH;
    private PasswordValidation passwordValidation;

    public MinLengthValidation() {}
    public MinLengthValidation(PasswordValidation passwordValidation) {
        this.passwordValidation = passwordValidation;
    }

    @Override
    public void validatePasssword(String password) throws ValidationError{
        if (password.length() < MIN_LENGTH) {
            throw new ValidationError(VALIDATION_ERROR_MESSAGE);
        }

        if (passwordValidation != null) {
            this.passwordValidation.validatePasssword(password);
        }
    }
}
