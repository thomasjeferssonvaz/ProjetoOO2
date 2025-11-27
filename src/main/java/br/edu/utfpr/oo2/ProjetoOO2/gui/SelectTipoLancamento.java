package br.edu.utfpr.oo2.ProjetoOO2.gui;


import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectTipoLancamento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCancelar;
	private JButton btnDespesas;
	private JButton btnReceitas;
	private JButton btnTransferencia;

    private Usuario userLogado;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectTipoLancamento frame = new SelectTipoLancamento();
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


    public SelectTipoLancamento(Usuario userLogado) {
        this.userLogado = userLogado;
        this.initComponent();
    }


	public void initComponent() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbTipoLancamento = new JLabel("Selecione o tipo de Lançamento");
		lbTipoLancamento.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbTipoLancamento.setBounds(118, 10, 214, 19);
		contentPane.add(lbTipoLancamento);
		
		btnTransferencia = new JButton("TRANSFERÊNCIA");
		btnTransferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                TransferenciaWindow transferenciaWindow = new TransferenciaWindow();
                transferenciaWindow.setVisible(true);
			}
		});
		btnTransferencia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnTransferencia.setBounds(118, 55, 214, 44);
		contentPane.add(btnTransferencia);
		
		btnReceitas = new JButton("RECEITAS");
		btnReceitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                ReceitasWindow receitasWindow = new ReceitasWindow(userLogado);
               // receitasWindow.setVisible(true);
			}
		});
		btnReceitas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnReceitas.setBounds(118, 109, 214, 44);
		contentPane.add(btnReceitas);
		
		btnDespesas = new JButton("DESPESAS");
		btnDespesas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                DespesasWindow despesasWindow = new DespesasWindow(userLogado);
                despesasWindow.setVisible(true);
			}
		});
		btnDespesas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDespesas.setBounds(118, 163, 214, 44);
		contentPane.add(btnDespesas);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                dispose();
			}
		});
		btnCancelar.setBounds(20, 226, 97, 27);
		contentPane.add(btnCancelar);

	}
}
