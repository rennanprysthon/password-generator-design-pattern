package br.com.ifpe.edu.recife.passwordgenerator.validations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShouldHaveTwoOfThemValidation implements PasswordValidation{
    private static final String NUMBER_REGEX = "(.*)[0-9](.*)";
    private static final String UPPERCASE_REGEX = "(.*)[A-Z](.*)";
    private static final String LOWERCASE_REGEX = "(.*)[a-z](.*)";
    private static final String SPECIAL_CHARACTER_REGEX = "(.*)[/#$@!&%?](.*)";
    private static final List<String> REGEX_LIST = Arrays.asList(
        NUMBER_REGEX,
        UPPERCASE_REGEX,
        UPPERCASE_REGEX,
        SPECIAL_CHARACTER_REGEX
    );

    private static final String VALIDATION_ERROR_MESSAGE = "Recomendamos adicionar mais outra condição ao gerador de senha, para deixa-la mais segura";
    private static final int MIN_CONDITION = 2;

    private PasswordValidation passwordValidation;
    public ShouldHaveTwoOfThemValidation() {}
    public ShouldHaveTwoOfThemValidation(PasswordValidation passwordValidation) {
        this.passwordValidation = passwordValidation;
    }

    @Override
    public void validatePasssword(String password) throws ValidationError {
        int quantityOfConditions = 0;

        if (password.matches(NUMBER_REGEX))
            quantityOfConditions++;
        if (password.matches(UPPERCASE_REGEX))
            quantityOfConditions++;
        if (password.matches(LOWERCASE_REGEX))
            quantityOfConditions++;
        if (password.matches(SPECIAL_CHARACTER_REGEX))
            quantityOfConditions++;

        if (quantityOfConditions < MIN_CONDITION)
            throw new ValidationError(VALIDATION_ERROR_MESSAGE);

        if (passwordValidation != null) {
            this.passwordValidation.validatePasssword(password);
        }
    }
}
