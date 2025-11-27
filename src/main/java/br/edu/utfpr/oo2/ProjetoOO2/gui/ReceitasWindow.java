package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaction;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.AnaliticaFinanceiraService;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;
import br.edu.utfpr.oo2.ProjetoOO2.service.TransactionService;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.Color;

public class ReceitasWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JFormattedTextField txtData;
    private JComboBox cbConta;
    private JComboBox cbAnalitica;
    private JTextField txtValor;
    private JTextField txtDescricao;
    private JButton btnCancelar;
    private JButton btnConfirmar;

    private Usuario userLogado;
    private MaskFormatter mascaraData;
    private String tipo;

    private ContaService contaService;
    private AnaliticaFinanceiraService analiticaFinanceiraService;
    private TransactionService transactionService;
    private JLabel lbObrigatoriedade_1;
    private JLabel lbObrigatoriedade_2;
    private JLabel lbObrigatoriedade_3;

    /**
     * Launch the application.
     */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReceitasWindow frame = new ReceitasWindow();
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
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Preencha a data com formato 'DD/MM/YYYY'","Erro",JOptionPane.WARNING_MESSAGE);
        }
    }

    private void popularComponentes() {

        GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(ReceitasWindow.this, "Buscando Contas");
        LancamentoPopulationContaWorker popularConta = new LancamentoPopulationContaWorker<>(this, contaService ,userLogado, genericLoadingDialog, cbConta);
        popularConta.execute();
        genericLoadingDialog.setVisible(true);

        GenericLoadingDialog genericLoadingDialog1 = new GenericLoadingDialog(ReceitasWindow.this, "Buscando Analiticas");
        LancamentoPopulationAnaliticaWorker popularAnalitica = new LancamentoPopulationAnaliticaWorker<>(this, analiticaFinanceiraService, userLogado, genericLoadingDialog1, cbAnalitica, tipo);
        popularAnalitica.execute();
        genericLoadingDialog1.setVisible(true);

    }

    private boolean verificarCampos() {
        if (txtData.getText().equals("") || txtValor.getText().equals("")) {
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtData.setText("");
        txtValor.setText("");
        txtDescricao.setText("");
        txtData.requestFocus();

    }

    public void registrarReceita() {

        if (!verificarCampos()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatorios");
            return;
        }
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Transaction transaciton = new Transaction();
            transaciton.setDataTransacao(new java.sql.Date(sdf.parse(this.txtData.getText()).getTime()));
            transaciton.setNumero_conta(Integer.parseInt(this.cbConta.getSelectedItem().toString()));
            transaciton.setTipo("Receita");
            transaciton.setAnaliticaFinanceira(cbAnalitica.getSelectedItem().toString());
            transaciton.setValor(Double.parseDouble(this.txtValor.getText()));
            transaciton.setDescricao(this.txtDescricao.getText());
            transaciton.setId_usuario(userLogado.getId());

            GenericLoadingDialog gldCadastrando = new GenericLoadingDialog(ReceitasWindow.this, "Registrando Receita");

            RegistroTransactionWorker registroTransactionWorker = new RegistroTransactionWorker(this,transaciton,gldCadastrando);
            registroTransactionWorker.execute();
            gldCadastrando.setVisible(true);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao formatar Data", "Erro Interno", JOptionPane.ERROR_MESSAGE);
        }

    }


    public ReceitasWindow(Usuario userLogado) {
        tipo = "Receita";
        this.analiticaFinanceiraService = new AnaliticaFinanceiraService();
        this.transactionService = new TransactionService();
        this.contaService = new ContaService();
        this.userLogado = userLogado;
        this.criarMascaraData();
        this.initComponents();
        setVisible(true);
        popularComponentes();

    }


    public void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 481, 422);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lbCadastrarReceitaTitle = new JLabel("Registrar Receita");
        lbCadastrarReceitaTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbCadastrarReceitaTitle.setBounds(155, 10, 133, 15);
        contentPane.add(lbCadastrarReceitaTitle);

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
                registrarReceita();
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
        
        JLabel lbObrigatoriedade = new JLabel("*");
        lbObrigatoriedade.setForeground(new Color(255, 0, 0));
        lbObrigatoriedade.setBounds(109, 48, 13, 12);
        contentPane.add(lbObrigatoriedade);
        
        lbObrigatoriedade_1 = new JLabel("*");
        lbObrigatoriedade_1.setForeground(Color.RED);
        lbObrigatoriedade_1.setBounds(109, 143, 13, 12);
        contentPane.add(lbObrigatoriedade_1);
        
        lbObrigatoriedade_2 = new JLabel("*");
        lbObrigatoriedade_2.setForeground(Color.RED);
        lbObrigatoriedade_2.setBounds(109, 95, 13, 12);
        contentPane.add(lbObrigatoriedade_2);
        
        lbObrigatoriedade_3 = new JLabel("*");
        lbObrigatoriedade_3.setForeground(Color.RED);
        lbObrigatoriedade_3.setBounds(109, 191, 13, 12);
        contentPane.add(lbObrigatoriedade_3);

    }
}
