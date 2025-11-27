package br.edu.utfpr.oo2.ProjetoOO2.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class TransferenciaWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField cbValor;
	private JComboBox cbContaOrigem;
	private JComboBox cbContaDestino;
	private JLabel lbValor;
	private JComboBox cbAnalitica;
	private JButton btnConfirmar;
	private JButton btnCancelar;

    private MaskFormatter mascaraData;
    private JFormattedTextField txtData;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
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


    private void criarMascaraData()
    {
        try {
            this.mascaraData = new MaskFormatter("##/##/####");
        }catch(Exception e){
            System.out.println("Erro ao criar MascaraData");
        }
    }

    private void setarAnaliticaTransferencia() {
        this.cbAnalitica.addItem("Transferencia");
        this.cbAnalitica.setSelectedItem("Transferencia");
        this.cbAnalitica.setEnabled(false);
    }



    public TransferenciaWindow() {
        this.criarMascaraData();
        this.initComponent();
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
		
		JLabel lbAnalitica = new JLabel("Analitica");
		lbAnalitica.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbAnalitica.setBounds(104, 229, 63, 12);
		contentPane.add(lbAnalitica);
		
		cbValor = new JTextField();
		cbValor.setColumns(10);
		cbValor.setBounds(143, 183, 104, 19);
		contentPane.add(cbValor);
		
		cbContaOrigem = new JComboBox();
		cbContaOrigem.setBounds(193, 103, 104, 20);
		contentPane.add(cbContaOrigem);
		
		cbContaDestino = new JComboBox();
		cbContaDestino.setBounds(193, 143, 104, 20);
		contentPane.add(cbContaDestino);
		
		cbAnalitica = new JComboBox();
		cbAnalitica.setBounds(177, 226, 120, 20);
		contentPane.add(cbAnalitica);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
