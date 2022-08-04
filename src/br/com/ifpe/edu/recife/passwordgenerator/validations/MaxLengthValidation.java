package br.com.ifpe.edu.recife.passwordgenerator.validations;

public class MaxLengthValidation extends PasswordValidation{
    private static final int MAX_LENGTH = 20;
    private static final String VALIDATION_ERROR_MESSAGE = "Password should not be greater than " + MAX_LENGTH;

    @Override
    public boolean validate(String password) {
        if (password.length() > MAX_LENGTH) {
            throw new ValidationError(password + VALIDATION_ERROR_MESSAGE);
        }

        return checkNext(password);
    }
}
