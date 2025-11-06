package br.edu.utfpr.oo2.ProjetoOO2.gui;

import javax.swing.*;
import java.awt.*;

public class LoginLoadingDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    public LoginLoadingDialog(JFrame owner) {
        super(owner, "Carregando", true);

        setUndecorated(true);
        setSize(300, 100);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JLabel messageLabel = new JLabel("Logando...", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Tahoma", Font.BOLD, 12));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(messageLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 20, 10, 20); // Mais espaço nas laterais para a barra
        panel.add(progressBar, gbc);

        setContentPane(panel);
    }
}