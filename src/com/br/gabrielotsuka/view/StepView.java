package com.br.gabrielotsuka.view;

import com.br.gabrielotsuka.data.Automaton;
import com.br.gabrielotsuka.service.AutomatonService;

import javax.swing.*;
import java.awt.*;

public class StepView extends JFrame{

    private final JTextField textField;

    private final JButton finishButton = new JButton("FINALIZAR");
    private final JButton sendButton = new JButton("ENVIAR");
    private final JButton changeAutomatonButton = new JButton("TROCAR REGEX");

    private final Automaton automaton;

    String sequence;
    AutomatonService automatonService;
    boolean validSequenceFlag;

    public StepView(Automaton automaton) {
        this.automatonService = new AutomatonService();
        this.automaton = automaton;

        setVisible(true);
        setResizable(false);
        setSize(800, 335);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel textAutomaton = new JLabel("AFND-\u03B5");
        textAutomaton.setBounds(20, 10, 400, 30);
        textAutomaton.setHorizontalAlignment(SwingConstants.CENTER);
        add(textAutomaton);

        JLabel textChain = new JLabel("CADEIA:");
        textChain.setBounds(420, 10, 55, 30);
        add(textChain);

        textField = new JTextField();
        textField.setBounds(478, 15, 205, 20);
        add(textField);

        JScrollPane automatonPanel = new JScrollPane(buildTextArea(automaton.toString()));
        automatonPanel.setBounds(20, 50, 360, 185);
        add(automatonPanel);

        JPanel processPanel = new JPanel();
        processPanel.setBounds(420, 50, 360, 185);
        processPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 128)));
        processPanel.setBackground(new Color(255, 255, 255));
        add(processPanel);

        sendButton.setBounds(690, 15 , 90, 20);
        add(sendButton);

        changeAutomatonButton.setBounds(20, 265, 360, 20);
        add(changeAutomatonButton);

        finishButton.setBorder(BorderFactory.createEtchedBorder());
        finishButton.setBounds(560, 245, 80, 40);
        add(finishButton);

        finishButton.setEnabled(false);

        this.sendButtonAction();
        this.finishButtonAction();
        this.changeAutomatonButtonAction();
    }

    public void sendButtonAction() {
        sendButton.addActionListener(event -> {
            try {
                sequence = textField.getText();
                automaton.validateSequence(sequence);
                finishButton.setEnabled(true);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void finishButtonAction() {
        finishButton.addActionListener(e -> {
            if (validSequenceFlag){
                JOptionPane.showMessageDialog(null,
                        "Cadeia pertence à linguagem representada pelo automato!", "Pertence :D",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Cadeia não pertence à linguagem representada pelo automato!", "Não pertence :(",
                        JOptionPane.WARNING_MESSAGE);
            }
            finishButton.setEnabled(false);
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
        textArea.setEditable(false);
        textArea.setCaretPosition(0);

        return textArea;
    }
}
