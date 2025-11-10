package br.edu.utfpr.oo2.ProjetoOO2.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class EditarUsuarioWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditarUsuarioWindow frame = new EditarUsuarioWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		});
	}
	
	public EditarUsuarioWindow() {
		this.initContent();
	}
	
	private void initContent() {
		
	}
}
