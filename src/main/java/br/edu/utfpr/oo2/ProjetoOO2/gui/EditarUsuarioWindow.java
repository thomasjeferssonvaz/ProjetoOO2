package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.usuarioWorkers.EditarStatusUsuarioWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.usuarioWorkers.EditarUsuarioWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.usuarioWorkers.LoadSelectedUserDataWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.usuarioWorkers.SearchUsersStartWorker;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditarUsuarioWindow extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JPanel editarUsuarioPanel;
    private JComboBox cmBoxUsuario = new JComboBox();
    private MaskFormatter mascaraData;
	private JTextField txtfName;
    private final ButtonGroup buttonGroupSexo = new ButtonGroup();
    private final ButtonGroup buttonGroupUsuario = new ButtonGroup();
    private Usuario userLogado;
    private UsuarioService usuarioService;
    private JFormattedTextField fTFDataNascimento;
    private JRadioButtonMenuItem rdbtnMasculino;
    private JRadioButtonMenuItem rdbtnFeminino;
    private JRadioButtonMenuItem rdbtnNaoInformar;
    private JRadioButtonMenuItem rdbtnUsuario;
    private JRadioButtonMenuItem rdbtnAdmin;
    private JButton statusUsuario;
    private Usuario usuarioSelecionado;

	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					EditarUsuarioWindow frame = new EditarUsuarioWindow();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

    private void setupSearchUsersStartWorker(JFrame editarUsuarioWindow){
        GenericLoadingDialog loadingDialog = new GenericLoadingDialog(editarUsuarioWindow, "Procurando usuários");

        SearchUsersStartWorker worker = new SearchUsersStartWorker(userLogado, usuarioService, this, loadingDialog);

        worker.execute();
        loadingDialog.setVisible(true);
        for (Usuario usuarioWorker : worker.getListaUsuario()) {
            cmBoxUsuario.addItem(usuarioWorker.getUsername());
        }
    }

    private void setupLoadSelectedUserDataWorker(JFrame editarUsuarioWindow){
        String cmBoxUsername = cmBoxUsuario.getSelectedItem().toString();
        GenericLoadingDialog loadingDialog = new GenericLoadingDialog(editarUsuarioWindow, "Carregando dados do usuário selecionado");

        LoadSelectedUserDataWorker worker = new LoadSelectedUserDataWorker(cmBoxUsername, usuarioService, this, loadingDialog);

        worker.execute();
        loadingDialog.setVisible(true);

        usuarioSelecionado = new Usuario(worker.getUsuario());

        txtfName.setText(usuarioSelecionado.getNome());
        String textAtualizarStatusUsuario = usuarioSelecionado.getStatus();
        if (textAtualizarStatusUsuario.equals("ativo")) {
            statusUsuario.setText("Desativar usuário");
            statusUsuario.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    setupEditarStatusUsuarioWorker(editarUsuarioPanel, "Desativando");
                    setupLoadSelectedUserDataWorker(EditarUsuarioWindow.this);

                }
            });
        } else {
            statusUsuario.setText("Ativar usuário");
            statusUsuario.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    setupEditarStatusUsuarioWorker(editarUsuarioPanel, "Ativando");
                    setupLoadSelectedUserDataWorker(EditarUsuarioWindow.this);
                }
            });
        }

        if (usuarioSelecionado.getDataNascimento() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataFormatada = sdf.format(usuarioSelecionado.getDataNascimento());
            fTFDataNascimento.setValue(dataFormatada);
        } else {
            this.fTFDataNascimento.setValue("");
        }



        switch (usuarioSelecionado.getSexo()){
            case "Masculino":
                rdbtnMasculino.setSelected(true);
                break;
            case "Feminino":
                rdbtnFeminino.setSelected(true);
                break;
            default:
                rdbtnNaoInformar.setSelected(true);
        }
        switch (usuarioSelecionado.getUsuarioTipo()){
            case "Admin":
                rdbtnAdmin.setSelected(true);
                break;
            default:
                rdbtnUsuario.setSelected(true);
        }
    }

    private void setupEditarUsuarioWorker(JPanel editarUsuarioPanel){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String cmBoxUsername = cmBoxUsuario.getSelectedItem().toString();
            Usuario usuario = new Usuario();
            usuario.setUsername(cmBoxUsername);
            usuario.setDataNascimento(new java.sql.Date(sdf.parse(this.fTFDataNascimento.getText()).getTime()));
            usuario.setNome(txtfName.getText());
            usuario.setSexo(verificarSelecaoRadioButtonSexo());
            usuario.setStatus(usuarioSelecionado.getStatus());
            if (userLogado.getUsuarioTipo().equals("Admin")){
                usuario.setUsuarioTipo(verificarSelecaoRadioButtonUsuario());
            } else {
                usuario.setUsuarioTipo(userLogado.getUsuarioTipo());
            }


            GenericLoadingDialog loadingDialog = new GenericLoadingDialog(EditarUsuarioWindow.this, "Editando usuário");


            EditarUsuarioWorker worker = new EditarUsuarioWorker(usuario, loadingDialog, usuarioService, this, cmBoxUsername, this.userLogado);

            worker.execute();
            loadingDialog.setVisible(true);
        } catch (ParseException e) {
            if (e.getMessage().startsWith("Unparseable date")) {
                JOptionPane.showMessageDialog(editarUsuarioPanel, "Erro: Data de nascimento fora do padrão.", "Erro de edição", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(editarUsuarioPanel, "Erro: " + e.getMessage(), "Erro grave", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setupEditarStatusUsuarioWorker(JPanel editarUsuarioPanel, String alteracaoStatus){
        String cmBoxUsername = cmBoxUsuario.getSelectedItem().toString();
        String textoLoading;
        String operacao;
        if(alteracaoStatus.equals("Ativando")){
            textoLoading = "Ativando";
            operacao = "Ativando";
        } else {
            textoLoading = "Desativando";
            operacao = "Desativando";
        }


        GenericLoadingDialog loadingDialog = new GenericLoadingDialog(EditarUsuarioWindow.this, textoLoading+ " usuário");


        EditarStatusUsuarioWorker worker = new EditarStatusUsuarioWorker(loadingDialog, usuarioService, this, cmBoxUsername, this.userLogado, operacao);

        worker.execute();
        loadingDialog.setVisible(true);
    }

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
            JOptionPane.showMessageDialog(editarUsuarioPanel, "Erro: Selecione o tipo do usuário.", "Erro de cadastro", JOptionPane.ERROR_MESSAGE);
            return this.rdbtnFeminino.getText();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        setupLoadSelectedUserDataWorker(EditarUsuarioWindow.this);
    }

    private void criarMascaraData() {
        try {
            this.mascaraData = new MaskFormatter("##/##/####");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

	public EditarUsuarioWindow(Usuario userLogado) {
        this.userLogado = userLogado;
        this.criarMascaraData();
		this.initContent();

        cmBoxUsuario.addItemListener(this);
        this.usuarioService = new UsuarioService();
        setupSearchUsersStartWorker(EditarUsuarioWindow.this);
	}

    private void initContent() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 505);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        editarUsuarioPanel = new JPanel();
        editarUsuarioPanel.setLayout(null);
        editarUsuarioPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        editarUsuarioPanel.setBounds(0, 0, 434, 466);
        contentPane.add(editarUsuarioPanel);

        JPanel pnSexo = new JPanel();
        pnSexo.setLayout(null);
        pnSexo.setBorder(new TitledBorder(null, "Sexo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnSexo.setBounds(42, 204, 184, 156);
        editarUsuarioPanel.add(pnSexo);
        
        rdbtnMasculino = new JRadioButtonMenuItem("Masculino");
        rdbtnMasculino.setBounds(10, 26, 133, 26);
        buttonGroupSexo.add(rdbtnMasculino);
        pnSexo.add(rdbtnMasculino);

        rdbtnFeminino = new JRadioButtonMenuItem("Feminino");
        rdbtnFeminino.setBounds(10, 63, 133, 26);
        buttonGroupSexo.add(rdbtnFeminino);
        pnSexo.add(rdbtnFeminino);
        
        rdbtnNaoInformar = new JRadioButtonMenuItem("Nao Informar");
        rdbtnNaoInformar.setBounds(10, 104, 133, 26);
        buttonGroupSexo.add(rdbtnNaoInformar);
        pnSexo.add(rdbtnNaoInformar);


        cmBoxUsuario.setBounds(97, 83, 113, 20);
        editarUsuarioPanel.add(cmBoxUsuario);
        
        JLabel usernameLabel = new JLabel("Usuário*");
        usernameLabel.setBounds(42, 86, 64, 14);
        usernameLabel.setLabelFor(cmBoxUsuario);
        editarUsuarioPanel.add(usernameLabel);

        txtfName = new JTextField();
        txtfName.setColumns(10);
        txtfName.setBounds(97, 120, 279, 20);
        editarUsuarioPanel.add(txtfName);
        
        JLabel nameLabel = new JLabel("Nome*");
        nameLabel.setBounds(42, 123, 50, 14);
        nameLabel.setLabelFor(txtfName);
        editarUsuarioPanel.add(nameLabel);
        
        fTFDataNascimento = new JFormattedTextField(this.mascaraData);
        fTFDataNascimento.setColumns(10);
        fTFDataNascimento.setBounds(160, 163, 92, 20);
        editarUsuarioPanel.add(fTFDataNascimento);
        
        JLabel LabelDataNascimento = new JLabel("Data Nascimento*");
        LabelDataNascimento.setBounds(42, 166, 108, 14);
        editarUsuarioPanel.add(LabelDataNascimento);
        
        JPanel pnTipoUsuario = new JPanel();
        pnTipoUsuario.setLayout(null);
        pnTipoUsuario.setBorder(new TitledBorder(null, "Tipo Usuário*", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnTipoUsuario.setBounds(236, 204, 140, 101);
        editarUsuarioPanel.add(pnTipoUsuario);
        
        rdbtnUsuario = new JRadioButtonMenuItem("Usuário");
        rdbtnUsuario.setBounds(10, 26, 133, 26);
        buttonGroupUsuario.add(rdbtnUsuario);
        pnTipoUsuario.add(rdbtnUsuario);
        
        rdbtnAdmin = new JRadioButtonMenuItem("Admin");
        rdbtnAdmin.setBounds(10, 63, 133, 26);
        buttonGroupUsuario.add(rdbtnAdmin);
        pnTipoUsuario.add(rdbtnAdmin);

        if(userLogado.getUsuarioTipo().equals("Usuário")){
            pnTipoUsuario.setEnabled(false);
            rdbtnUsuario.setEnabled(false);
            rdbtnAdmin.setEnabled(false);
            pnTipoUsuario.setToolTipText("Disponível somente para Administradores do sistema");
            rdbtnUsuario.setToolTipText("Disponível somente para Administradores do sistema");
            rdbtnAdmin.setToolTipText("Disponível somente para Administradores do sistema");
        }

        statusUsuario = new JButton();
        statusUsuario.setBounds(236, 320, 150, 26);
        editarUsuarioPanel.add(statusUsuario);


        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(321, 432, 103, 23);
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setupEditarUsuarioWorker(editarUsuarioPanel);
            }
        });
        editarUsuarioPanel.add(btnAtualizar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(10, 432, 89, 23);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        editarUsuarioPanel.add(btnCancelar);
        
        JLabel lblLabelCentral = new JLabel("Atualização de usuário");
        lblLabelCentral.setHorizontalAlignment(SwingConstants.CENTER);
        lblLabelCentral.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblLabelCentral.setBounds(42, 32, 334, 34);
        editarUsuarioPanel.add(lblLabelCentral);
    }

}
