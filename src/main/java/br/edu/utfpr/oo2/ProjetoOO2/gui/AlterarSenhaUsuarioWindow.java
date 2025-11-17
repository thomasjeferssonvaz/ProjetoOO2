package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.usuarioWorkers.AlterarSenhaUsuarioStartWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.usuarioWorkers.AlterarSenhaUsuarioWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.usuarioWorkers.CadastroTaskWorker;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class AlterarSenhaUsuarioWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JPanel alterarSenhaPanel;
	private JPasswordField pssFRedigiteSuaSenha;
	private JPasswordField pssFNovaSenha;
	private JPasswordField pssFSenhaAntiga;
    private Usuario userLogado;
    private UsuarioService usuarioService;
    private JComboBox cmBoxUsuario = new JComboBox();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AlterarSenhaUsuarioWindow frame = new AlterarSenhaUsuarioWindow();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

    private void setupAlterarSenhaStartAction(JPanel alterarSenhaPanel){
        GenericLoadingDialog loadingDialog = new GenericLoadingDialog(AlterarSenhaUsuarioWindow.this, "Procurando usuários");

        AlterarSenhaUsuarioStartWorker worker = new AlterarSenhaUsuarioStartWorker(userLogado, usuarioService, this, loadingDialog);

        worker.execute();
        loadingDialog.setVisible(true);
        for (Usuario usuarioWorker : worker.getListaUsuario()) {
            cmBoxUsuario.addItem(usuarioWorker.getUsername());
        }

    }

    private void setupAlterarSenhaAction(JPanel alterarSenhaPanel){
        String novaSenha = new String(pssFNovaSenha.getPassword());
        String redigiteSuaSenha = new String(pssFRedigiteSuaSenha.getPassword());
        String senhaAntiga = new String(pssFSenhaAntiga.getPassword());
        String cmBoxUsername = cmBoxUsuario.getSelectedItem().toString();
        if (novaSenha.equals(redigiteSuaSenha)){
            if (!novaSenha.equals(senhaAntiga)) {
                GenericLoadingDialog loadingDialog = new GenericLoadingDialog(AlterarSenhaUsuarioWindow.this, "Alterando senha");


                AlterarSenhaUsuarioWorker worker = new AlterarSenhaUsuarioWorker(cmBoxUsername, senhaAntiga, novaSenha, usuarioService, this, loadingDialog);

                worker.execute();
                loadingDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(alterarSenhaPanel, "A nova senha é igual a antiga, tente novamente com uma senha diferente", "Erro ao alterar senha", JOptionPane.ERROR_MESSAGE);

            }

        } else {
            JOptionPane.showMessageDialog(alterarSenhaPanel, "Senhas não estão iguais", "Erro ao alterar senha", JOptionPane.ERROR_MESSAGE);
        }

    }

	public AlterarSenhaUsuarioWindow(Usuario userLogado) {

        this.userLogado = userLogado;
        this.usuarioService = new UsuarioService();
        setupAlterarSenhaStartAction(alterarSenhaPanel);
        this.initComponents();

	}

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        alterarSenhaPanel = new JPanel();
        alterarSenhaPanel.setBounds(0, 0, 434, 261);
        contentPane.add(alterarSenhaPanel);
        alterarSenhaPanel.setLayout(null);


        cmBoxUsuario.setBounds(146, 25, 119, 22);
        alterarSenhaPanel.add(cmBoxUsuario);

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setBounds(68, 29, 68, 14);
        alterarSenhaPanel.add(lblUsuario);

        JLabel lblSenhaAntiga = new JLabel("Senha Antiga:");
        lblSenhaAntiga.setBounds(68, 76, 78, 14);
        alterarSenhaPanel.add(lblSenhaAntiga);

        JLabel lblNovaSenha = new JLabel("Nova Senha:");
        lblNovaSenha.setBounds(68, 114, 78, 14);
        alterarSenhaPanel.add(lblNovaSenha);

        JLabel lblNewLabel = new JLabel("Redigite sua nova senha:");
        lblNewLabel.setBounds(68, 147, 154, 14);
        alterarSenhaPanel.add(lblNewLabel);

        pssFRedigiteSuaSenha = new JPasswordField();
        pssFRedigiteSuaSenha.setBounds(232, 144, 78, 20);
        alterarSenhaPanel.add(pssFRedigiteSuaSenha);

        pssFNovaSenha = new JPasswordField();
        pssFNovaSenha.setBounds(170, 111, 95, 20);
        alterarSenhaPanel.add(pssFNovaSenha);

        pssFSenhaAntiga = new JPasswordField();
        pssFSenhaAntiga.setBounds(170, 73, 95, 20);
        alterarSenhaPanel.add(pssFSenhaAntiga);
        
        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                setupAlterarSenhaAction(alterarSenhaPanel);
        	}
        });
        btnAlterar.setBounds(335, 227, 89, 23);
        alterarSenhaPanel.add(btnAlterar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(10, 227, 89, 23);
        alterarSenhaPanel.add(btnCancelar);
    }
}
