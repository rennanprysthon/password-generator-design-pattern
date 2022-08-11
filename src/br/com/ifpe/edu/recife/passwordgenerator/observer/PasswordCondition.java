package br.com.ifpe.edu.recife.passwordgenerator.observer;

import br.com.ifpe.edu.recife.passwordgenerator.strategy.GenerationStrategy;

import javax.swing.*;

public abstract class PasswordCondition extends JCheckBox implements PasswordCheckboxObserver, GenerationStrategy {
    private final PasswordLevel passwordLevel;
    public PasswordCondition(String label, PasswordLevel passwordLevel) {
        super(label);
        this.passwordLevel = passwordLevel;
    }

    public final void updateState(PasswordLevel passwordLevel) {
        this.setEnabled(passwordLevel.isGreatherThan(this.passwordLevel));
    }
}
