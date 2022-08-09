package br.com.ifpe.edu.recife.passwordgenerator.generator;

import java.util.Random;

public class SpecialCharactersDecorator extends PasswordGenerator {
    private final static String LIST_SPECIAL_CHARACTERES = "/#$@!&%?";

    private final PasswordGenerator passwordGenerator;
    public SpecialCharactersDecorator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public String generatePassword(int quantity) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(quantity);

        for (int i = 0; i < quantity; i++) {
            stringBuilder.append(LIST_SPECIAL_CHARACTERES.charAt(random.nextInt(LIST_SPECIAL_CHARACTERES.length())));
        }

        return passwordGenerator.generatePassword(quantity) + stringBuilder;
    }
}
