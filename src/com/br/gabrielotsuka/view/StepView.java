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
        setSize(400, 335);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel text = new JLabel("CADEIA:");
        text.setBounds(20, 10, 55, 30);
        add(text);

        textField = new JTextField();
        textField.setBounds(78, 15, 205, 20);
        add(textField);

        JPanel panel = new JPanel();
        panel.setBounds(20, 50, 360, 150);
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 128)));
        panel.setBackground(new Color(255, 255, 255));
        add(panel);

        sendButton.setBounds(290, 15 , 90, 20);
        add(sendButton);

        changeAutomatonButton.setBounds(20, 265, 360, 20);
        add(changeAutomatonButton);

        finishButton.setBorder(BorderFactory.createEtchedBorder());
        finishButton.setBounds(160, 210, 80, 40);
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
}
