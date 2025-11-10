package br.edu.utfpr.oo2.ProjetoOO2.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CadastroContaWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox cbTipoConta;
	private JButton btnCadastarConta;
	private JButton btnCancelarConta;
	private JComboBox cbNomeBanco;
	private JTextField txtAgencia;
	private JTextField txtNumeroConta;

	private Usuario userLogado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// CadastroContaWindow frame = new CadastroContaWindow();
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadastroContaWindow(Usuario userLogado) { // Passar como parametro um usuario
        this.userLogado = userLogado;
		this.initComponent();
		this.popularNomeBanco();
		this.popularTipoConta();

	}

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

	private boolean verificarCamposVazios() {
		if (txtAgencia.getText().isEmpty() || txtNumeroConta.getText().isEmpty()
				|| cbNomeBanco.getSelectedItem() == null || cbTipoConta.getSelectedItem() == null) {

			return true;
		}
		return false;
	}

	private void limparCampos() {
		this.txtAgencia.setText("");
		this.txtNumeroConta.setText("");
		this.cbNomeBanco.setSelectedIndex(0);
		this.cbTipoConta.setSelectedIndex(0);
	}


	private void cadastrarConta() {

		Conta conta = new Conta();

		try {

			// Verifica se os campos não estão vazio
			if (this.verificarCamposVazios()) {
				JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.ERROR_MESSAGE);
				return;
			}

			conta.setNomeBanco((String) this.cbNomeBanco.getSelectedItem());
			conta.setAgencia(Integer.parseInt(this.txtAgencia.getText()));
			conta.setNumeroConta(Integer.parseInt(this.txtNumeroConta.getText()));
			conta.setTipoConta((String) this.cbTipoConta.getSelectedItem());
			conta.setIdUsuario(this.userLogado.getId());

			// Passa o paramento para ContaService
			ContaService contaService = new ContaService();
			contaService.cadastrarConta(conta);

			// Conta cadastrada
			JOptionPane.showMessageDialog(btnCadastarConta, "Conta cadastrada com sucesso", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);

			// Se nao for possivel cadastrar a conta
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(btnCadastarConta, "Os campos dever conter Apenas numeros", "Erro",
					JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(btnCadastarConta, "Erro ao acessar o banco de dados: " + e.getMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(btnCadastarConta, "Ocorreu um erro inesperado: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void initComponent() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 452, 316);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbTitulo = new JLabel("Cadastrar Conta");
		lbTitulo.setBounds(154, 11, 120, 26);
		lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lbTitulo);

		txtAgencia = new JTextField();
		txtAgencia.setBounds(179, 96, 86, 20);
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

		JLabel lbAgencia = new JLabel("Agência");
		lbAgencia.setBounds(123, 98, 46, 14);
		lbAgencia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lbAgencia);

		JLabel lbNumeroConta = new JLabel("Número da Conta");
		lbNumeroConta.setBounds(123, 130, 98, 14);
		lbNumeroConta.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lbNumeroConta);

		txtNumeroConta = new JTextField();
		txtNumeroConta.setBounds(223, 128, 86, 20);
		contentPane.add(txtNumeroConta);
		txtNumeroConta.setColumns(10);
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

		JLabel lbTipoConta = new JLabel("Tipo da Conta");
		lbTipoConta.setBounds(123, 164, 86, 14);
		lbTipoConta.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lbTipoConta);

		cbTipoConta = new JComboBox();
		cbTipoConta.setBounds(211, 161, 98, 22);
		contentPane.add(cbTipoConta);

		btnCadastarConta = new JButton("Cadastrar");
		btnCadastarConta.setBounds(220, 215, 106, 35);
		btnCadastarConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				cadastrarConta();
				limparCampos();
			}
		});
		contentPane.add(btnCadastarConta);

		btnCancelarConta = new JButton("Cancelar");
		btnCancelarConta.setBounds(103, 215, 106, 35);
        btnCancelarConta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                    dispose();
            }
        });
		contentPane.add(btnCancelarConta);

		JLabel lbNomeBanco = new JLabel("Banco");
		lbNomeBanco.setBounds(123, 62, 46, 14);
		contentPane.add(lbNomeBanco);

		cbNomeBanco = new JComboBox();
		cbNomeBanco.setBounds(179, 58, 130, 22);
		contentPane.add(cbNomeBanco);

	}

}
