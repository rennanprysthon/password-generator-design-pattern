package br.com.ifpe.edu.recife.passwordgenerator.builder;

import br.com.ifpe.edu.recife.passwordgenerator.generator.PasswordGenerator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class PasswordGeneratorBuilder {
    private static final String SPLIT_REGEX = "(?<=\\G.{%d})";
    private int length;
    private List<String> rules;
    private PasswordGenerator passwordGenerator;

    public PasswordGeneratorBuilder() {
        this.rules = new ArrayList<>();
        this.length = 5;
    }

    public PasswordGeneratorBuilder addLength(int length) {
        this.length = returnLength(length);
        return this;
    }

    public PasswordGeneratorBuilder addPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
        return this;
    }

    public String generatePassword() {
        String allCharacteres = this.passwordGenerator.generatePassword(this.length);
        String regex = String.format(SPLIT_REGEX, this.length);
        String[] elements = allCharacteres.split(regex);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.length; i++) {
            for (int x = 0; x < elements.length; x++) {
                char charVal = elements[x].charAt(0);
                elements[x] = elements[x].substring(1);
                sb.append(charVal);

                if (sb.toString().length() == this.length) {
                    return shuffle(sb.toString());
                }
            }
        }

        return shuffle(sb.toString());
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
