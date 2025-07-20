package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.Pessoa;
import modelo.Look;
import modelo.Item;

public class TelaGerenciarLooks extends JFrame {

    private Pessoa pessoa;
    private JTable tabelaLooks;
    private DefaultTableModel modeloTabela;
    private JTextArea areaDetalhes;

    public TelaGerenciarLooks(Pessoa pessoa) {
        this.pessoa = pessoa;

        setTitle("Gerenciador de Looks");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel principal que vai conter tudo
        JPanel painelConteudo = new JPanel(new BorderLayout(10, 10));
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // --- Título ---
        JLabel lblTitulo = new JLabel("Meus Looks", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        painelConteudo.add(lblTitulo, BorderLayout.NORTH);

        // --- Painel Central com a Tabela e os Detalhes ---
        JSplitPane painelDividido = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        painelDividido.setResizeWeight(0.6); // 60% do espaço para a tabela

        // Tabela de Looks
        String[] colunas = {"Nome do Look"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaLooks = new JTable(modeloTabela);
        tabelaLooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaLooks.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tabelaLooks.setRowHeight(30);
        painelDividido.setTopComponent(new JScrollPane(tabelaLooks));

        // Área de Detalhes
        areaDetalhes = new JTextArea("Selecione um look para ver os detalhes.");
        areaDetalhes.setEditable(false);
        areaDetalhes.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaDetalhes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelDividido.setBottomComponent(new JScrollPane(areaDetalhes));
        
        painelConteudo.add(painelDividido, BorderLayout.CENTER);

        // --- Painel de Botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdicionar = new JButton("Criar Novo Look");
        JButton btnEditar = new JButton("Editar Look");
        JButton btnRemover = new JButton("Remover Look");
        JButton btnVoltar = new JButton("Voltar");
        
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnVoltar);
        painelConteudo.add(painelBotoes, BorderLayout.SOUTH);

        // Adiciona o painel de conteúdo à janela
        add(painelConteudo);
        
        // --- Ações ---
        btnAdicionar.addActionListener(_ -> {
            TelaCriarLook telaCriar = new TelaCriarLook(this, pessoa);
            telaCriar.setVisible(true);
        });
        btnEditar.addActionListener(_ -> editarLookSelecionado());
        btnRemover.addActionListener(_ -> removerLookSelecionado());
        btnVoltar.addActionListener(_ -> dispose());

        // Listener para atualizar os detalhes quando um look é selecionado
        tabelaLooks.getSelectionModel().addListSelectionListener(_ -> mostrarDetalhesDoLook());
        
        // Atualiza a tabela com os dados iniciais
        atualizarTabela();
    }

    private void editarLookSelecionado() {
        int linhaSelecionada = tabelaLooks.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um look para editar.", "Nenhum look selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Look lookParaEditar = pessoa.getLooks().get(linhaSelecionada);
        TelaEditarLook telaEditar = new TelaEditarLook(this, pessoa, lookParaEditar);
        telaEditar.setVisible(true);
    }

    private void removerLookSelecionado() {
        int linhaSelecionada = tabelaLooks.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um look para remover.", "Nenhum look selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o look selecionado?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            pessoa.removerLook(linhaSelecionada);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Look removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarDetalhesDoLook() {
        int linhaSelecionada = tabelaLooks.getSelectedRow();
        if (linhaSelecionada != -1) {
            Look lookSelecionado = pessoa.getLooks().get(linhaSelecionada);
            StringBuilder detalhes = new StringBuilder("Itens do Look: '" + lookSelecionado.getNome() + "'\n\n");
            for (Item item : lookSelecionado.listarItensDoLook()) {
                detalhes.append(String.format("- %s: %s (%s)\n", item.getTipo(), item.getNome(), item.getCor()));
            }
            areaDetalhes.setText(detalhes.toString());
        } else {
            areaDetalhes.setText("Selecione um look para ver os detalhes.");
        }
    }

    public void atualizarTabela() {
        modeloTabela.setRowCount(0); // Limpa a tabela
        for (Look look : pessoa.getLooks()) {
            modeloTabela.addRow(new Object[]{look.getNome()});
        }
        // Limpa a área de detalhes após qualquer atualização
        mostrarDetalhesDoLook();
    }
}