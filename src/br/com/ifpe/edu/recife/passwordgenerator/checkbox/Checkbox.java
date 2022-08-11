package br.com.ifpe.edu.recife.passwordgenerator.checkbox;

import javax.swing.*;

public abstract class Checkbox extends JCheckBox {
    public Checkbox(String label) {
        this.setText(label);
    }

    public final void toggleState() {
        this.setEnabled(!this.isEnabled());
    }

    public abstract String getSequence();
}
