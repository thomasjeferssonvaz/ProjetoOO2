package br.edu.utfpr.oo2.ProjetoOO2.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.contaWorkers.SearchContaTaskWorker;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;

public class EditarContaWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tbContas;
    private Usuario userLogado;
    private ContaService contaService;
    private JButton btnAtualizar;
    private JButton btnCancelar;
    private JScrollPane scrollPane;


	/**
	 * Launch the application.

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	EditarContaWindow frame = new EditarContaWindow();
				//	frame.setVisible(true);
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

    private void pupularJtable(){


        GenericLoadingDialog genericLoadingDialog =
                new GenericLoadingDialog(this,"Buscando Contas");


        SearchContaTaskWorker searchContas =
                new SearchContaTaskWorker(contaService, userLogado, this, genericLoadingDialog,tbContas);

        searchContas.execute();
        genericLoadingDialog.setVisible(true);


        /*try{
            List<Conta> contasDB = this.contaService.buscarPorUsuario(userLogado);
            DefaultTableModel model = (DefaultTableModel) tbContas.getModel();
            model.fireTableDataChanged();
            model.setRowCount(0);

            for (Conta contaDB : contasDB) {

                model.addRow(new Object[] {
                        contaDB.getIdConta(),
                        contaDB.getNomeBanco(),
                        contaDB.getAgencia(),
                        contaDB.getNumeroConta(),
                        contaDB.getTipoConta()
                });
            }
        }catch (Exception e){
           System.out.println(e.getMessage());
        }*/
    }


	public EditarContaWindow( Usuario userLogado) {
    this.userLogado = userLogado;
	this.contaService = new ContaService();
	this.initComponent();
    setVisible(true);
    this.pupularJtable();

	}



	private void initComponent() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbTituloAtualizarConta = new JLabel("Atualizar Conta");
		lbTituloAtualizarConta.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbTituloAtualizarConta.setBounds(142, 11, 150, 29);
		contentPane.add(lbTituloAtualizarConta);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 414, 143);
		contentPane.add(scrollPane);

		tbContas = new JTable();
		scrollPane.setViewportView(tbContas);
		tbContas.setModel(new DefaultTableModel(
			new Object[][] {
            },
                new String[] {
                    "Id", "Nome do Banco", "Ag\u00EAncia", "N\u00FAmero da Conta", "Tipo"
			}){

                //não permite editar a tabela
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            }

        );


		btnAtualizar = new JButton("Atualizar Selecionado");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnAtualizar.setBounds(289, 221, 135, 29);
		contentPane.add(btnAtualizar);

		btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
		btnCancelar.setBounds(10, 221, 135, 29);
		contentPane.add(btnCancelar);



	}
}
