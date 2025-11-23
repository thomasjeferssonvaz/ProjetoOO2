package br.edu.utfpr.oo2.ProjetoOO2.gui.transaction;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;

import javax.swing.*;
import java.util.List;

public class TransactionTela extends JFrame {
    private JComboBox<Conta> contaOrigemComboBox;
    private JComboBox<Conta> contaDestinoComboBox;
    private JTextField valorTextField;
    private JButton transferirButton;

    public TransactionTela(List<Conta> contas){

    }
}
