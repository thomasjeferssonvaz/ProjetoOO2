package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaction;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.AnaliticaFinanceiraService;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DespesasWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JFormattedTextField txtData;
    private JComboBox cbConta;
    private JComboBox cbAnalitica;
    private JTextField txtValor;
    private JTextField txtDescricao;
    private JButton btnCancelar;
    private JButton btnConfirmar;

    private MaskFormatter mascaraData;
    private ContaService contaService;
    private AnaliticaFinanceiraService analiticaFinanceiraService;
    private Usuario userLogado;
    private String tipo;

    /**
     * Launch the application.
     */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DespesasWindow frame = new DespesasWindow();
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

    private void criarMascaraData() {
        try {

            this.mascaraData = new MaskFormatter("##/##/####");
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Preencha a data com formato 'DD/MM/YYYY'", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void popularComponentes() {

        GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this, "Buscando Contas");
        LancamentoPopulationContaWorker popularConta = new LancamentoPopulationContaWorker<>(this, contaService, userLogado, genericLoadingDialog, cbConta);
        popularConta.execute();
        genericLoadingDialog.setVisible(true);

        GenericLoadingDialog genericLoadingDialog1 = new GenericLoadingDialog(this, "Buscando Analiticas");
        LancamentoPopulationAnaliticaWorker popularAnalitica = new LancamentoPopulationAnaliticaWorker<>(this, analiticaFinanceiraService, userLogado, genericLoadingDialog1, cbAnalitica, tipo);
        popularAnalitica.execute();
        genericLoadingDialog1.setVisible(true);

    }

    private void limparCampos() {
        txtData.setText("");
        txtValor.setText("");
        txtDescricao.setText("");
        txtData.requestFocus();

    }

    private boolean verificarCampos() {
        if (txtData.getText().equals("") || txtValor.getText().equals("")) {
            return false;
        }
        return true;
    }

    public void registrarDespesa() {

        if (!verificarCampos()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatorios");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Transaction transaction = new Transaction();
        try {
            transaction.setDataTransacao(new java.sql.Date(sdf.parse(this.txtData.getText()).getTime()));
            transaction.setNumero_conta(Integer.parseInt(this.cbConta.getSelectedItem().toString()));
            transaction.setAnaliticaFinanceira(this.cbAnalitica.getSelectedItem().toString());
            transaction.setValor(Double.parseDouble(this.txtValor.getText()));
            transaction.setDescricao(this.txtDescricao.getText());

            transaction.setId_usuario(this.userLogado.getId());
            transaction.setTipo("Despesa");

            GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this, "Cadastrando Despesa");
            RegistroTransactionWorker registroTransactionWorker = new RegistroTransactionWorker(this, transaction, genericLoadingDialog);
            registroTransactionWorker.execute();
            genericLoadingDialog.setVisible(true);


        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Preencha a data com formato 'DD/MM/YYYY'","Erro",JOptionPane.WARNING_MESSAGE);
        }

    }

    public DespesasWindow(Usuario userLogado) {
        this.tipo = "Despesa";
        this.contaService = new ContaService();
        this.analiticaFinanceiraService = new AnaliticaFinanceiraService();
        this.userLogado = userLogado;
        this.criarMascaraData();
        this.initComponents();
        setVisible(true);
        this.popularComponentes();
    }

    public void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 481, 422);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lbCadastrarDespesaTitle = new JLabel("Registrar Despesa");
        lbCadastrarDespesaTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbCadastrarDespesaTitle.setBounds(155, 10, 133, 15);
        contentPane.add(lbCadastrarDespesaTitle);

        JLabel lbData = new JLabel("Data");
        lbData.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbData.setBounds(117, 47, 66, 12);
        contentPane.add(lbData);

        JLabel lbConta = new JLabel("Conta");
        lbConta.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbConta.setBounds(117, 95, 66, 12);
        contentPane.add(lbConta);

        JLabel lbAnalitica = new JLabel("Analitica");
        lbAnalitica.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbAnalitica.setBounds(117, 143, 66, 12);
        contentPane.add(lbAnalitica);

        JLabel lbValor = new JLabel("Valor");
        lbValor.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbValor.setBounds(117, 191, 66, 12);
        contentPane.add(lbValor);

        JLabel lbDescricao = new JLabel("Descrição");
        lbDescricao.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbDescricao.setBounds(117, 233, 66, 12);
        contentPane.add(lbDescricao);

        txtData = new JFormattedTextField(mascaraData);
        txtData.setBounds(165, 43, 123, 24);
        contentPane.add(txtData);

        cbConta = new JComboBox();
        cbConta.setBounds(160, 90, 128, 24);
        contentPane.add(cbConta);

        cbAnalitica = new JComboBox();
        cbAnalitica.setBounds(172, 136, 117, 24);
        contentPane.add(cbAnalitica);

        txtValor = new JTextField();
        txtValor.setBounds(155, 187, 133, 24);
        contentPane.add(txtValor);
        txtValor.setColumns(10);
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();

                // permite números
                if (Character.isDigit(c)) {

                    // verifica limite de 2 casas decimais
                    String texto = txtValor.getText();
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
                    if (txtValor.getText().contains(".")) {
                        e.consume();
                    }
                    return;
                }
                // qualquer outra coisa nao permite
                e.consume();
            }
        });

        txtDescricao = new JTextField();
        txtDescricao.setBounds(117, 246, 227, 36);
        contentPane.add(txtDescricao);
        txtDescricao.setColumns(10);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarDespesa();
                limparCampos();
            }
        });
        btnConfirmar.setBounds(247, 310, 133, 42);
        contentPane.add(btnConfirmar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancelar.setBounds(86, 310, 133, 42);
        contentPane.add(btnCancelar);

    }
}
