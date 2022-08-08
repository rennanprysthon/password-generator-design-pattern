package br.com.ifpe.edu.recife.passwordgenerator.builder;

import br.com.ifpe.edu.recife.passwordgenerator.generator.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordGeneratorBuilder {
    private PasswordGenerator passwordGenerator;
    private int length;
    private boolean withNumberGenerator;
    private boolean withCharactersGenerator;
    private boolean withSpecialCharactersGenerator;

    public PasswordGeneratorBuilder() {
        this.passwordGenerator = new BasePasswordGeneratorDecorator();
        this.length = 5;
    }

    public PasswordGeneratorBuilder withLength(int length) {
        this.length = returnLength(length);
        return this;
    }

    public PasswordGeneratorBuilder withNumbersGenerator(boolean withNumberGenerator) {
        this.withNumberGenerator = withNumberGenerator;
        return this;
    }

    public PasswordGeneratorBuilder withCharactersGenerator(boolean withCharactersGenerator) {
        this.withCharactersGenerator = withCharactersGenerator;
        return this;
    }

    public PasswordGeneratorBuilder withSpecialCharactersGenerator(boolean withSpecialCharactersGenerator) {
        this.withSpecialCharactersGenerator = withSpecialCharactersGenerator;
        return this;
    }

    public String generatePassword() {
        if (withNumberGenerator)
            passwordGenerator = new NumbersDecorator(passwordGenerator);
        if (withSpecialCharactersGenerator)
            passwordGenerator = new SpecialCharactersDecorator(passwordGenerator);
        if (withCharactersGenerator)
            passwordGenerator = new CharactersDecorator(passwordGenerator);

        String password = passwordGenerator.generatePassword(this.length);

        this.passwordGenerator = new BasePasswordGeneratorDecorator();

        if (password.length() > this.length) {
            password = password.substring(0, this.length);
        }

        return shuffle(password);
    }

    private String shuffle(String password) {
        List<String> letters = Arrays.stream(password.split("")).collect(Collectors.toList());
        Collections.shuffle(letters);
        return letters.stream().reduce("", (prev, next) -> prev + next);
    }

    private static int returnLength(int length) {
        return Math.max(length, 5);
    }
}
