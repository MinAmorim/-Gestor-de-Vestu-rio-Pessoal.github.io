package gui;

import modelo.Look;
import modelo.RegistrarUso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaUsoDoLook extends JDialog {
    private JTable tabelaHistorico;
    private DefaultTableModel tableModel;

    public TelaUsoDoLook(JFrame parent, Look look) {
        super(parent, "Histórico de Uso", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(null);
        
        JLabel lblTitulo = new JLabel("Histórico do Look: " + look.getNome());
        lblTitulo.setBounds(20, 10, 300, 25);
        add(lblTitulo);

        tableModel = new DefaultTableModel(new String[]{"Data", "Ocasião"}, 0);
        tabelaHistorico = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(tabelaHistorico);
        scroll.setBounds(20, 50, 440, 250);
        add(scroll);

        List<RegistrarUso> usos = look.getUsosRegistrados();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (usos != null) {
            for (RegistrarUso uso : usos) {
                String[] linha = {uso.getData().format(formatador), uso.getDescricao()};
                tableModel.addRow(linha);
            }
        }

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBounds(200, 320, 100, 30);
        btnFechar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(btnFechar);
    }
}
