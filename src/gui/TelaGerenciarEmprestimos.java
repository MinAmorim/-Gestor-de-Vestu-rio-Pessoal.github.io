package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import modelo.Item;
import modelo.subclasses.RoupaIntima;
import persistencia.ItemDAO;

public class TelaGerenciarEmprestimos extends JFrame {

    private ItemDAO itemDAO;
    private JList<Item> listaItens;
    private DefaultListModel<Item> listModel;

    public TelaGerenciarEmprestimos() {
        this.itemDAO = new ItemDAO();
        setTitle("Gerenciar Empréstimos");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("Itens Emprestáveis", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        listaItens = new JList<>(listModel);
        listaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaItens.setFont(new Font("Monospaced", Font.PLAIN, 14));

        listaItens.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Item) {
                    Item item = (Item) value;
                    String status;
                    if (item.isEmprestado()) {
                        // CORRIGIDO: Chamada ao método correto getParaQuemEmprestado()
                        status = "[EMPRESTADO PARA: " + item.getParaQuemEstaEmprestado() + "]";
                    } else {
                        status = "[DISPONÍVEL]";
                    }
                    setText(String.format("%-35s %s", status, item.getNome()));
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(listaItens);
        scrollPane.setBorder(new TitledBorder("Selecione um item:"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnEmprestar = new JButton("Registrar Empréstimo");
        JButton btnDevolver = new JButton("Registrar Devolução");

        painelBotoes.add(btnEmprestar);
        painelBotoes.add(btnDevolver);
        add(painelBotoes, BorderLayout.SOUTH);

        btnEmprestar.addActionListener(_ -> registrarEmprestimo());
        btnDevolver.addActionListener(_ -> registrarDevolucao());

        atualizarLista();
    }

    private void atualizarLista() {
        listModel.clear();
        itemDAO.listarTodosItens().stream()
              .filter(item -> !(item instanceof RoupaIntima))
              .forEach(listModel::addElement);
    }

    private void registrarEmprestimo() {
        Item itemSelecionado = listaItens.getSelectedValue();
        if (itemSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item.", "Nenhum Item", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (itemSelecionado.isEmprestado()) {
            // CORRIGIDO: Chamada ao método correto getParaQuemEmprestado()
            JOptionPane.showMessageDialog(this, "Este item já está emprestado para " + itemSelecionado.getParaQuemEstaEmprestado() + ".", "Ação Inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String paraQuem = JOptionPane.showInputDialog(this, "Emprestar para quem?", "Registrar Empréstimo", JOptionPane.PLAIN_MESSAGE);
        if (paraQuem != null && !paraQuem.trim().isEmpty()) {
            itemSelecionado.registrarEmprestimo(paraQuem.trim());
            itemDAO.atualizarStatusEmprestimo(itemSelecionado);
            JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "O nome da pessoa é obrigatório.", "Operação Cancelada", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void registrarDevolucao() {
        Item itemSelecionado = listaItens.getSelectedValue();
        if (itemSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item.", "Nenhum Item", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!itemSelecionado.isEmprestado()) {
            JOptionPane.showMessageDialog(this, "Este item não está emprestado.", "Ação Inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }
        itemSelecionado.registrarDevolucao();
        itemDAO.atualizarStatusEmprestimo(itemSelecionado);
        JOptionPane.showMessageDialog(this, "Devolução registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        atualizarLista();
    }
}