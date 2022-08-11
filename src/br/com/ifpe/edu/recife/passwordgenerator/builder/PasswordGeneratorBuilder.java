package br.com.ifpe.edu.recife.passwordgenerator.builder;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class PasswordGeneratorBuilder {
    private static final String SPLIT_REGEX = "(?<=\\G.{%d})";
    private int length;
    private final List<String> rules;

    public PasswordGeneratorBuilder() {
        this.rules = new ArrayList<>();
        this.length = 5;
    }

    public PasswordGeneratorBuilder addLength(int length) {
        this.length = returnLength(length);
        return this;
    }

    public PasswordGeneratorBuilder addRule(String rule) {
        this.rules.add(shuffle(rule));
        return this;
    }

    public String generatePassword() {
        StringBuilder sb = new StringBuilder();

        String[] chars = this.rules.toArray(new String[0]);

        for (int i = 0; i < this.length; i++) {
            for (int x = 0; x < chars.length; x++) {
                char charVal = chars[x].charAt(0);
                chars[x] = chars[x].substring(1);
                sb.append(charVal);

                if (sb.toString().length() == this.length) {
                    return sb.toString();
                }
            }
        }

        return sb.toString();
    }

    public void reset() {
        this.rules.clear();
        this.length = 5;
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
