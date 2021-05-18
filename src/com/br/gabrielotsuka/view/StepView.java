package com.br.gabrielotsuka.view;

import com.br.gabrielotsuka.data.Automaton;
import com.br.gabrielotsuka.repository.RegexRepository;
import com.br.gabrielotsuka.service.AutomatonService;

import javax.swing.*;
import java.awt.*;

public class StepView extends JFrame{

    private final JTextField textField;

    private final JButton validateButton = new JButton("VALIDAR");
    private final JButton changeAutomatonButton = new JButton("TROCAR REGEX");

    Automaton automaton;
    String sequence;
    AutomatonService automatonService;
    RegexRepository regexRepository;

    public StepView(RegexRepository regexRepository, Automaton automaton) {
        this.regexRepository = regexRepository;
        this.automatonService = new AutomatonService();
        this.automaton = automaton;

        setVisible(true);
        setResizable(false);
        setSize(800, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel textRegex = new JLabel("Expressão Regular");
        textRegex.setBounds(20, 10, 360, 30);
        textRegex.setHorizontalAlignment(SwingConstants.CENTER);
        add(textRegex);

        JLabel infixRegexLabel = new JLabel("Infixa: ");
        infixRegexLabel.setBounds(20, 90, 60, 20);
        add(infixRegexLabel);

        JTextField infixField = new JTextField(regexRepository.getRegularExpressions().get(0));
        infixField.setBounds(90, 90, 290, 20);
        infixField.setCaretPosition(0);
        add(infixField);

        JLabel postfixRegexLabel = new JLabel("Posfixa: ");
        postfixRegexLabel.setBounds(20, 140, 60, 20);
        add(postfixRegexLabel);

        JTextField postfixField = new JTextField(regexRepository.getRegularExpressions().get(1));
        postfixField.setBounds(90, 140, 290, 20);
        postfixField.setCaretPosition(0);
        add(postfixField);

        JLabel textChain = new JLabel("Cadeia:");
        textChain.setBounds(20, 190, 60, 20);
        add(textChain);

        textField = new JTextField();
        textField.setBounds(90, 190, 290, 20);
        add(textField);

        validateButton.setBorder(BorderFactory.createEtchedBorder());
        validateButton.setBounds(170, 225 , 90, 20);
        add(validateButton);

        JLabel textAutomaton = new JLabel("AFND-\u03B5");
        textAutomaton.setBounds(420, 10, 360, 30);
        textAutomaton.setHorizontalAlignment(SwingConstants.CENTER);
        add(textAutomaton);

        JScrollPane automatonPanel = new JScrollPane(buildTextArea(automaton.toString()));
        automatonPanel.setBounds(420, 50, 355, 195);
        add(automatonPanel);

        changeAutomatonButton.setBounds(20, 265, 760, 20);
        add(changeAutomatonButton);

        this.validateButtonAction();
        this.changeAutomatonButtonAction();
    }

    public void validateButtonAction() {
        validateButton.addActionListener(e -> {
            try {
                sequence = textField.getText();
                automaton.validateSequence(sequence);
                automatonService.belongsToLanguage(sequence, automaton);
            } catch (Exception exception) {
                if (exception.getMessage().equals("n pertence")){
                    JOptionPane.showMessageDialog(this, "Cadeia não pertence à linguagem!", "Não pertence :(", JOptionPane.WARNING_MESSAGE);
                } else if (exception.getMessage().equals("pertence")) {
                    JOptionPane.showMessageDialog(this, "Cadeia pertence à linguagem!", "Pertence :)", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void changeAutomatonButtonAction() {
        changeAutomatonButton.addActionListener(e -> {
            dispose();
            new InitialView(new RegexRepository());
        });
    }

    public JTextArea buildTextArea(String text) {
        JTextArea textArea = new JTextArea();
        textArea.setText(text);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setFont(new Font("", Font.PLAIN, 15));
        textArea.setCaretPosition(0);

        return textArea;
    }
}
