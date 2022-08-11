package br.com.ifpe.edu.recife.passwordgenerator.strategy;

import br.com.ifpe.edu.recife.passwordgenerator.observer.PasswordCondition;
import br.com.ifpe.edu.recife.passwordgenerator.observer.PasswordLevel;

public class SpecialGenerator extends PasswordCondition {
    public SpecialGenerator(String label, PasswordLevel passwordLevel) {
        super(label, passwordLevel);
    }

    @Override
    public String getSequenceStrategy() {
       return "@#@$%%*";
    }
}
