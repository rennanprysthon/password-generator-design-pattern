package br.com.ifpe.edu.recife.passwordgenerator.strategy;

import br.com.ifpe.edu.recife.passwordgenerator.observer.PasswordCondition;
import br.com.ifpe.edu.recife.passwordgenerator.observer.PasswordLevel;

public class NumberGenerator extends PasswordCondition {
    public NumberGenerator(String label, PasswordLevel passwordLevel) {
        super(label, passwordLevel);
    }

    @Override
    public String getSequenceStrategy() {
        return "123456789";
    }
}
