package br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos.workers.SearchInvestimentoWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MinhasMetasWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tbMetas;

    private Usuario userLogado;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MinhasMetasWindow frame = new MinhasMetasWindow();
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

    private void buscarMetas() {

        GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this,"Buscando metas");
        SearchInvestimentoWorker searchInvestimentoWorker = new SearchInvestimentoWorker(this,genericLoadingDialog,tbMetas,userLogado);
        searchInvestimentoWorker.execute();
        genericLoadingDialog.setVisible(true);



    }

    public MinhasMetasWindow(Usuario userLogado) {
        this.userLogado = userLogado;
        this.initComponent();
        setVisible(true);
        buscarMetas();
    }

	public void initComponent() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 416, 174);
		contentPane.add(scrollPane);
		
		tbMetas = new JTable();
		scrollPane.setViewportView(tbMetas);
		tbMetas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Nome", "Tipo", "Local", "Aporte Mensal"
			}){
			public boolean isCellEditable(int row, int column) {
                return false;
            }
                         }
		);
		
		JLabel lbMinhasMetas = new JLabel("Minhas Metas");
		lbMinhasMetas.setBounds(163, 10, 119, 25);
		lbMinhasMetas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lbMinhasMetas);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnFechar.setBounds(180, 229, 84, 30);
		contentPane.add(btnFechar);

	}

}
