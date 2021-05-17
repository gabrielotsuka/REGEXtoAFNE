package com.br.gabrielotsuka.view;

import com.br.gabrielotsuka.data.Automaton;
import com.br.gabrielotsuka.service.ConverterService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.CENTER;

public class InitialView extends JFrame implements ActionListener {

    JLabel titleLabel = new JLabel("Testador de Expressões Regulares");
    JLabel regexLabel = new JLabel("Regex:");
    JTextField regexTextField = new JTextField();
    JButton validateButton = new JButton("Validar");
    JButton aboutButton = new JButton("Sobre...");

    public InitialView(){
        setupFrame();
        setupTitle();
        setupRegexLabel();
        setupRegexTextField();
        setupValidateButton();
        setupAboutButton();
    }

    private void setupFrame() {
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,200);
    }

    private void setupTitle() {
        titleLabel.setHorizontalAlignment(CENTER);
        titleLabel.setFont(new Font(null, Font.BOLD, 20));
        titleLabel.setBounds(0, 20, 450, 20);
        add(titleLabel);
    }

    private void setupRegexLabel() {
        regexLabel.setHorizontalAlignment(CENTER);
        regexLabel.setBounds(20, 70, 50, 20);
        add(regexLabel);
    }

    private void setupRegexTextField() {
        regexTextField.setBounds(75, 70, 250, 20);
        add(regexTextField);
    }

    private void setupValidateButton() {
        validateButton.setBounds(330,70 ,90,20);
        validateButton.addActionListener(this);
        add(validateButton);
    }

    private void setupAboutButton() {
        aboutButton.setBounds(170,115 ,100,30);
        aboutButton.addActionListener(this);
        add(aboutButton);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == aboutButton) {
            JOptionPane.showMessageDialog(this, "\n\nO programa tem a finalidade de simular o " +
                            "funcionamento de Automatos Finitos \nNão Deterministicos com 'ε' Transições(AFNE) " +
                            "que representam\numa expressão regular forma genérica.\nPara tal, deve-se inserir " +
                            "uma expressão regular válida contendo os possíveis\n" +
                            " operadores e qualquer símbolo que não seja 'ε':\n" +
                            "   - Concatenação: .\n" +
                            "   - Fechamento: *\n" +
                            "   - União: +\n" +
                            "Posteriormente, basta adicionar as cadeias que deseja testar, tendo a\npossibilidade de" +
                            " checar se ela pertence ou nao a linguagem representada por eles.\n\n",
                    "Sobre o programa", JOptionPane.INFORMATION_MESSAGE);
        } else if (event.getSource() == validateButton) {
            ConverterService converterService = new ConverterService();
            try {
                Automaton automaton = converterService.convert(regexTextField.getText());
                System.out.println(automaton);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
