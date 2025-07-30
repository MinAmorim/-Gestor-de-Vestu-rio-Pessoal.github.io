package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.Item;
import persistencia.ItemDAO;

public class TelaGerenciarItens extends JFrame {

    private static final Color COR_FUNDO = new Color(255, 223, 229);

    private JTable tabela;
    private DefaultTableModel modelo;
    private ItemDAO itemDAO;
    private List<Item> listaItens;

    public TelaGerenciarItens() {
        setTitle("Gerenciador de Itens");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COR_FUNDO);

        itemDAO = new ItemDAO();

        JLabel titulo = new JLabel("Meus Itens", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        String[] colunas = {"Tipo", "Nome", "Cor", "Tamanho", "Loja", "Conservação"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabela.setRowHeight(25);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout());
        JButton btnNovo = new JButton("Adicionar Novo");
        JButton btnEditar = new JButton("Editar Selecionado");
        JButton btnRemover = new JButton("Remover Selecionado");
        JButton btnVoltar = new JButton("Voltar");

        botoes.add(btnNovo);
        botoes.add(btnEditar);
        botoes.add(btnRemover);
        botoes.add(btnVoltar);

        add(botoes, BorderLayout.SOUTH);

        btnNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaAdicionarItem tela = new TelaAdicionarItem(TelaGerenciarItens.this);
                tela.setVisible(true);
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarItem();
            }
        });

        btnRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removerItem();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        
        atualizarTabela();
    }

    
    private void editarItem() {
        int i = tabela.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item para editar.");
            return;
        }
        Item item = listaItens.get(i);
        TelaEditarItem telaEditar = new TelaEditarItem(this, item);
        telaEditar.setVisible(true);
    }

    
    private void removerItem() {
        int i = tabela.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item para remover.");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this, "Tem certeza que quer remover esse item?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            Item item = listaItens.get(i);
            itemDAO.removerItemPorId(item.getId());
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Item removido com sucesso.");
        }
    }

    
    public void atualizarTabela() {
        modelo.setRowCount(0); 
        listaItens = itemDAO.listarTodosItens();
        for (Item item : listaItens) {
            modelo.addRow(new Object[]{
                item.getTipo(),
                item.getNome(),
                item.getCor(),
                item.getTamanho(),
                item.getLoja(),
                item.getConservacao()
            });
        }
    }
}
