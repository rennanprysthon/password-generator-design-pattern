package br.com.ifpe.edu.recife.passwordgenerator;

import br.com.ifpe.edu.recife.passwordgenerator.builder.PasswordGeneratorBuilder;

import javax.swing.*;
import java.awt.*;

public class PasswordGeneratorApplication extends JFrame {
    private final PasswordGeneratorBuilder passwordGeneratorBuilder;
    public PasswordGeneratorApplication() {
        this.passwordGeneratorBuilder = new PasswordGeneratorBuilder();
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

        textField = new JTextField(8);
        textField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height) );
        textField.setFont(Font.getFont(Font.MONOSPACED));

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

    private JPanel getRadioSection() {
        JPanel radioSection = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JRadioButton comCaracteresEspeciaisRadioButton = new JRadioButton("Com caracteres especiais");
        comCaracteresEspeciaisRadioButton.addActionListener((value) -> {
            this.passwordGeneratorBuilder.withSpecialCharactersGenerator(comCaracteresEspeciaisRadioButton.isSelected());
        });

        JRadioButton comCaracteresButton = new JRadioButton("Com caracteres");
        comCaracteresButton.addActionListener((value) -> {
            this.passwordGeneratorBuilder.withCharactersGenerator(comCaracteresButton.isSelected());
        });

        JRadioButton comNumerosButton = new JRadioButton("Com numeros");
        comNumerosButton.addActionListener((value) -> {
            this.passwordGeneratorBuilder.withNumbersGenerator(comNumerosButton.isSelected());
        });

        radioSection.add(comCaracteresEspeciaisRadioButton);
        radioSection.add(comCaracteresButton);
        radioSection.add(comNumerosButton);
        radioSection.setMaximumSize(new Dimension(640, 50));
        return radioSection;
    }

    private void showScreen() {
        this.setPreferredSize(new Dimension(618, 726));
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
    }

    public static void main(String[] args) {
        PasswordGeneratorApplication app = new PasswordGeneratorApplication();
        app.showScreen();
    }

}
