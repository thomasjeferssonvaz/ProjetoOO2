package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.conta.CadastroContaWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.conta.EditarContasSelecaoWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.transacao.SelectTipoLancamento;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private Usuario userLogado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginWindow loginWindow = new LoginWindow();
                    loginWindow.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
	}

    public Usuario getUserLogado() {
        return this.userLogado;
    }


    public MainWindow(Usuario userLogado) {
        this.userLogado = userLogado;
        this.initComponent();
	}

    public void initComponent() {
        setTitle("Main");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 434, 261);
        contentPane.add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu userMenu = new JMenu("Usuário");
        menuBar.add(userMenu);

        JMenuItem cadastrarUsuarioMenuItem = new JMenuItem("Cadastrar usuário");
        if(userLogado.getUsuarioTipo().equals("Usuário")){
            cadastrarUsuarioMenuItem.setEnabled(false);
            cadastrarUsuarioMenuItem.setToolTipText("Disponível somente para Administradores do sistema");
        }
        cadastrarUsuarioMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                CadastroUsuarioWindow cadastroUsuarioWindow = new CadastroUsuarioWindow();
                cadastroUsuarioWindow.setVisible(true);
        	}
        });
        userMenu.add(cadastrarUsuarioMenuItem);

        JMenuItem mntmEditarUsuario = new JMenuItem("Editar usuário");
        mntmEditarUsuario.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                EditarUsuarioWindow editarUsuarioWindow = new EditarUsuarioWindow(getUserLogado());
                editarUsuarioWindow.setVisible(true);
        	}
        });
        userMenu.add(mntmEditarUsuario);

        JMenuItem mntmAlterarSenhaUsuario = new JMenuItem("Alterar senha");
        userMenu.add(mntmAlterarSenhaUsuario);

        mntmAlterarSenhaUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlterarSenhaUsuarioWindow alterarSenhaUsuarioWindow = new AlterarSenhaUsuarioWindow(getUserLogado());
                alterarSenhaUsuarioWindow.setVisible(true);

            }
        });

        JMenuItem mntmLogout = new JMenuItem("Sair");
        mntmLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginWindow loginWindow = new LoginWindow();
                loginWindow.setVisible(true);
            }
        });
        userMenu.add(mntmLogout);


        JMenu contaMenu = new JMenu("Conta");
        menuBar.add(contaMenu);

        JMenuItem cadastrarContaMenuItem = new JMenuItem("Cadastrar Conta");
        cadastrarContaMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		CadastroContaWindow cadastroContaWindow = new CadastroContaWindow(getUserLogado());
        		cadastroContaWindow.setVisible(true);
        	}
        });
        contaMenu.add(cadastrarContaMenuItem);
        
        JMenuItem EditarContaMenuItem = new JMenuItem("Editar Conta");
        EditarContaMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        		EditarContasSelecaoWindow editarContasSelecaoWindow = new EditarContasSelecaoWindow(getUserLogado());
        		//editarContaWindow.setVisible(true);
        	}
        });
        contaMenu.add(EditarContaMenuItem);

        JMenu despesaMenu = new JMenu("Receitas/Despesas");
        menuBar.add(despesaMenu);
        
        JMenuItem cadastrarDespesaMenuItem = new JMenuItem("Cadastrar Analitica");
        cadastrarDespesaMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                CadastroAnaliticaWindow cadastroAnaliticaWindow = new CadastroAnaliticaWindow(getUserLogado());
        	}
        });
        despesaMenu.add(cadastrarDespesaMenuItem);
        
        JMenu lancamentosMenu = new JMenu("Lançamentos");
        menuBar.add(lancamentosMenu);
        
        JMenuItem novoLancamentoMenuItem = new JMenuItem("+ Novo Lançamento");
        novoLancamentoMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                SelectTipoLancamento selectTipoLancamento = new SelectTipoLancamento(getUserLogado());
                selectTipoLancamento.setVisible(true);

        	}
        });
        lancamentosMenu.add(novoLancamentoMenuItem);

    }
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
