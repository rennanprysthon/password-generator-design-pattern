package br.com.ifpe.edu.recife.passwordgenerator.validations;

public class ShouldHaveTwoOfThemValidation implements PasswordValidation{
    private static final String NUMBER_REGEX = "(.*)[0-9](.*)";
    private static final String CHARACTER_REGEX = "(.*)[a-zA-Z](.*)";
    private static final String SPECIAL_CHARACTER_REGEX = "(.*)[/#$@!&%*?](.*)";

    private static final String VALIDATION_ERROR_MESSAGE = "Password should have at least two of conditions above";
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
        if (password.matches(CHARACTER_REGEX))
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
