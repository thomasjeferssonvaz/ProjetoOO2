package br.edu.utfpr.oo2.ProjetoOO2.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    JTextField textField;
    JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginWindow() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(0, 0, 434, 261);
        contentPane.add(loginPanel);
        loginPanel.setLayout(null);

        textField = new JTextField();
        textField.setBounds(186, 78, 99, 20);
        loginPanel.add(textField);
        textField.setColumns(10);

        JLabel UsernameLabel = new JLabel("Usuário");
        UsernameLabel.setBounds(130, 81, 46, 14);
        loginPanel.add(UsernameLabel);

        JLabel SenhaLabel = new JLabel("Senha");
        SenhaLabel.setBounds(130, 116, 46, 14);
        loginPanel.add(SenhaLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(186, 109, 99, 20);
        loginPanel.add(passwordField);

        JButton LoginBtn = new JButton("Login");
        LoginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validarLogin();
            }
        });
        LoginBtn.setBounds(335, 227, 89, 23);
        loginPanel.add(LoginBtn);

        JButton SairBtn = new JButton("Sair");
        SairBtn.setBounds(10, 227, 89, 23);
        loginPanel.add(SairBtn);
	}

    protected boolean validarLogin() {
        this.dispose();
        MainWindow mainFrame = new MainWindow();
        mainFrame.setVisible(true);
//      JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        return true;
    }

}
