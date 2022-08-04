package br.com.ifpe.edu.recife.passwordgenerator.validations;

public abstract class PasswordValidation {
    private PasswordValidation nextValidation;

    public static PasswordValidation link(PasswordValidation first, PasswordValidation... others) {
        PasswordValidation head = first;

        for (PasswordValidation nextInChain : others) {
            head.nextValidation = nextInChain;
            head = nextInChain;
        }

        return first;
    }

    public abstract boolean validate(String password) throws ValidationError;

    protected boolean checkNext(String password) {
        if (nextValidation == null) {
            return true;
        }

        return nextValidation.validate(password);
    }
}
