package br.com.ifpe.edu.recife.passwordgenerator;

import br.com.ifpe.edu.recife.passwordgenerator.builder.PasswordGeneratorBuilder;
import br.com.ifpe.edu.recife.passwordgenerator.observer.*;
import br.com.ifpe.edu.recife.passwordgenerator.strategy.LowerCaseGenerator;
import br.com.ifpe.edu.recife.passwordgenerator.strategy.NumberGenerator;
import br.com.ifpe.edu.recife.passwordgenerator.strategy.SpecialGenerator;
import br.com.ifpe.edu.recife.passwordgenerator.strategy.UppercaseGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PasswordGeneratorApplication extends JFrame {
    private static final int WIDTH = 840;
    private static final int HEIGHT = 300;
    private static final Integer[] AVAIABLE_SELECT_LENGHTS = new Integer[] { 5, 10, 15, 20, 25 };

    private final List<PasswordCondition> passwordConditions;
    private final PasswordGeneratorBuilder passwordGeneratorBuilder;
    private JTextField passwordInput;
    private JComboBox<String> comboBox;

    public PasswordGeneratorApplication() {
        this.passwordGeneratorBuilder = new PasswordGeneratorBuilder();
        passwordConditions = Arrays.asList(
            new LowerCaseGenerator("Letras minusculas", PasswordLevel.EASY),
            new NumberGenerator("Numeros", PasswordLevel.EASY),
            new UppercaseGenerator("Letras maiusculas", PasswordLevel.MEDIUM),
            new SpecialGenerator("Caracteres especiais", PasswordLevel.HARD)
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

        this.pack();
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
            passwordInput.setText(passwordGeneratorBuilder.generatePassword());
            passwordGeneratorBuilder.reset();
        });
        buttonSection.add(button);

        return buttonSection;
    }

    private JPanel renderCheckboxSection(PasswordManager passwordManager) {
        JPanel checkboxSection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkboxSection.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT / 2));

        for (PasswordCondition passwordCondition : passwordConditions) {
            passwordManager.addEvent(passwordCondition);
            checkboxSection.add(passwordCondition);
        }

        return checkboxSection;
    }

    private JPanel renderRadioSection(PasswordManager passwordManager) {
        JPanel radioSection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioSection.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT / 2));

        JLabel title = new JLabel("Nivel da senha");
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

        JPanel selectSection = new JPanel();
        selectSection.setPreferredSize(new Dimension(WIDTH / 3 , HEIGHT / 2));

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

    private JPanel getHeader() {
        JPanel headerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerWrapper.setPreferredSize(new Dimension(WIDTH, HEIGHT/ 4));
        JPanel header = new JPanel(new GridLayout(2, 0));

        header.setPreferredSize(new Dimension(WIDTH - 20, (HEIGHT / 4) - 20));
        JLabel title = new JLabel("Gerador de senha");
        header.add(title);

        passwordInput = new JTextField();
        passwordInput.setColumns(20);
        header.add(passwordInput);

        headerWrapper.add(header);

        return headerWrapper;
    }
}
