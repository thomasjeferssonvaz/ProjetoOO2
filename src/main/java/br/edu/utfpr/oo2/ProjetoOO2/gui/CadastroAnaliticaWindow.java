package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.AnaliticaFinanceira;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.analiticaWorkers.CadastrarAnaliticaWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.AnaliticaFinanceiraService;

import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastroAnaliticaWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomeAnalitica;
	private JTextField txtDescricao;
	private JLabel lbNomeAnalitica;
	private JLabel ldDescricao;
	private JLabel lbRecorrencia;
	private JComboBox cbRecorrencia;
	private JButton btnCancelar;
	private JButton btnCadastrar;

    private Usuario userLogado;
    private AnaliticaFinanceiraService despesasService;
    private JLabel lbCategoria;
    private JComboBox cbTipoCategoria;

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
        if (txtNomeAnalitica.getText().isEmpty()) {
            return  false;
        }
        return true;
    }

    private void cadastrarDespesa() {

        if (!this.verificarCampos()){
            JOptionPane.showMessageDialog(this,"Nome nao pode ser Vazio", "Error",  JOptionPane.ERROR_MESSAGE);
            return;
        }

        AnaliticaFinanceira despesa = new AnaliticaFinanceira();

        despesa.setNome(txtNomeAnalitica.getText().trim());
        despesa.setCategoriaTipo(cbTipoCategoria.getSelectedItem().toString());
        despesa.setDescricao(txtDescricao.getText().trim());

        Object selecionado = cbRecorrencia.getSelectedItem();
        if (selecionado == null) {
            despesa.setRecorrencia(null);
        } else {
            despesa.setRecorrencia(cbRecorrencia.getSelectedItem().toString());
        }

        //despesa.setRecorrencia(cbRecorrencia.getSelectedItem().toString());
        despesa.setId_usuario(userLogado.getId());

        GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this, "Cadastrando Analitica");

        CadastrarAnaliticaWorker cadastrarDespesaWorker = new CadastrarAnaliticaWorker(this, genericLoadingDialog, despesasService, despesa);
        cadastrarDespesaWorker.execute();
        genericLoadingDialog.setVisible(true);


    }


    private void popularCbTipoDespesa() {
        this.cbRecorrencia.addItem("RECORRENTE");
        this.cbRecorrencia.addItem("PONTUAL");

        this.cbTipoCategoria.addItem("RECEITA");
        this.cbTipoCategoria.addItem("DESPESA");


    }


    public CadastroAnaliticaWindow(Usuario userLogado) {
        despesasService = new AnaliticaFinanceiraService();
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
		
		lbNomeAnalitica = new JLabel("Nome");
		lbNomeAnalitica.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbNomeAnalitica.setBounds(110, 81, 114, 14);
		contentPane.add(lbNomeAnalitica);
		
		ldDescricao = new JLabel("Descrição");
		ldDescricao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ldDescricao.setBounds(110, 147, 147, 14);
		contentPane.add(ldDescricao);
		
		lbRecorrencia = new JLabel("Recorrencia:");
		lbRecorrencia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbRecorrencia.setBounds(110, 213, 95, 14);
		contentPane.add(lbRecorrencia);
		
		txtNomeAnalitica = new JTextField();
		txtNomeAnalitica.setBounds(234, 79, 105, 20);
		contentPane.add(txtNomeAnalitica);
		txtNomeAnalitica.setColumns(10);

		
		txtDescricao = new JTextField();
		txtDescricao.setHorizontalAlignment(SwingConstants.LEFT);
		txtDescricao.setBounds(110, 161, 229, 36);
		contentPane.add(txtDescricao);
		txtDescricao.setColumns(10);
		
		cbRecorrencia = new JComboBox();
		cbRecorrencia.setBounds(234, 210, 105, 22);
		contentPane.add(cbRecorrencia);
		
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
		
		lbCategoria = new JLabel("Categoria");
		lbCategoria.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbCategoria.setBounds(110, 113, 114, 14);
		contentPane.add(lbCategoria);

		
		cbTipoCategoria = new JComboBox();
		cbTipoCategoria.setBounds(234, 110, 105, 22);
		contentPane.add(cbTipoCategoria);
        cbTipoCategoria.addActionListener(e -> {
            String selecionado = (String) cbTipoCategoria.getSelectedItem();

            if ("RECEITA".equals(selecionado)) {
                cbRecorrencia.setEnabled(false);
                cbRecorrencia.setSelectedItem(null); // limpa seleção
            } else {
                cbRecorrencia.setEnabled(true);
            }
        });

	}
}
