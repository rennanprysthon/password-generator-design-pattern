package br.com.ifpe.edu.recife.passwordgenerator.observer;

import java.util.ArrayList;
import java.util.List;

public class PasswordManager {
    private final List<PasswordCheckboxObserver> events = new ArrayList<>();

    public void addEvent(PasswordCheckboxObserver passwordCondition) {
        this.events.add(passwordCondition);
    }

    public void notify(PasswordLevel passwordLevel) {
        for (PasswordCheckboxObserver passwordCondition : events) {
            passwordCondition.updateState(passwordLevel);
        }
    }
}
