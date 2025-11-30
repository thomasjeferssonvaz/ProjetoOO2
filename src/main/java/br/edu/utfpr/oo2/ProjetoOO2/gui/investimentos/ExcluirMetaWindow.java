package br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos.workers.ExcluirMetaWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos.workers.PopulationMetaCbWorker;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExcluirMetaWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

    private JComboBox cbMetas;
    private JButton btnExcluir;
    private JButton btnCancelar;

    private Usuario userLogado;
    /**
     * Launch the application.
     */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExcluirMetaWindow frame = new ExcluirMetaWindow();
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

    private void popularMetas() {

        GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this,"Buscando Metas");
        PopulationMetaCbWorker populationMetaCbWorker = new PopulationMetaCbWorker(this, genericLoadingDialog,cbMetas,userLogado);
        populationMetaCbWorker.execute();
        genericLoadingDialog.setVisible(true);

    }

    public void excluirMeta() {

        int confirm = JOptionPane.showConfirmDialog(this,"Deseja excluir a meta?","Confirm", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

        if (confirm == 0) {
            GenericLoadingDialog genericLoadingDialog = new GenericLoadingDialog(this,"Excluir Meta");
            ExcluirMetaWorker excluirMetaWorker = new ExcluirMetaWorker(this,genericLoadingDialog,userLogado,this.cbMetas.getSelectedItem().toString());
            excluirMetaWorker.execute();
            genericLoadingDialog.setVisible(true);
        }else{
         //   dispose();
        }

    }



    public ExcluirMetaWindow(Usuario userLogado) {
        this.userLogado = userLogado;
        this.initComponents();
        setVisible(true);
        this.popularMetas();
    }


	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbExcluir = new JLabel("Excluir Meta de investimento");
		lbExcluir.setBounds(88, 10, 257, 25);
		lbExcluir.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lbExcluir);
		
		JLabel lbNomeMeta = new JLabel("Nome da Meta");
		lbNomeMeta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbNomeMeta.setBounds(177, 63, 94, 14);
		contentPane.add(lbNomeMeta);
		
		cbMetas = new JComboBox();
		cbMetas.setBounds(149, 88, 155, 22);
		contentPane.add(cbMetas);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                excluirMeta();
			}
		});
		btnExcluir.setBounds(256, 161, 89, 31);
		contentPane.add(btnExcluir);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                dispose();
			}
		});
		btnCancelar.setBounds(88, 161, 89, 31);
		contentPane.add(btnCancelar);

	}
}
