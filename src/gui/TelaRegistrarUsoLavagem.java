package gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Vector;
import modelo.Pessoa;
import modelo.Look;
import modelo.Item;
import modelo.interfaces.ILavavel;
// --- IMPORTAÇÕES ADICIONADAS AQUI ---
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class TelaRegistrarUsoLavagem extends JFrame {

    private Pessoa pessoa;

    public TelaRegistrarUsoLavagem(Pessoa pessoa) {
        this.pessoa = pessoa;

        setTitle("Registrar Uso e Lavagem");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2, 10, 10));

        // --- PAINEL DA ESQUERDA: REGISTRAR USO DE LOOK ---
        JPanel painelUso = new JPanel(new BorderLayout(10, 10));
        painelUso.setBorder(BorderFactory.createTitledBorder("Registrar Uso de Look"));

        JComboBox<Look> comboLooks = criarComboLooks();
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

        // --- PAINEL DA DIREITA: REGISTRAR LAVAGEM DE ITENS ---
        JPanel painelLavagem = new JPanel(new BorderLayout(10, 10));
        painelLavagem.setBorder(BorderFactory.createTitledBorder("Registrar Lavagem de Itens"));

        JList<Item> listaItensLavaveis = criarListaLavagem();
        JButton btnLavar = new JButton("Lavar Itens Selecionados");

        painelLavagem.add(new JLabel("Selecione os itens para lavar:"), BorderLayout.NORTH);
        painelLavagem.add(new JScrollPane(listaItensLavaveis), BorderLayout.CENTER);
        painelLavagem.add(btnLavar, BorderLayout.SOUTH);

        add(painelLavagem);

        // --- AÇÕES DOS BOTÕES ---
        btnRegistrarUso.addActionListener(_ -> {
            Look lookSelecionado = (Look) comboLooks.getSelectedItem();
            String dataTexto = txtData.getText();
            String periodo = (String) comboPeriodo.getSelectedItem();
            String ocasiao = txtOcasiao.getText();

            if (lookSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um look.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (ocasiao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, defina a ocasião.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LocalDate dataUso;
            try {
                dataUso = LocalDate.parse(dataTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/mm/aaaa.", "Erro na Data", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String ocasiaoCompleta = ocasiao + " (" + periodo + ")";
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream old = System.out;
            System.setOut(ps);

            lookSelecionado.registrarUso(dataUso, ocasiaoCompleta);
            
            System.out.flush();
            System.setOut(old);
            
            String mensagemModelo = baos.toString().trim();
            if (!mensagemModelo.isEmpty() && mensagemModelo.contains("⚠️")) {
                 JOptionPane.showMessageDialog(this, mensagemModelo, "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Uso do look '" + lookSelecionado.getNome() + "' registrado com sucesso!");
            }
            
            listaItensLavaveis.repaint();
        });

        btnLavar.addActionListener(_ -> {
            java.util.List<Item> itensSelecionados = listaItensLavaveis.getSelectedValuesList();
            if (itensSelecionados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um ou mais itens para lavar.", "Nenhum item selecionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int itensRealmenteLavados = 0;
            for (Item item : itensSelecionados) {
                if (!item.isLimpo()) {
                    item.registrarLavagem();
                    itensRealmenteLavados++;
                }
            }

            if (itensRealmenteLavados > 0) {
                JOptionPane.showMessageDialog(this, itensRealmenteLavados + " item(ns) lavado(s) com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum item foi lavado, pois todos os selecionados já estavam limpos.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
            
            listaItensLavaveis.repaint(); 
        });
    }

    private JComboBox<Look> criarComboLooks() {
        Vector<Look> looksDisponiveis = new Vector<>(pessoa.getLooks());
        JComboBox<Look> comboLooks = new JComboBox<>(looksDisponiveis);
        comboLooks.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Look) {
                    setText(((Look) value).getNome());
                }
                return this;
            }
        });
        return comboLooks;
    }

    private JList<Item> criarListaLavagem() {
        DefaultListModel<Item> modeloLista = new DefaultListModel<>();
        pessoa.getGuardaRoupa().stream()
            .filter(item -> item instanceof ILavavel)
            .forEach(modeloLista::addElement);

        JList<Item> listaItensLavaveis = new JList<>(modeloLista);
        listaItensLavaveis.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaItensLavaveis.setCellRenderer(new DefaultListCellRenderer() {
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
        return listaItensLavaveis;
    }
}