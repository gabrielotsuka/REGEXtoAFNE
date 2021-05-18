package com.br.gabrielotsuka.view;

import com.br.gabrielotsuka.data.Automaton;
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

    public StepView(Automaton automaton) {
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

        JLabel postfixRegexLabel = new JLabel("Posfixa: ");
        postfixRegexLabel.setBounds(20, 140, 60, 20);
        add(postfixRegexLabel);

        JLabel textChain = new JLabel("Cadeia:");
        textChain.setBounds(20, 220, 55, 30);
        add(textChain);

        textField = new JTextField();
        textField.setBounds(78, 225, 205, 20);
        add(textField);

        validateButton.setBorder(BorderFactory.createEtchedBorder());
        validateButton.setBounds(290, 225 , 90, 20);
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
            new InitialView();
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
