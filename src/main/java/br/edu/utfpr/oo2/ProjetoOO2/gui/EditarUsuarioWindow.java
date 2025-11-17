package br.edu.utfpr.oo2.ProjetoOO2.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

public class EditarUsuarioWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditarUsuarioWindow frame = new EditarUsuarioWindow();
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
	public EditarUsuarioWindow() {
		this.initContent();

	}

    private void initContent() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 505);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel contentPane_1 = new JPanel();
        contentPane_1.setLayout(null);
        contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane_1.setBounds(0, 0, 434, 466);
        contentPane.add(contentPane_1);
        
        JPanel cadastroPanel = new JPanel();
        cadastroPanel.setLayout(null);
        cadastroPanel.setBounds(0, 0, 434, 466);
        contentPane_1.add(cadastroPanel);
        
        JPanel pnSexo = new JPanel();
        pnSexo.setLayout(null);
        pnSexo.setBorder(new TitledBorder(null, "Sexo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnSexo.setBounds(42, 204, 184, 156);
        cadastroPanel.add(pnSexo);
        
        JRadioButtonMenuItem rdbtnMasculino = new JRadioButtonMenuItem("Masculino");
        rdbtnMasculino.setBounds(10, 26, 133, 26);
        pnSexo.add(rdbtnMasculino);
        
        JRadioButtonMenuItem rdbtnFeminino = new JRadioButtonMenuItem("Feminino");
        rdbtnFeminino.setBounds(10, 63, 133, 26);
        pnSexo.add(rdbtnFeminino);
        
        JRadioButtonMenuItem rdbtnNaoInformar = new JRadioButtonMenuItem("Nao Informar");
        rdbtnNaoInformar.setBounds(10, 104, 133, 26);
        pnSexo.add(rdbtnNaoInformar);
        
        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(97, 83, 113, 20);
        cadastroPanel.add(textField);
        
        JLabel UsernameLabel = new JLabel("Usuário*");
        UsernameLabel.setBounds(42, 86, 64, 14);
        cadastroPanel.add(UsernameLabel);
        
        JLabel SenhaLabel = new JLabel("Senha*");
        SenhaLabel.setBounds(239, 86, 54, 14);
        cadastroPanel.add(SenhaLabel);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(284, 83, 92, 20);
        cadastroPanel.add(passwordField);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(97, 120, 279, 20);
        cadastroPanel.add(textField_1);
        
        JLabel nameLabel = new JLabel("Nome*");
        nameLabel.setBounds(42, 123, 36, 14);
        cadastroPanel.add(nameLabel);
        
        JFormattedTextField fTFDataNascimento = new JFormattedTextField((JFormattedTextField.AbstractFormatter) null);
        fTFDataNascimento.setColumns(10);
        fTFDataNascimento.setBounds(160, 163, 92, 20);
        cadastroPanel.add(fTFDataNascimento);
        
        JLabel LabelDataNascimento = new JLabel("Data Nascimento*");
        LabelDataNascimento.setBounds(42, 166, 108, 14);
        cadastroPanel.add(LabelDataNascimento);
        
        JPanel pnTipoUsuario = new JPanel();
        pnTipoUsuario.setLayout(null);
        pnTipoUsuario.setBorder(new TitledBorder(null, "Tipo Usuário*", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnTipoUsuario.setBounds(236, 204, 140, 101);
        cadastroPanel.add(pnTipoUsuario);
        
        JRadioButtonMenuItem rdbtnUsuario = new JRadioButtonMenuItem("Usuário");
        rdbtnUsuario.setBounds(10, 26, 133, 26);
        pnTipoUsuario.add(rdbtnUsuario);
        
        JRadioButtonMenuItem rdbtnAdmin = new JRadioButtonMenuItem("Admin");
        rdbtnAdmin.setBounds(10, 63, 133, 26);
        pnTipoUsuario.add(rdbtnAdmin);
        
        JButton btnCadastrar = new JButton("Atualizar");
        btnCadastrar.setBounds(321, 432, 103, 23);
        cadastroPanel.add(btnCadastrar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(10, 432, 89, 23);
        cadastroPanel.add(btnCancelar);
        
        JLabel lblLabelCentral = new JLabel("Atualização de usuário");
        lblLabelCentral.setHorizontalAlignment(SwingConstants.CENTER);
        lblLabelCentral.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblLabelCentral.setBounds(42, 32, 334, 34);
        cadastroPanel.add(lblLabelCentral);
    }

}
