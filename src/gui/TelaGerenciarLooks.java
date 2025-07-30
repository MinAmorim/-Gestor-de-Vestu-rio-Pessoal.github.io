package gui;

import modelo.Look;
import persistencia.LookDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaGerenciarLooks extends JFrame {
    private static final Color COR_FUNDO = new Color(255, 223, 229);

    private LookDAO lookDAO;
    private JTable tabelaLooks;
    private DefaultTableModel tableModel;

    public TelaGerenciarLooks() {
        this.lookDAO = new LookDAO();

        setTitle("Gerenciar Looks");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"ID", "Nome do Look", "Qtd. Usos"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaLooks = new JTable(tableModel);
        tabelaLooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        tabelaLooks.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tabelaLooks);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(COR_FUNDO);

        JButton btnCriar = new JButton("criar Novo Look");
        btnCriar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                TelaCriarLook telaCriar = new TelaCriarLook(TelaGerenciarLooks.this);
                telaCriar.setVisible(true);
                carregarLooks();
            }
        });
        painelBotoes.add(btnCriar);

        JButton btnEditar = new JButton("editar Look Selecionado");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int selectedRow = tabelaLooks.getSelectedRow();
                if (selectedRow >= 0) {
                    int lookId = (int) tableModel.getValueAt(selectedRow, 0);
                    Look lookParaEditar = buscarLookPorId(lookId);
                    if (lookParaEditar != null) {
                        TelaEditarLook telaEditar = new TelaEditarLook(TelaGerenciarLooks.this, lookParaEditar);
                        telaEditar.setVisible(true);
                        carregarLooks();
                    } else {
                        JOptionPane.showMessageDialog(TelaGerenciarLooks.this, "Erro ao carregar look para edição.", "", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(TelaGerenciarLooks.this, "Selecione um look para editar.", "", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        painelBotoes.add(btnEditar);

        JButton btnRemover = new JButton("Remover Look Selecionado");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int selectedRow = tabelaLooks.getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(TelaGerenciarLooks.this,
                            "Tem certeza que deseja remover este look?", "",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        int lookId = (int) tableModel.getValueAt(selectedRow, 0);
                        lookDAO.removerLookPorId(lookId);
                        JOptionPane.showMessageDialog(TelaGerenciarLooks.this, "Look removido com sucesso!");
                        carregarLooks();
                    }
                } else {
                    JOptionPane.showMessageDialog(TelaGerenciarLooks.this, "Selecione um look para remover.", "", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        painelBotoes.add(btnRemover);

        JButton btnVerHistorico = new JButton("Ver Histórico de Uso");
        btnVerHistorico.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int selectedRow = tabelaLooks.getSelectedRow();
                if (selectedRow >= 0) {
                    int lookId = (int) tableModel.getValueAt(selectedRow, 0);
                    Look lookSelecionado = buscarLookPorId(lookId);
                    if (lookSelecionado != null) {
                        TelaUsoDoLook telaHistorico = new TelaUsoDoLook(TelaGerenciarLooks.this, lookSelecionado); 
                        telaHistorico.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(TelaGerenciarLooks.this, "Erro ao carregar histórico do look.", "", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(TelaGerenciarLooks.this, "Selecione um look para ver o histórico.", "", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        painelBotoes.add(btnVerHistorico);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
        painelBotoes.add(btnVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

        carregarLooks();
    }

    public void carregarLooks() { 
        tableModel.setRowCount(0);
        List<Look> looks = lookDAO.listarTodosLooks();
        for (Look look : looks) {
            tableModel.addRow(new Object[]{look.getId(), look.getNome(), look.getTotalUsos()});
        }
    }

    private Look buscarLookPorId(int id) {
        List<Look> todosLooks = lookDAO.listarTodosLooks();
        for (Look look : todosLooks) {
            if (look.getId() == id) {
                return look;
            }
        }
        return null;
    }
}
