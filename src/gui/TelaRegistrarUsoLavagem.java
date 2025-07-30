package gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Vector;
import modelo.Look;
import modelo.Item;
import modelo.interfaces.ILavavel;
import persistencia.ItemDAO;
import persistencia.LookDAO;

public class TelaRegistrarUsoLavagem extends JFrame {
    private static final Color COR_FUNDO = new Color(255, 223, 229);


    private LookDAO lookDAO;
    private ItemDAO itemDAO;

    private JComboBox<Look> comboLooks;
    private JList<Item> listaItensLavaveis;
    private DefaultListModel<Item> modeloLista;

    public TelaRegistrarUsoLavagem() { 
        this.lookDAO = new LookDAO();
        this.itemDAO = new ItemDAO();

        setTitle("Registrar Uso e Lavagem");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2, 10, 10));
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new GridBagLayout());
        

        JPanel painelUso = new JPanel(new BorderLayout(10, 10));
        painelUso.setBorder(BorderFactory.createTitledBorder("Registrar Uso de Look"));

        comboLooks = criarComboLooks();
        JTextField txtData = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JComboBox<String> comboPeriodo = new JComboBox<>(new String[]{"Manhã", "Tarde", "Noite"});
        JTextField txtOcasiao = new JTextField();
        JButton btnRegistrarUso = new JButton("Registrar Uso do Look");

        JPanel painelFormUso = new JPanel(new GridLayout(8, 1, 5, 5));
        painelFormUso.add(new JLabel("Selecione o Look:"));
        painelFormUso.add(comboLooks);
        painelFormUso.add(new JLabel("Data (dd/mm/aaaa):"));
        painelFormUso.add(txtData);
        painelFormUso.add(new JLabel("Período do Dia:"));
        painelFormUso.add(comboPeriodo);
        painelFormUso.add(new JLabel("Ocasião (ex: Aniversário da Maria):"));
        painelFormUso.add(txtOcasiao);
        
        painelUso.add(painelFormUso, BorderLayout.CENTER);
        painelUso.add(btnRegistrarUso, BorderLayout.SOUTH);
        
        add(painelUso);

        JPanel painelLavagem = new JPanel(new BorderLayout(10, 10));
        painelLavagem.setBorder(BorderFactory.createTitledBorder("Registrar Lavagem de Itens"));

        listaItensLavaveis = criarListaLavagem();
        JButton btnLavar = new JButton("Lavar Itens Selecionados");

        painelLavagem.add(new JLabel("Selecione os itens para lavar:"), BorderLayout.NORTH);
        painelLavagem.add(new JScrollPane(listaItensLavaveis), BorderLayout.CENTER);
        painelLavagem.add(btnLavar, BorderLayout.SOUTH);

        add(painelLavagem);


        btnRegistrarUso.addActionListener(_ -> {
            Look lookSelecionado = (Look) comboLooks.getSelectedItem();
            String dataTexto = txtData.getText();
            String periodo = (String) comboPeriodo.getSelectedItem();
            String ocasiao = txtOcasiao.getText().trim();

            if (lookSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um look.",
                "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (ocasiao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, defina a ocasião.",
                "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check for borrowed items in the selected look
            for (Item item : lookSelecionado.listarItensDoLook()) {
                if (item != null && item.isEmprestado()) {
                    JOptionPane.showMessageDialog(this, "O look '" + lookSelecionado.getNome() + "' não pode ser usado porque o item '" + item.getNome() + "' está emprestado para '" + item.getParaQuemEstaEmprestado() + "'.",
                    "Erro: Item Emprestado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            LocalDate dataUso;
            try {
                dataUso = LocalDate.parse(dataTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/mm/aaaa.",
                "Erro na Data", JOptionPane.ERROR_MESSAGE);
                return;
            }

            itemDAO.registrarUso(lookSelecionado.listarItensDoLook());
            
            String ocasiaoCompleta = ocasiao + " (" + periodo + ")";
            lookDAO.registrarUsoLook(lookSelecionado, dataUso, ocasiaoCompleta);
            
            JOptionPane.showMessageDialog(this, "Uso do look '" + lookSelecionado.getNome() + "' registrado com sucesso!");
            
            atualizarListaLavagem();
        });

        btnLavar.addActionListener(_ -> {
            List<Item> itensSelecionados = listaItensLavaveis.getSelectedValuesList();
            if (itensSelecionados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um ou mais itens para lavar.",
                "Nenhum item selecionado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Item> itensParaLavar = itensSelecionados.stream()
                .filter(item -> !item.isLimpo())
                .toList();


            if (itensParaLavar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O item selecionado já está limpo.", 
            "", JOptionPane.INFORMATION_MESSAGE);
            return;
    }
            
            itemDAO.registrarLavagem(itensSelecionados);

            JOptionPane.showMessageDialog(this, itensSelecionados.size() + " item(ns) lavado(s) com sucesso!");
            
            atualizarListaLavagem();
        });
    }

    private JComboBox<Look> criarComboLooks() {
        Vector<Look> looksDisponiveis = new Vector<>(lookDAO.listarTodosLooks());
        JComboBox<Look> combo = new JComboBox<>(looksDisponiveis);
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Look) {
                    setText(((Look) value).getNome());
                }
                return this;
            }
        });
        return combo;
    }

    private JList<Item> criarListaLavagem() {
        modeloLista = new DefaultListModel<>();
        JList<Item> lista = new JList<>(modeloLista);
        lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        lista.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Item) {
                    Item item = (Item) value;
                    String status = item.isLimpo() ? " (Limpo)" : " (Sujo)";
                    setText(item.getNome() + status);
                }
                return this;
            }
        });
        
        atualizarListaLavagem(); 
        return lista;
    }

    private void atualizarListaLavagem() {
        modeloLista.clear();
        itemDAO.listarTodosItens().stream()
            .filter(item -> item instanceof ILavavel)
            .forEach(modeloLista::addElement);
    }
}