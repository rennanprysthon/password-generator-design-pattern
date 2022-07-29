package br.com.ifpe.edu.recife.passwordgenerator.generator;

import java.util.Random;

public class NumbersDecorator extends PasswordGenerator {
    private static final int RANGE = 9;
    private final PasswordGenerator passwordGenerator;
    public NumbersDecorator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public String generatePassword(int quantity) {
        StringBuilder stringBuilder = new StringBuilder(quantity);

        for (int i = quantity; i > 0; i--) {
            Random random = new Random();
            stringBuilder.append(random.nextInt(RANGE));
        }

        return passwordGenerator.generatePassword(quantity) + stringBuilder;
    }
}
