package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.gui.usuario.usuarioWorkers.LoginTaskWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.usuario.CadastroUsuarioWindow;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
    private JTextField txtfUsername;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private UsuarioService usuarioService;
    private Usuario user;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
                    frame.setVisible(true);
				} catch (Exception e) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(0, 0, 434, 261);
        contentPane.add(loginPanel);
        loginPanel.setLayout(null);

        txtfUsername = new JTextField();
        txtfUsername.setBounds(186, 98, 99, 20);
        loginPanel.add(txtfUsername);
        txtfUsername.setColumns(10);

        JLabel UsernameLabel = new JLabel("Usuário");
        UsernameLabel.setBounds(130, 101, 46, 14);
        loginPanel.add(UsernameLabel);

        JLabel SenhaLabel = new JLabel("Senha");
        SenhaLabel.setBounds(130, 136, 46, 14);
        loginPanel.add(SenhaLabel);


        passwordField = new JPasswordField();
        passwordField.setBounds(186, 129, 99, 20);
        loginPanel.add(passwordField);

        loginBtn = new JButton("Login");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setupLoginAction(loginPanel);
            }
        });
        loginBtn.setBounds(335, 227, 89, 23);
        loginPanel.add(loginBtn);

        JButton SairBtn = new JButton("Sair");
        SairBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                System.exit(0);
        	}
        });
        SairBtn.setBounds(10, 227, 89, 23);
        loginPanel.add(SairBtn);

        JLabel lblLoginCentral;
        lblLoginCentral = new JLabel("Login");
        lblLoginCentral.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblLoginCentral.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoginCentral.setBounds(130, 28, 155, 37);
        loginPanel.add(lblLoginCentral);
        
        JLabel lblNewLabel = new JLabel("Não tem usuário? Clique aqui!");
        lblNewLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
                CadastroUsuarioWindow cadastroUsuarioWindow = new CadastroUsuarioWindow(true);
                cadastroUsuarioWindow.setVisible(true);
        	}
        });
        lblNewLabel.setBounds(186, 160, 194, 14);
        loginPanel.add(lblNewLabel);
    }

    private void setupLoginAction(JPanel loginPanel) {
        String username = txtfUsername.getText();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginPanel, "Preencha o usuário e a senha.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        GenericLoadingDialog loadingDialog = new GenericLoadingDialog(LoginWindow.this, "Logando");

        LoginTaskWorker worker = new LoginTaskWorker(
                username,
                password,
                usuarioService,
                LoginWindow.this,
                loadingDialog
        );

        worker.execute();
        loadingDialog.setVisible(true);
    }
}
