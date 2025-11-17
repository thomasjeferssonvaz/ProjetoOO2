package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.usuarioWorkers.CadastroTaskWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import java.awt.EventQueue;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class CadastroUsuarioWindow extends JFrame {

	private static final long serialVersionUID = 1L;
    private JTextField txtfUsername;
    private JPasswordField passwordField;
    private MaskFormatter mascaraData;
    private JTextField txtfName;
    private JFormattedTextField fTFDataNascimento;
    private JRadioButtonMenuItem rdbtnMasculino;
    private JRadioButtonMenuItem rdbtnFeminino;
    private JRadioButtonMenuItem rdbtnNaoInformar;
    private final ButtonGroup buttonGroupSexo = new ButtonGroup();
    private final ButtonGroup buttonGroupUsuario = new ButtonGroup();
    private JPanel cadastroPanel;
    private JRadioButtonMenuItem rdbtnUsuario;
    private JRadioButtonMenuItem rdbtnAdmin;
    private UsuarioService usuarioService;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroUsuarioWindow frame = new CadastroUsuarioWindow();
					frame.setVisible(true);
				} catch (Exception e) {
                    System.out.println(e.getMessage());
				}
			}
		});
	}

    private void criarMascaraData() {
        try {
            this.mascaraData = new MaskFormatter("##/##/####");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public CadastroUsuarioWindow() {
        this.criarMascaraData();
        this.initComponent();

        this.usuarioService = new UsuarioService();

	}

//    private void cadastrarAluno() {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            Usuario usuario = new Usuario();
//            usuario.setUsername(txtfUsername.getText());
//            usuario.setSenha(new String(passwordField.getPassword()));
//            usuario.setDataNascimento(new java.sql.Date(sdf.parse(this.fTFDataNascimento.getText()).getTime()));
//            usuario.setNome(txtfName.getText());
//            usuario.setSexo(verificarSelecaoRadioButtonSexo());
//            usuario.setUsuarioTipo(verificarSelecaoRadioButtonUsuario());
//            if (usuario.getUsername() != null &&
//                usuario.getSenha() != null &&
//                usuario.getDataNascimento() != null &&
//                usuario.getNome() != null) {
//                try {
//                    int cadastro = usuarioService.cadastrarUsuario(usuario);
//                    if (cadastro > 0) {
//                        JOptionPane.showMessageDialog(cadastroPanel, "Usuário cadastrado com sucesso!!", "Erro ao cadastrar", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                } catch (SQLException | IOException e) {
//                    if (e.getMessage().startsWith("Duplicate")) {
//                        JOptionPane.showMessageDialog(cadastroPanel, "Usuário já existente, tente novamente com outro nome de usuário!", "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
//                    } else {
//                        JOptionPane.showMessageDialog(cadastroPanel, "Erro: " + e.getMessage(), "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            } else {
//                JOptionPane.showMessageDialog(cadastroPanel, "Erro: Preencha todos os campos.", "Erro de cadastro", JOptionPane.ERROR_MESSAGE);
//                System.out.println(usuario);
//            }
//
//        } catch (ParseException e) {
//            System.out.println("Erro: " + e.getMessage());
//            if (e.getMessage().startsWith("Unparseable date")) {
//                JOptionPane.showMessageDialog(cadastroPanel, "Erro: Data de nascimento fora do padrão.", "Erro de cadastro", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }

    private String verificarSelecaoRadioButtonSexo() {
        if(this.rdbtnMasculino.isSelected()) {
            return this.rdbtnMasculino.getText();
        } else if (this.rdbtnFeminino.isSelected()) {
            return this.rdbtnFeminino.getText();
        } else {
            return this.rdbtnNaoInformar.getText();
        }
    }

    private String verificarSelecaoRadioButtonUsuario() {
        if(this.rdbtnUsuario.isSelected()) {
            return this.rdbtnUsuario.getText();
        } else if (this.rdbtnAdmin.isSelected()) {
            return this.rdbtnAdmin.getText();
        } else {
            JOptionPane.showMessageDialog(cadastroPanel, "Erro: Selecione o tipo do usuário.", "Erro de cadastro", JOptionPane.ERROR_MESSAGE);
            return this.rdbtnFeminino.getText();
        }
    }

    private void setupCadastroAction(JPanel cadastroPanel) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Usuario usuario = new Usuario();
            usuario.setUsername(txtfUsername.getText());
            usuario.setSenha(new String(passwordField.getPassword()));
            usuario.setDataNascimento(new java.sql.Date(sdf.parse(this.fTFDataNascimento.getText()).getTime()));
            usuario.setNome(txtfName.getText());
            usuario.setSexo(verificarSelecaoRadioButtonSexo());
            usuario.setUsuarioTipo(verificarSelecaoRadioButtonUsuario());

            GenericLoadingDialog loadingDialog = new GenericLoadingDialog(CadastroUsuarioWindow.this, "Cadastrando usuário");

            CadastroTaskWorker worker = new CadastroTaskWorker(usuario, usuarioService, this, loadingDialog);

            worker.execute();
            loadingDialog.setVisible(true);

        } catch (ParseException e) {
            System.out.println("Erro: " + e.getMessage());
            if (e.getMessage().startsWith("Unparseable date")) {
                JOptionPane.showMessageDialog(cadastroPanel, "Erro: Data de nascimento fora do padrão.", "Erro de cadastro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private void initComponent() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 505);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        cadastroPanel = new JPanel();
        cadastroPanel.setBounds(0, 0, 434, 466);
        contentPane.add(cadastroPanel);
        cadastroPanel.setLayout(null);

        JPanel pnSexo = new JPanel();
        pnSexo.setLayout(null);
        pnSexo.setBorder(new TitledBorder(null, "Sexo*", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnSexo.setBounds(42, 204, 184, 156);
        cadastroPanel.add(pnSexo);
        
        rdbtnMasculino = new JRadioButtonMenuItem("Masculino");
        buttonGroupSexo.add(rdbtnMasculino);
        rdbtnMasculino.setBounds(10, 26, 133, 26);
        pnSexo.add(rdbtnMasculino);
        
        rdbtnFeminino = new JRadioButtonMenuItem("Feminino");
        rdbtnFeminino.setBounds(10, 63, 133, 26);
        buttonGroupSexo.add(rdbtnFeminino);
        pnSexo.add(rdbtnFeminino);
        
        rdbtnNaoInformar = new JRadioButtonMenuItem("Nao Informar");
        rdbtnNaoInformar.setBounds(10, 104, 133, 26);
        buttonGroupSexo.add(rdbtnNaoInformar);
        pnSexo.add(rdbtnNaoInformar);

        txtfUsername = new JTextField();
        txtfUsername.setBounds(97, 83, 113, 20);
        cadastroPanel.add(txtfUsername);
        txtfUsername.setColumns(10);

        JLabel UsernameLabel = new JLabel("Usuário*");
        UsernameLabel.setBounds(42, 86, 64, 14);
        cadastroPanel.add(UsernameLabel);

        JLabel SenhaLabel = new JLabel("Senha*");
        SenhaLabel.setBounds(239, 86, 54, 14);
        cadastroPanel.add(SenhaLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(284, 83, 92, 20);
        cadastroPanel.add(passwordField);

        txtfName = new JTextField();
        txtfName.setBounds(97, 120, 279, 20);
        cadastroPanel.add(txtfName);
        txtfName.setColumns(10);

        JLabel nameLabel = new JLabel("Nome*");
        nameLabel.setBounds(42, 123, 36, 14);
        cadastroPanel.add(nameLabel);

        fTFDataNascimento = new JFormattedTextField(this.mascaraData);
        fTFDataNascimento.setBounds(160, 163, 92, 20);
        cadastroPanel.add(fTFDataNascimento);
        fTFDataNascimento.setColumns(10);

        JLabel LabelDataNascimento = new JLabel("Data Nascimento*");
        LabelDataNascimento.setBounds(42, 166, 108, 14);
        cadastroPanel.add(LabelDataNascimento);
        
        JPanel pnTipoUsuario = new JPanel();
        pnTipoUsuario.setLayout(null);
        pnTipoUsuario.setBorder(new TitledBorder(null, "Tipo Usuário*", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnTipoUsuario.setBounds(236, 204, 140, 101);
        cadastroPanel.add(pnTipoUsuario);
        
        rdbtnUsuario = new JRadioButtonMenuItem("Usuário");
        rdbtnUsuario.setBounds(10, 26, 133, 26);
        buttonGroupUsuario.add(rdbtnUsuario);
        pnTipoUsuario.add(rdbtnUsuario);
        
        rdbtnAdmin = new JRadioButtonMenuItem("Admin");
        rdbtnAdmin.setBounds(10, 63, 133, 26);
        buttonGroupUsuario.add(rdbtnAdmin);
        pnTipoUsuario.add(rdbtnAdmin);
        
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                setupCadastroAction(cadastroPanel);
        	}
        });
        btnCadastrar.setBounds(321, 432, 103, 23);
        cadastroPanel.add(btnCadastrar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                dispose();
        	}
        });
        btnCancelar.setBounds(10, 432, 89, 23);
        cadastroPanel.add(btnCancelar);

        JLabel lblLabelCentral = new JLabel("Cadastro de usuário");
        lblLabelCentral.setHorizontalAlignment(SwingConstants.CENTER);
        lblLabelCentral.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblLabelCentral.setBounds(42, 32, 334, 34);
        cadastroPanel.add(lblLabelCentral);
    }
}
