package br.edu.utfpr.oo2.ProjetoOO2.gui;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginWindow loginWindow = new LoginWindow();
                    loginWindow.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
	}



	public MainWindow() {
        this.initComponent();
	}

    public void initComponent() {
        setTitle("Main");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 434, 261);
        contentPane.add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu userMenu = new JMenu("Usuário");
        menuBar.add(userMenu);

        JMenuItem cadastrarUsuarioMenuItem = new JMenuItem("Cadastrar usuário");
        cadastrarUsuarioMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                CadastroUsuarioWindow cadastroUsuarioWindow = new CadastroUsuarioWindow();
                cadastroUsuarioWindow.setVisible(true);
        	}
        });
        userMenu.add(cadastrarUsuarioMenuItem);

    }
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
