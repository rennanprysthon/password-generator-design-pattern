package br.com.ifpe.edu.recife.passwordgenerator.generator;

import java.util.Random;

public class UpperCaseLettersDecorator extends PasswordGenerator {
    private final PasswordGenerator passwordGenerator;
    public UpperCaseLettersDecorator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public String generatePassword(int quantity) {
        StringBuilder stringBuilder = new StringBuilder(quantity);
        for (int i = quantity; i > 0; i--) {
            Random random = new Random();
            char character = (char)(random.nextInt(26) + 'A');
            stringBuilder.append(character);
        }

        return passwordGenerator.generatePassword(quantity) + stringBuilder;
    }
}
