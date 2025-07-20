package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.Pessoa;
import modelo.Item;

public class TelaGerenciarItens extends JFrame {

    private Pessoa pessoa;
    private JTable tabelaItens;
    private DefaultTableModel modeloTabela;

    public TelaGerenciarItens(Pessoa pessoa) {
        this.pessoa = pessoa;

        setTitle("Gerenciador de Itens");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Título ---
        JLabel lblTitulo = new JLabel("Meus Itens", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        // --- Tabela de Itens ---
        String[] colunas = {"Tipo", "Nome", "Cor", "Tamanho", "Loja", "Conservação"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaItens = new JTable(modeloTabela);
        tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaItens.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaItens.setRowHeight(25);
        
        atualizarTabela();

        JScrollPane painelTabela = new JScrollPane(tabelaItens);
        add(painelTabela, BorderLayout.CENTER);

        // --- Painel de Botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdicionar = new JButton("Adicionar Novo");
        JButton btnEditar = new JButton("Editar Selecionado");
        JButton btnRemover = new JButton("Remover Selecionado");
        JButton btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- Ações dos Botões ---
        
        btnAdicionar.addActionListener(_ -> {
            TelaAdicionarItem telaAdicionar = new TelaAdicionarItem(this, pessoa);
            telaAdicionar.setVisible(true);
        });

        btnVoltar.addActionListener(_ -> {
            this.dispose();
        });
        
        btnRemover.addActionListener(_ -> removerItemSelecionado());

        // Ação para editar o item selecionado
        btnEditar.addActionListener(_ -> editarItemSelecionado());
    }
    
    /**
     * Abre a tela de edição para o item que está selecionado na tabela.
     */
    private void editarItemSelecionado() {
        int linhaSelecionada = tabelaItens.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item na tabela para editar.", "Nenhum item selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Pega o objeto Item correspondente à linha selecionada
        Item itemParaEditar = pessoa.getGuardaRoupa().get(linhaSelecionada);

        // Abre a tela de edição, passando o item
        TelaEditarItem telaEditar = new TelaEditarItem(this, itemParaEditar);
        telaEditar.setVisible(true);
    }

    /**
     * Lógica para remover o item que está selecionado na tabela.
     */
    private void removerItemSelecionado() {
        int linhaSelecionada = tabelaItens.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item na tabela para remover.", "Nenhum item selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            this, 
            "Tem certeza que deseja remover o item selecionado?", 
            "Confirmar Remoção", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            pessoa.removerItem(linhaSelecionada);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Item removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Atualiza a tabela com os dados mais recentes da lista de itens da Pessoa.
     */
    public void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Item item : pessoa.getGuardaRoupa()) {
            Object[] linha = {
                item.getTipo(),
                item.getNome(),
                item.getCor(),
                item.getTamanho(),
                item.getLoja(),
                item.getConservacao()
            };
            modeloTabela.addRow(linha);
        }
    }
}