package br.edu.utfpr.oo2.ProjetoOO2.gui.conta;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.gui.conta.contaWorkers.EditarContaWorker;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.awt.Font;

public class EditarContaWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNumeroConta;
	private JComboBox cbTipoConta;
	private JButton btnAtualizar;
	private JButton btnCancelar;
	private JTextField txtAgencia;
	private JComboBox cbNomeBanco;

    private ContaService contaService;
    private Conta contaOld;
    private Conta contaNew;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditarContaWindow frame = new EditarContaWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    */
	/**
	 * Create the frame.
	 */

    private void popularTipoConta() {

        this.cbTipoConta.addItem("Poupança");
        this.cbTipoConta.addItem("Corrente");
        this.cbTipoConta.addItem("Salário");
        this.cbTipoConta.addItem("Outra");

    }

    private void popularNomeBanco() {

        this.cbNomeBanco.addItem("Brasil");
        this.cbNomeBanco.addItem("Itaú");
        this.cbNomeBanco.addItem("Sicredi");
        this.cbNomeBanco.addItem("Bradesco");
    }

    private void popularCampos(){

        this.popularTipoConta();
        this.popularNomeBanco();

        this.txtNumeroConta.setText(String.valueOf(contaOld.getNumeroConta()));
        this.cbTipoConta.setSelectedItem(contaOld.getTipoConta());
        this.txtAgencia.setText(String.valueOf(contaOld.getAgencia()));
        this.cbNomeBanco.setSelectedItem(contaOld.getNomeBanco());
    }

    private boolean verificarCamposVazios() {
        if (txtAgencia.getText().isEmpty() || txtNumeroConta.getText().isEmpty()
                || cbNomeBanco.getSelectedItem() == null || cbTipoConta.getSelectedItem() == null) {

            return true;
        }
        return false;
    }

    private void atualizarConta(){

        if (this.verificarCamposVazios()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.ERROR_MESSAGE);
            return;
        }

        contaNew.setNumeroConta(Integer.parseInt(txtNumeroConta.getText().trim()));
        contaNew.setTipoConta((String)cbTipoConta.getSelectedItem());
        contaNew.setAgencia(Integer.parseInt(txtAgencia.getText().trim()));
        contaNew.setNomeBanco((String)cbNomeBanco.getSelectedItem());

        GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this, "Atualizando conta");

        EditarContaWorker editarContaWorker = new EditarContaWorker(this,genericLoadingDialog,contaOld,contaNew,contaService);
        editarContaWorker.execute();
        genericLoadingDialog.setVisible(true);



    }


    public EditarContaWindow(Conta conta) {
        initComponent();
        this.contaService = new ContaService();
        this.contaOld = conta;
        this.contaNew =  new Conta();
        popularCampos();
    }

	public void initComponent() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 458, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbEdiarContaTitle = new JLabel("Ediar Conta");
		lbEdiarContaTitle.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lbEdiarContaTitle.setBounds(171, 10, 111, 35);
		contentPane.add(lbEdiarContaTitle);
		
		JLabel lbNumeroConta = new JLabel("Numero da Conta");
		lbNumeroConta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbNumeroConta.setBounds(97, 58, 106, 19);
		contentPane.add(lbNumeroConta);

		
		JLabel lbAgencia = new JLabel("Agência");
		lbAgencia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbAgencia.setBounds(97, 150, 106, 19);
		contentPane.add(lbAgencia);
		
		JLabel lbTipoConta = new JLabel("Tipo Conta");
		lbTipoConta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbTipoConta.setBounds(97, 195, 75, 19);
		contentPane.add(lbTipoConta);
		
		txtAgencia = new JTextField();
		txtAgencia.setBounds(182, 148, 97, 26);
		contentPane.add(txtAgencia);
		txtAgencia.setColumns(10);
        txtAgencia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // bloqueia caracteres
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });

		
		JLabel lbNomeBanco = new JLabel("Nome do Banco");
		lbNomeBanco.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbNomeBanco.setBounds(97, 103, 106, 19);
		contentPane.add(lbNomeBanco);
		
		cbNomeBanco = new JComboBox();
		cbNomeBanco.setBounds(213, 100, 97, 26);
		contentPane.add(cbNomeBanco);
		
		txtNumeroConta = new JTextField();
		txtNumeroConta.setColumns(10);
		txtNumeroConta.setBounds(213, 55, 97, 26);
		contentPane.add(txtNumeroConta);
        txtNumeroConta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // bloqueia caracteres
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
		
		cbTipoConta = new JComboBox();
		cbTipoConta.setBounds(182, 195, 128, 26);
		contentPane.add(cbTipoConta);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                atualizarConta();
			}
		});
		btnAtualizar.setBounds(273, 262, 111, 39);
		contentPane.add(btnAtualizar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                dispose();
			}
		});
		btnCancelar.setBounds(83, 262, 111, 39);
		contentPane.add(btnCancelar);

	}
}
