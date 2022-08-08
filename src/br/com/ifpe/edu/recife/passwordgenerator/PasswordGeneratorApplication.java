package br.com.ifpe.edu.recife.passwordgenerator;

import br.com.ifpe.edu.recife.passwordgenerator.builder.PasswordGeneratorBuilder;
import br.com.ifpe.edu.recife.passwordgenerator.validations.*;
import org.w3c.dom.Text;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Arrays;
import java.util.regex.Pattern;

public class PasswordGeneratorApplication extends JFrame {
    private final PasswordGeneratorBuilder passwordGeneratorBuilder;
    private final PasswordValidation passwordValidation;
    public PasswordGeneratorApplication() {
        this.passwordGeneratorBuilder = new PasswordGeneratorBuilder();
        this.passwordValidation = PasswordValidation.link(
            new MinLengthValidation(),
            new MaxLengthValidation(),
            new HasNumberValidation()
        );
    }

    private JTextField textField;

    private JPanel getHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("PasswordGenerator");
        title.setFont(new Font("Serif", Font.BOLD, 24));

        header.add(title);
        header.setMaximumSize(new Dimension(640, 150));

        return header;
    }

    private JPanel getMenu() {
        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        menu.setBackground(Color.WHITE);

        textField = new JTextField(16);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        textField.setFont(Font.getFont(Font.MONOSPACED));
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextFieldChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {}

            @Override
            public void changedUpdate(DocumentEvent e) {
                onTextFieldChange();
            }
        });

        JButton jButton = new JButton("Gerar");
        jButton.addActionListener((value) -> {
            String password = passwordGeneratorBuilder.generatePassword();
            this.textField.setText(password);
        });

        menu.add(textField);
        menu.add(jButton);
        menu.setMaximumSize(new Dimension(640, 50));
        return menu;
    }

    private void onTextFieldChange() {
        String password = this.textField.getText();
        try {
            this.passwordValidation.validate(password);
        } catch(ValidationError error) {
            System.out.println(error.getMessage());
        }
    }

    private JPanel getRadioSection() {
        JPanel checkboxSection = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JCheckBox comCaracteresEspeciaisRadioButton = new JCheckBox("Com caracteres especiais");
        comCaracteresEspeciaisRadioButton.addActionListener((value) -> {
            this.passwordGeneratorBuilder.withSpecialCharactersGenerator(comCaracteresEspeciaisRadioButton.isSelected());
        });

        JCheckBox comCaracteresButton = new JCheckBox("Com caracteres");
        comCaracteresButton.addActionListener((value) -> {
            this.passwordGeneratorBuilder.withCharactersGenerator(comCaracteresButton.isSelected());
        });

        JCheckBox comNumerosButton = new JCheckBox("Com numeros");
        comNumerosButton.addActionListener((value) -> {
            this.passwordGeneratorBuilder.withNumbersGenerator(comNumerosButton.isSelected());
        });

        checkboxSection.add(comCaracteresEspeciaisRadioButton);
        checkboxSection.add(comCaracteresButton);
        checkboxSection.add(comNumerosButton);
        checkboxSection.setMaximumSize(new Dimension(640, 50));
        return checkboxSection;
    }

    private void showScreen() {
        this.setPreferredSize(new Dimension(618, 300));
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

        this.pack();
    }

    public static void main(String[] args) {
        PasswordGeneratorApplication app = new PasswordGeneratorApplication();
        app.showScreen();
    }
}
