package br.com.ifpe.edu.recife.passwordgenerator;

import br.com.ifpe.edu.recife.passwordgenerator.builder.PasswordGeneratorBuilder;
import br.com.ifpe.edu.recife.passwordgenerator.generator.*;
import br.com.ifpe.edu.recife.passwordgenerator.validations.MinLengthValidation;
import br.com.ifpe.edu.recife.passwordgenerator.validations.PasswordValidation;
import br.com.ifpe.edu.recife.passwordgenerator.validations.ShouldHaveTwoOfThemValidation;
import br.com.ifpe.edu.recife.passwordgenerator.validations.ValidationError;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PasswordGeneratorApplication extends JFrame {
    private final PasswordGeneratorBuilder passwordGeneratorBuilder;
    private final PasswordValidation passwordValidation;
    private PasswordGenerator passwordGenerator;
    private JTextField textField;
    private JLabel errorText;
    private JCheckBox comCaracteresEspeciaisRadioButton;
    private JCheckBox comLetrasMinusculas;
    private JCheckBox comNumerosButton;
    private JCheckBox comLetrasMaisculas;

    public PasswordGeneratorApplication() {
        this.passwordGeneratorBuilder = new PasswordGeneratorBuilder();
        this.passwordValidation = new MinLengthValidation(
            new ShouldHaveTwoOfThemValidation()
        );
        this.passwordGenerator = new BasePasswordGeneratorDecorator();
    }

    public static void main(String[] args) {
        PasswordGeneratorApplication app = new PasswordGeneratorApplication();
        app.showScreen();
    }

    private JPanel getHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("PasswordGenerator");
        title.setFont(new Font("Serif", Font.BOLD, 24));

        header.add(title);
        header.setPreferredSize(new Dimension(840, 150));

        return header;
    }

    private JPanel getMenu() {
        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        menu.setBackground(Color.WHITE);

        textField = new JTextField(16);
        textField.setPreferredSize(new Dimension(250, 20));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        textField.setFont(Font.getFont(Font.MONOSPACED));
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextFieldChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onTextFieldChange();
            }
        });

        JButton jButton = new JButton("Gerar");
        jButton.addActionListener((value) -> {
            if (isAllCheckboxUnmarked()) {
                errorText.setText("Select at least one condition above");
                return;
            }
            passwordGeneratorBuilder.addPasswordGenerator(passwordGenerator);
            String password = passwordGeneratorBuilder.generatePassword();
            this.textField.setText(password);
        });

        menu.add(textField);
        menu.add(jButton);
        menu.setMaximumSize(new Dimension(840, 50));
        return menu;
    }

    private void onTextFieldChange() {
        String password = this.textField.getText();
        try {
            this.passwordValidation.validatePasssword(password);
            resetApplication();
        } catch (ValidationError error) {
            errorText.setText(error.getMessage());
        }
    }

    private JPanel getRadioSection() {
        JPanel checkboxSection = new JPanel(new FlowLayout(FlowLayout.LEFT));

        comCaracteresEspeciaisRadioButton = new JCheckBox("Com caracteres especiais");
        comCaracteresEspeciaisRadioButton.addActionListener((value) -> {
            this.passwordGenerator = new SpecialCharactersDecorator(passwordGenerator);
        });

        comLetrasMaisculas = new JCheckBox("Com letras maiusculas");
        comLetrasMaisculas.addActionListener((value) -> {
            this.passwordGenerator = new UpperCaseLettersDecorator(passwordGenerator);
        });

        comLetrasMinusculas = new JCheckBox("Com letras minusculas");
        comLetrasMinusculas.addActionListener((value) -> {
            this.passwordGenerator = new LowerCaseLettersDecorator(passwordGenerator);
        });

        comNumerosButton = new JCheckBox("Com numeros");
        comNumerosButton.addActionListener((value) -> {
            this.passwordGenerator = new NumbersDecorator(passwordGenerator);
        });

        checkboxSection.add(comCaracteresEspeciaisRadioButton);
        checkboxSection.add(comLetrasMaisculas);
        checkboxSection.add(comLetrasMinusculas);
        checkboxSection.add(comNumerosButton);

        checkboxSection.setMaximumSize(new Dimension(840, 50));
        return checkboxSection;
    }

    private JPanel getSelectSection() {
        JPanel selectSection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] ALL_SIZES = {"5", "10", "15", "20", "25"};

        JComboBox<String> lengthSelect = new JComboBox<>(ALL_SIZES);

        lengthSelect.setSelectedIndex(0);
        lengthSelect.addActionListener((event) -> {
            JComboBox<String> element = (JComboBox<String>) event.getSource();
            int length = Integer.parseInt((String) element.getSelectedItem());
            this.passwordGeneratorBuilder.addLength(length);
        });

        selectSection.add(lengthSelect);
        return selectSection;
    }

    private JPanel getErrorSection() {
        JPanel errorSection = new JPanel(new FlowLayout(FlowLayout.LEADING));

        this.errorText = new JLabel("");
        this.errorText.setForeground(Color.RED);

        errorSection.add(this.errorText);
        return errorSection;
    }

    private void showScreen() {
        this.setPreferredSize(new Dimension(840, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setBackground(Color.WHITE);

        JPanel header = getHeader();
        this.add(header);

        JPanel menu = getMenu();
        this.add(menu);

        JPanel radioSection = getRadioSection();
        this.add(radioSection);

        JPanel selectSection = getSelectSection();
        this.add(selectSection);

        JPanel errorSection = getErrorSection();
        this.add(errorSection);

        this.pack();
    }

    private boolean isAllCheckboxUnmarked() {
        if (comCaracteresEspeciaisRadioButton.isSelected()) {
           return false;
        }

        if (comLetrasMaisculas.isSelected()) {
            return false;
        }


        if (comLetrasMinusculas.isSelected()) {
            return false;
        }


        return !comNumerosButton.isSelected();
    }

    private void resetApplication() {
        errorText.setText("");
        comCaracteresEspeciaisRadioButton.setSelected(false);
        comLetrasMinusculas.setSelected(false);
        comNumerosButton.setSelected(false);
        comLetrasMaisculas.setSelected(false);
        this.passwordGenerator = new BasePasswordGeneratorDecorator();
    }
}
