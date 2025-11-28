package br.edu.utfpr.oo2.ProjetoOO2.gui;



import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaction;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class TransferenciaWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtValor;
    private JComboBox cbContaOrigem;
    private JComboBox cbContaDestino;
    private JLabel lbValor;
    private JComboBox cbTipo;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    private MaskFormatter mascaraData;
    private JFormattedTextField txtData;
    private Usuario userLogado;

    /**
     * Launch the application.
     *//*
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TransferenciaWindow frame = new TransferenciaWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
    private void criarMascaraData() {
        try {
            this.mascaraData = new MaskFormatter("##/##/####");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Preencha a data no formato 'DD/MM/YYYY'");
        }
    }

    private void setarAnaliticaTransferencia() {
        this.cbTipo.addItem("Transferencia");
        this.cbTipo.setSelectedItem("Transferencia");
        this.cbTipo.setEnabled(false);
    }

    private boolean verificarCampos() {

        if (this.txtValor.getText().trim().isEmpty()) {
            return false;
            }

        return true;
    }

    private void cadastrarTransferencia() {

        if (!verificarCampos()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os Campos","Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Transaction transactionSaida = new Transaction();
            transactionSaida.setDataTransacao(new java.sql.Date(sdf.parse(this.txtData.getText()).getTime()));
            transactionSaida.setNumero_conta(Integer.parseInt(this.cbContaOrigem.getSelectedItem().toString()));
            transactionSaida.setValor(Double.parseDouble(this.txtValor.getText()));
            transactionSaida.setTipo("Despesa");
            transactionSaida.setId_usuario(this.userLogado.getId());
            transactionSaida.setAnaliticaFinanceira("Transferencia Saida");

            Transaction transactionEntrada = new Transaction();
            transactionEntrada.setDataTransacao(new java.sql.Date(sdf.parse(this.txtData.getText()).getTime()));
            transactionEntrada.setNumero_conta(Integer.parseInt(this.cbContaDestino.getSelectedItem().toString()));
            transactionEntrada.setValor(Double.parseDouble(this.txtValor.getText()));
            transactionEntrada.setTipo("Receita");
            transactionEntrada.setId_usuario(this.userLogado.getId());
            transactionEntrada.setAnaliticaFinanceira("Transferencia Entrada");

            GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this, "Realizando Transferência");
            TransferenciaWorker transferenciaWorker = new TransferenciaWorker(this, genericLoadingDialog, transactionSaida, transactionEntrada);
            transferenciaWorker.execute();
            genericLoadingDialog.setVisible(true);


        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Preencha a data no formato 'dd/MM/yyyy'");
        }
    }

    public void popularContas() {

        GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this, "Buscando contas");
        LancamentoPopulationContaWorker populationContaWorker = new LancamentoPopulationContaWorker<>(this, new ContaService(), userLogado, genericLoadingDialog, cbContaOrigem);
        populationContaWorker.execute();
        genericLoadingDialog.setVisible(true);

        LancamentoPopulationContaWorker populationContaWorker2 = new LancamentoPopulationContaWorker<>(this, new ContaService(), userLogado, genericLoadingDialog, cbContaDestino);
        populationContaWorker2.execute();
        genericLoadingDialog.setVisible(true);

    }


    public TransferenciaWindow(Usuario userLogado) {
        this.userLogado = userLogado;
        this.criarMascaraData();
        this.initComponent();
        setVisible(true);
        this.popularContas();
        this.setarAnaliticaTransferencia();
    }


    /**
     * Create the frame.
     */
    public void initComponent() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 481, 375);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lbTransferenciaContas = new JLabel("Transferencia entre minhas contas");
        lbTransferenciaContas.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbTransferenciaContas.setBounds(104, 10, 275, 31);
        contentPane.add(lbTransferenciaContas);

        JLabel lbContaOrigem = new JLabel("Conta Origem");
        lbContaOrigem.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbContaOrigem.setBounds(104, 106, 79, 12);
        contentPane.add(lbContaOrigem);

        JLabel lbContaDestino = new JLabel("Conta Destino");
        lbContaDestino.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbContaDestino.setBounds(104, 146, 79, 12);
        contentPane.add(lbContaDestino);

        JLabel lbData = new JLabel("Data");
        lbData.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbData.setBounds(104, 65, 63, 12);
        contentPane.add(lbData);

        lbValor = new JLabel("Valor");
        lbValor.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbValor.setBounds(104, 185, 63, 12);
        contentPane.add(lbValor);

        JLabel lbAnalitica = new JLabel("Tipo");
        lbAnalitica.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbAnalitica.setBounds(104, 229, 63, 12);
        contentPane.add(lbAnalitica);

        txtValor = new JTextField();
        txtValor.setColumns(10);
        txtValor.setBounds(143, 183, 104, 19);
        contentPane.add(txtValor);

        cbContaOrigem = new JComboBox();
        cbContaOrigem.setBounds(193, 103, 104, 20);
        contentPane.add(cbContaOrigem);

        cbContaDestino = new JComboBox();
        cbContaDestino.setBounds(193, 143, 104, 20);
        contentPane.add(cbContaDestino);

        cbTipo = new JComboBox();
        cbTipo.setBounds(177, 226, 120, 20);
        contentPane.add(cbTipo);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarTransferencia();
            }
        });
        btnConfirmar.setBounds(259, 276, 120, 36);
        contentPane.add(btnConfirmar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancelar.setBounds(104, 276, 120, 36);
        contentPane.add(btnCancelar);

        txtData = new JFormattedTextField(this.mascaraData);
        txtData.setBounds(143, 63, 104, 20);
        contentPane.add(txtData);

    }
}
