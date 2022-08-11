package br.com.ifpe.edu.recife.passwordgenerator;

import br.com.ifpe.edu.recife.passwordgenerator.builder.PasswordGeneratorBuilder;
import br.com.ifpe.edu.recife.passwordgenerator.observer.*;
import br.com.ifpe.edu.recife.passwordgenerator.strategy.LowerCaseGenerator;
import br.com.ifpe.edu.recife.passwordgenerator.strategy.NumberGenerator;
import br.com.ifpe.edu.recife.passwordgenerator.strategy.SpecialGenerator;
import br.com.ifpe.edu.recife.passwordgenerator.strategy.UppercaseGenerator;
import br.com.ifpe.edu.recife.passwordgenerator.validations.MinLengthValidation;
import br.com.ifpe.edu.recife.passwordgenerator.validations.PasswordValidation;
import br.com.ifpe.edu.recife.passwordgenerator.validations.ShouldHaveTwoOfThemValidation;
import br.com.ifpe.edu.recife.passwordgenerator.validations.ValidationError;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class PasswordGeneratorApplication extends JFrame {
    private static final int WIDTH = 840;
    private static final int HEIGHT = 400;
    private static final Integer[] AVAIABLE_SELECT_LENGHTS = new Integer[] { 5, 10, 15, 20, 25 };

    private final List<PasswordCondition> passwordConditions;
    private final PasswordGeneratorBuilder passwordGeneratorBuilder;
    private final PasswordValidation passwordValidation;
    private JTextField passwordInput;
    private JComboBox<String> comboBox;
    private JLabel warningMessage;

    public PasswordGeneratorApplication() {
        this.passwordGeneratorBuilder = new PasswordGeneratorBuilder();
        this.passwordConditions = Arrays.asList(
            new LowerCaseGenerator("Letras minusculas", PasswordLevel.EASY),
            new NumberGenerator("Numeros", PasswordLevel.EASY),
            new UppercaseGenerator("Letras maiusculas", PasswordLevel.MEDIUM),
            new SpecialGenerator("Caracteres especiais", PasswordLevel.HARD)
        );
        this.passwordValidation = new ShouldHaveTwoOfThemValidation(
            new MinLengthValidation()
        );
    }

    public static void main(String[] args) {
        PasswordGeneratorApplication app = new PasswordGeneratorApplication();
        app.showScreen();
    }

    private void showScreen() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setBackground(Color.WHITE);

        JPanel header = getHeader();
        this.add(header);

        JPanel mainSection = getMainSection();
        this.add(mainSection);

        JPanel buttonSection = getButtonSection();
        this.add(buttonSection);

        JPanel footerSection = getFooterSection();
        this.add(footerSection);

        this.pack();
    }

    private JPanel getFooterSection() {
        JPanel footerSection = new JPanel();

        this.warningMessage = new JLabel();
        this.warningMessage.setForeground(new Color(208, 208, 66));

        footerSection.add(warningMessage);

        return footerSection;
    }

    private JPanel getButtonSection() {
        JPanel buttonSection = new JPanel();

        JButton button = new JButton("Gerar senha");
        button.addActionListener((actionListener) -> {
            for (PasswordCondition passwordCondition : passwordConditions) {
                if (passwordCondition.isSelected() && passwordCondition.isEnabled()) {
                    passwordGeneratorBuilder.addRule(passwordCondition.getSequenceStrategy());
                }
            }

            passwordGeneratorBuilder.addLength(AVAIABLE_SELECT_LENGHTS[this.comboBox.getSelectedIndex()]);

            String generatedPassword = passwordGeneratorBuilder.generatePassword();

            passwordInput.setText(generatedPassword);

            passwordGeneratorBuilder.reset();
        });
        buttonSection.add(button);

        return buttonSection;
    }

    private JPanel renderCheckboxSection(PasswordManager passwordManager) {
        JPanel checkboxSection = new JPanel(new GridLayout(1 + passwordConditions.size(), 0));
        checkboxSection.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT));

        JLabel label = new JLabel("Condições da senha");
        checkboxSection.add(label);

        for (PasswordCondition passwordCondition : passwordConditions) {
            passwordManager.addEvent(passwordCondition);
            checkboxSection.add(passwordCondition);
        }

        return checkboxSection;
    }

    private JPanel renderRadioSection(PasswordManager passwordManager) {
        JPanel radioSection = new JPanel(new GridLayout(1 + PasswordLevel.values().length, 0));
        radioSection.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT));

        JLabel title = new JLabel("Força da senha");
        radioSection.add(title);
        ButtonGroup group = new ButtonGroup();

        JRadioButton levelButton;
        for (PasswordLevel value : PasswordLevel.values()) {
            levelButton = new JRadioButton(value.getLabel());

            levelButton.addActionListener((event) -> {
                passwordManager.notify(value);
            });

            group.add(levelButton);
            radioSection.add(levelButton);
        }

        group.getElements().nextElement().setSelected(true);
        return radioSection;
    }

    private JPanel renderSelectSection() {
        JPanel selectSection = new JPanel(new FlowLayout());
        selectSection.setPreferredSize(new Dimension(WIDTH / 3 , HEIGHT));

        JLabel label = new JLabel("Tamanho da senha");
        selectSection.add(label);

        String[] selectLabels = Arrays.stream(AVAIABLE_SELECT_LENGHTS).map(String::valueOf).toArray(String[]::new);

        comboBox = new JComboBox<>(selectLabels);

        selectSection.add(comboBox);

        return selectSection;
    }

    private JPanel getMainSection() {
        JPanel mainSection = new JPanel(new GridLayout(1, 3));
        mainSection.setPreferredSize(new Dimension(WIDTH, HEIGHT / 4));
        mainSection.setBackground(Color.WHITE);

        PasswordManager passwordManager = new PasswordManager();

        JPanel radioSection = renderRadioSection(passwordManager);
        JPanel checkboxSection = renderCheckboxSection(passwordManager);
        JPanel selectSection = renderSelectSection();

        mainSection.add(radioSection);
        mainSection.add(checkboxSection);
        mainSection.add(selectSection);

        passwordManager.notify(PasswordLevel.EASY);

        return mainSection;
    }

    private void validatePassword() {
        String password = passwordInput.getText();

        try {
            this.passwordValidation.validatePasssword(password);
        } catch (ValidationError validationError) {
            this.warningMessage.setText(validationError.getMessage());
        }
    }

    private void resetWarningMessage() {
        this.warningMessage.setText("");
    }

    private JPanel getHeader() {
        JPanel headerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerWrapper.setPreferredSize(new Dimension(WIDTH, HEIGHT/ 4));
        JPanel header = new JPanel(new GridLayout(2, 0));

        header.setPreferredSize(new Dimension(WIDTH - 20, (HEIGHT / 4) - 20));
        JLabel title = new JLabel("Gerador de senha");
        header.add(title);

        passwordInput = new JTextField();
        passwordInput.setColumns(20);
        passwordInput.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                validatePassword();
            }
            public void removeUpdate(DocumentEvent e) {
                resetWarningMessage();
            }
            public void insertUpdate(DocumentEvent e) {
                validatePassword();
            }
        });


        header.add(passwordInput);

        headerWrapper.add(header);

        return headerWrapper;
    }
}
