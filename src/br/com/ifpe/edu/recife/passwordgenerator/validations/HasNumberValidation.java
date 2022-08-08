package br.com.ifpe.edu.recife.passwordgenerator.validations;

import java.util.regex.Pattern;

public class HasNumberValidation implements PasswordValidation{
    private static final String NUMBER_REGEX = ".*\\d+.*";
    private PasswordValidation passwordValidation;

    public HasNumberValidation() {}
    public HasNumberValidation(PasswordValidation passwordValidation) {
        this.passwordValidation = passwordValidation;
    }

    @Override
    public void validatePasssword(String password) throws ValidationError {
        if (!Pattern.matches(NUMBER_REGEX, password)) {
            throw new ValidationError("Should have a number in this password");
        }

        if (passwordValidation != null) {
            passwordValidation.validatePasssword(password);
        }
    }
}
