package br.com.ifpe.edu.recife.passwordgenerator.validations;

import java.util.regex.Pattern;

public class HasNumberValidation extends PasswordValidation{
    private static final String NUMBER_REGEX = ".*\\d+.*";

    @Override
    public boolean validate(String password) {
        if (!Pattern.matches(NUMBER_REGEX, password)) {
            throw new ValidationError("Should have a number in this password");
        }

        return checkNext(password);
    }
}
