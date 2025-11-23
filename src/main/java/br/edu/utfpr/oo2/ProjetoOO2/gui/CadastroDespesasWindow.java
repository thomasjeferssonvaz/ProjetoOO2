package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Despesas;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.despesasWorkers.CadastrarDespesaWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.DespesasService;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CadastroDespesasWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomeDespesa;
	private JTextField txtDescricaoDespesa;
	private JLabel lbNomeDespesa;
	private JLabel ldDescricaoDespesa;
	private JLabel lbTipoDespesa;
	private JComboBox cbTipoDespesa;
	private JButton btnCancelar;
	private JButton btnCadastrar;

    private Usuario userLogado;
    private DespesasService despesasService;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroDespesasWindow frame = new CadastroDespesasWindow();
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

    private boolean verificarCampos() {
        if (txtNomeDespesa.getText().isEmpty() || txtDescricaoDespesa.getText().isEmpty()) {
            return  false;
        }
        return true;
    }

    private void cadastrarDespesa() {

        if (!this.verificarCampos()){
            JOptionPane.showMessageDialog(this,"Preencha todos os Campos", "Error",  JOptionPane.ERROR_MESSAGE);
            return;
        }

        Despesas despesa = new Despesas();

        despesa.setNome(txtNomeDespesa.getText().trim());
        despesa.setDescricao(txtDescricaoDespesa.getText().trim());
        despesa.setTipoDespesa(cbTipoDespesa.getSelectedItem().toString());
        despesa.setId_usuario(userLogado.getId());

        GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this, "Cadastrando Despesa");

        CadastrarDespesaWorker cadastrarDespesaWorker = new CadastrarDespesaWorker(this, genericLoadingDialog, despesasService, despesa);
        cadastrarDespesaWorker.execute();
        genericLoadingDialog.setVisible(true);


    }


    private void popularCbTipoDespesa() {
        this.cbTipoDespesa.addItem("RECORRENTE");
        this.cbTipoDespesa.addItem("PONTUAL");
    }


    public CadastroDespesasWindow(Usuario userLogado) {
        despesasService = new DespesasService();
        this.userLogado = userLogado;
        initComponent();
        this.popularCbTipoDespesa();
        setVisible(true);
    }

	public void initComponent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbCadastrarDespesaTitle = new JLabel("CadastrarDespesa");
		lbCadastrarDespesaTitle.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lbCadastrarDespesaTitle.setBounds(161, 32, 156, 23);
		contentPane.add(lbCadastrarDespesaTitle);
		
		lbNomeDespesa = new JLabel("Nome da Despesa:");
		lbNomeDespesa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbNomeDespesa.setBounds(110, 81, 114, 14);
		contentPane.add(lbNomeDespesa);
		
		ldDescricaoDespesa = new JLabel("Descrição da Despesa:");
		ldDescricaoDespesa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ldDescricaoDespesa.setBounds(110, 116, 147, 14);
		contentPane.add(ldDescricaoDespesa);
		
		lbTipoDespesa = new JLabel("Tipo da Despesa");
		lbTipoDespesa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbTipoDespesa.setBounds(110, 195, 95, 14);
		contentPane.add(lbTipoDespesa);
		
		txtNomeDespesa = new JTextField();
		txtNomeDespesa.setBounds(234, 79, 105, 20);
		contentPane.add(txtNomeDespesa);
		txtNomeDespesa.setColumns(10);

		
		txtDescricaoDespesa = new JTextField();
		txtDescricaoDespesa.setHorizontalAlignment(SwingConstants.LEFT);
		txtDescricaoDespesa.setBounds(110, 132, 229, 36);
		contentPane.add(txtDescricaoDespesa);
		txtDescricaoDespesa.setColumns(10);
		
		cbTipoDespesa = new JComboBox();
		cbTipoDespesa.setBounds(234, 192, 105, 22);
		contentPane.add(cbTipoDespesa);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                dispose();
			}
		});
		btnCancelar.setBounds(91, 287, 114, 31);
		contentPane.add(btnCancelar);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                cadastrarDespesa();
			}
		});
		btnCadastrar.setBounds(240, 287, 114, 31);
		contentPane.add(btnCadastrar);

	}
}
