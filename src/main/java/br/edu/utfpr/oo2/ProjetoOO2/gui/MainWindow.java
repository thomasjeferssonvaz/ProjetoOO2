package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.analiticas.CadastroAnaliticaWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.conta.CadastroContaWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.conta.EditarContasSelecaoWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos.CadastroMetaInvestimentoWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos.ExcluirMetaWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos.MinhasMetasWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.transacao.SelectTipoLancamento;
import br.edu.utfpr.oo2.ProjetoOO2.gui.usuario.AlterarSenhaUsuarioWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.usuario.CadastroUsuarioWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.usuario.EditarUsuarioWindow;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Usuario userLogado;
    private JTable tbExtrato;
    private JLabel lbSaldo;

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


    public void atualizarSaldo() {
        new MainWorker(this, lbSaldo, userLogado).execute();
    }

    public void buscarExtrato() {
        new ExtratoWorker(this, tbExtrato, userLogado).execute();
    }


    public MainWindow(Usuario userLogado) {
        this.userLogado = userLogado;
        this.initComponent();

        Timer timer = new Timer(3000, e -> {
            atualizarSaldo();
            buscarExtrato();

        });
        timer.start();
    }

    public void initComponent() {
        setTitle("Main");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 472, 305);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 438, 236);
        contentPane.add(panel);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_1.setBounds(10, 10, 418, 74);
        panel.add(panel_1);
        panel_1.setLayout(null);

        JLabel lbSaldoTotal = new JLabel("Saldo Total das Contas");
        lbSaldoTotal.setBounds(10, 9, 160, 16);
        panel_1.add(lbSaldoTotal);
        lbSaldoTotal.setFont(new Font("Tahoma", Font.PLAIN, 13));

        lbSaldo = new JLabel("calculando...");
        lbSaldo.setBounds(10, 35, 154, 21);
        panel_1.add(lbSaldo);
        lbSaldo.setFont(new Font("Tahoma", Font.PLAIN, 17));

        JLabel lbExtrato = new JLabel("Extrado das Contas");
        lbExtrato.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbExtrato.setBounds(10, 94, 132, 12);
        panel.add(lbExtrato);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 111, 418, 115);
        panel.add(scrollPane);

        tbExtrato = new JTable();
        scrollPane.setViewportView(tbExtrato);
        tbExtrato.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Data", "Conta", "Analitica", "Valor"
                }) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
                }


        );


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu userMenu = new JMenu("Usuário");
        menuBar.add(userMenu);

        JMenuItem cadastrarUsuarioMenuItem = new JMenuItem("Cadastrar usuário");
        if (userLogado.getUsuarioTipo().equals("Usuário")) {
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

        JMenu despesaMenu = new JMenu("Analiticas");
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

        JMenu PlanejamentoFinanceiroMenu = new JMenu("Investimentos");
        menuBar.add(PlanejamentoFinanceiroMenu);

        JMenuItem InvestimentosMenuItem = new JMenuItem("Cadastrar Meta");
        InvestimentosMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroMetaInvestimentoWindow investimentoWindow = new CadastroMetaInvestimentoWindow(getUserLogado());
                //investimentoWindow.setVisible(true);
            }
        });
        PlanejamentoFinanceiroMenu.add(InvestimentosMenuItem);

        JMenuItem listarMetasMenuItem = new JMenuItem("Ver Metas");
        PlanejamentoFinanceiroMenu.add(listarMetasMenuItem);
        
        JMenuItem excluirMetaMenuItem = new JMenuItem("Excluir Meta");
        excluirMetaMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ExcluirMetaWindow excluirMetaWindow = new ExcluirMetaWindow(getUserLogado());
        	}
        });
        PlanejamentoFinanceiroMenu.add(excluirMetaMenuItem);
        listarMetasMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MinhasMetasWindow minhasMetasWindow = new MinhasMetasWindow(getUserLogado());
            }
        });

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
