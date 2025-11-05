package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtfUsername;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private UsuarioService usuarioService;

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





	public LoginWindow() {
        this.usuarioService = new UsuarioService();
        this.initComponent();
        this.getRootPane().setDefaultButton(loginBtn);
	}

    private void initComponent(){
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

        txtfUsername = new JTextField();
        txtfUsername.setBounds(186, 78, 99, 20);
        loginPanel.add(txtfUsername);
        txtfUsername.setColumns(10);

        JLabel UsernameLabel = new JLabel("Usuário");
        UsernameLabel.setBounds(130, 81, 46, 14);
        loginPanel.add(UsernameLabel);

        JLabel SenhaLabel = new JLabel("Senha");
        SenhaLabel.setBounds(130, 116, 46, 14);
        loginPanel.add(SenhaLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(186, 109, 99, 20);
        loginPanel.add(passwordField);

        loginBtn = new JButton("Login");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    validarLogin();
                } catch (SQLException | IOException error) {
                    JOptionPane.showMessageDialog(loginPanel, "Erro: \n"+error.getMessage(), "Erro de Login", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        loginBtn.setBounds(335, 227, 89, 23);
        loginPanel.add(loginBtn);

        JButton SairBtn = new JButton("Sair");
        SairBtn.setBounds(10, 227, 89, 23);
        loginPanel.add(SairBtn);
    }

    protected boolean validarLogin() throws SQLException, IOException {
        Usuario usuario = this.usuarioService.buscarUsuarioPorUsername(txtfUsername.getText());
        if (usuario != null) {
            this.dispose();
            MainWindow mainFrame = new MainWindow();
            mainFrame.setVisible(true);
            return true;
        } else{
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
