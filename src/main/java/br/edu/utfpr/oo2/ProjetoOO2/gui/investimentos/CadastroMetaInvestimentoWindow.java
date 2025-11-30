package br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Investimento;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos.workers.CadastroInvestimentoWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastroMetaInvestimentoWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtAporte;
    private JComboBox cbTipo;
    private JComboBox cbLocal;
    private JButton btnCadastrar;
    private JButton btnCancelar;
    private Usuario userLogado;

    /**
     * Launch the application.
     */
   /* public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InvestimentoWindow frame = new InvestimentoWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    /**
     * Create the frame.
     */
    private void popularCbTipo() {
        this.cbTipo.addItem("Longo Prazo");
        this.cbTipo.addItem("Despesas Ocasionais");
    }

    private void cadastrarMeta(){
        Investimento investimento = new Investimento();
        investimento.setNome(txtNome.getText());
        investimento.setTipo(cbTipo.getSelectedItem().toString().trim());
        investimento.setAporte(Double.parseDouble(txtAporte.getText()));
        investimento.setLocal(cbLocal.getSelectedItem().toString());
        investimento.setIdUsuario(this.userLogado.getId());

        GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this,"Cadastrando Meta");
        CadastroInvestimentoWorker cadastroInvestimentoWorker = new CadastroInvestimentoWorker(this,genericLoadingDialog,investimento);
        cadastroInvestimentoWorker.execute();
        genericLoadingDialog.setVisible(true);

    }


    public CadastroMetaInvestimentoWindow(Usuario userLogado) {
        this.userLogado = userLogado;
        this.initComponent();
        setVisible(true);
        this.popularCbTipo();
    }


    public void initComponent() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lbInvestimentoTitle = new JLabel("Investimentos");
        lbInvestimentoTitle.setBounds(154, 10, 130, 23);
        lbInvestimentoTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
        contentPane.add(lbInvestimentoTitle);

        JLabel lbNome = new JLabel("Nome Investimento");
        lbNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbNome.setBounds(79, 55, 130, 12);
        contentPane.add(lbNome);

        JLabel lbTipoInvestimento = new JLabel("Tipo ");
        lbTipoInvestimento.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbTipoInvestimento.setBounds(79, 90, 41, 12);
        contentPane.add(lbTipoInvestimento);

        JLabel lbAporteMensal = new JLabel("Aporte Mensal");
        lbAporteMensal.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbAporteMensal.setBounds(79, 128, 130, 12);
        contentPane.add(lbAporteMensal);

        JLabel tipoInvestimento = new JLabel("Local de Investimento ");
        tipoInvestimento.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tipoInvestimento.setBounds(79, 170, 130, 12);
        contentPane.add(tipoInvestimento);

        txtNome = new JTextField();
        txtNome.setBounds(219, 53, 150, 23);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        txtAporte = new JTextField();
        txtAporte.setColumns(10);
        txtAporte.setBounds(168, 126, 103, 23);
        contentPane.add(txtAporte);
        txtAporte.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();

                // permite números
                if (Character.isDigit(c)) {

                    // verifica limite de 2 casas decimais
                    String texto = txtAporte.getText();
                    int indexPonto = texto.indexOf('.');

                    //conferir casas decimais
                    if (indexPonto != -1) {
                        int casasDepois = texto.length() - indexPonto - 1;

                        if (casasDepois >= 2) { //2casas decimais
                            e.consume();
                        }
                    }
                    return; // dígito permitido
                }

                // permite apenas 1 ponto
                if (c == '.') {
                    if (txtAporte.getText().contains(".")) {
                        e.consume();
                    }
                    return;
                }
                // qualquer outra coisa nao permite
                e.consume();
            }
        });

        cbTipo = new JComboBox();
        cbTipo.setBounds(130, 87, 116, 23);
        contentPane.add(cbTipo);
        cbTipo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {

                // pega o item selecionado no primeiro combo
                Object selecionado = cbTipo.getSelectedItem();

                // limpa o segundo combo
                cbLocal.removeAllItems();

                // altera o conteúdo do segundo combo conforme a seleção
                if ("Longo Prazo".equals(selecionado)) {
                    cbLocal.addItem("Ações");
                    cbLocal.addItem("Renda Fixa");
                    cbLocal.addItem("Fundos Imobiliarios");
                    cbLocal.addItem("Cripto");
                    cbLocal.addItem("Tesouro Direto");

                } else if ("Despesas Ocasionais".equals(selecionado)) {
                    cbLocal.addItem("Iptu");
                    cbLocal.addItem("Ipva");
                    cbLocal.addItem("Seguros");
                    cbLocal.addItem("Medico");
                    cbLocal.addItem("Viagem");
                    cbLocal.addItem("Reforma");

                } else {

                }
            }
        });

        cbLocal = new JComboBox();
        cbLocal.setBounds(219, 167, 122, 23);
        contentPane.add(cbLocal);

        btnCadastrar = new JButton("Cadastrar meta");
        btnCadastrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                cadastrarMeta();
        	}
        });
        btnCadastrar.setBounds(236, 215, 133, 38);
        contentPane.add(btnCadastrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                dispose();
        	}
        });
        btnCancelar.setBounds(79, 215, 130, 38);
        contentPane.add(btnCancelar);

    }
}
