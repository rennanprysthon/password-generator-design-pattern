package br.com.ifpe.edu.recife.passwordgenerator.strategy;

import br.com.ifpe.edu.recife.passwordgenerator.observer.PasswordCondition;
import br.com.ifpe.edu.recife.passwordgenerator.observer.PasswordLevel;

public class LowerCaseGenerator extends PasswordCondition {
    public LowerCaseGenerator(String label, PasswordLevel passwordLevel) {
        super(label, passwordLevel);
    }

    @Override
    public String getSequenceStrategy() {
        StringBuilder stringBuilder = new StringBuilder(26);
        for (int index = 0; index < 26; index++) {
            char character = (char)(index + 'a');
            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }
}
