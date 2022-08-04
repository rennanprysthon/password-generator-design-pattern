package br.com.ifpe.edu.recife.passwordgenerator.validations;

public class MinLengthValidation extends PasswordValidation{
    private static final int MIN_LENGTH = 5;
    private static final String VALIDATION_ERROR_MESSAGE = "Password should be greater than " + MIN_LENGTH;

    @Override
    public boolean validate(String password) {
        if (password.length() < MIN_LENGTH) {
            throw new ValidationError(password + VALIDATION_ERROR_MESSAGE);
        }

        return checkNext(password);
    }
}
