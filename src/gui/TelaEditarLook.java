package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import modelo.Look;
import modelo.Item;
import modelo.subclasses.Acessorios;
import modelo.subclasses.RoupaIntima;
import modelo.subclasses.roupasdiaadia.Calcado;
import modelo.subclasses.roupasdiaadia.Inferior;
import modelo.subclasses.roupasdiaadia.Superior;
import persistencia.ItemDAO;
import persistencia.LookDAO;

public class TelaEditarLook extends JDialog {

    private Look lookParaEditar;
    private TelaGerenciarLooks telaPai;
    private ItemDAO itemDAO;

    private JTextField txtNomeLook;
    private JComboBox<Superior> comboSuperior;
    private JComboBox<Inferior> comboInferior;
    private JComboBox<Calcado> comboCalcado;
    private JComboBox<Acessorios> comboAcessorios;
    private JComboBox<RoupaIntima> comboRoupaIntima;

    public TelaEditarLook(TelaGerenciarLooks telaPai, Look look) {
        super(telaPai, "Editar Look", true);
        this.telaPai = telaPai;
        this.lookParaEditar = look;
        this.itemDAO = new ItemDAO();

        setSize(500, 400);
        setLocationRelativeTo(telaPai);
        setLayout(new BorderLayout(10, 10));

        JPanel painelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtNomeLook = new JTextField();
        comboSuperior = criarComboBox(Superior.class);
        comboInferior = criarComboBox(Inferior.class);
        comboCalcado = criarComboBox(Calcado.class);
        comboAcessorios = criarComboBox(Acessorios.class);
        comboRoupaIntima = criarComboBox(RoupaIntima.class);

        painelFormulario.add(new JLabel("Nome do Look:"));
        painelFormulario.add(txtNomeLook);
        painelFormulario.add(new JLabel("Peça Superior:"));
        painelFormulario.add(comboSuperior);
        painelFormulario.add(new JLabel("Peça Inferior:"));
        painelFormulario.add(comboInferior);
        painelFormulario.add(new JLabel("Calçado:"));
        painelFormulario.add(comboCalcado);
        painelFormulario.add(new JLabel("Acessório:"));
        painelFormulario.add(comboAcessorios);
        painelFormulario.add(new JLabel("Roupa Íntima:"));
        painelFormulario.add(comboRoupaIntima);

        add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar Alterações");
        JButton btnCancelar = new JButton("Cancelar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        preencherFormulario();

        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                salvarAlteracoes();
            }
        });

        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
    }

    private void preencherFormulario() {
        txtNomeLook.setText(lookParaEditar.getNome());
        comboSuperior.setSelectedItem(lookParaEditar.getSuperior());
        comboInferior.setSelectedItem(lookParaEditar.getInferior());
        comboCalcado.setSelectedItem(lookParaEditar.getCalcado());
        comboAcessorios.setSelectedItem(lookParaEditar.getAcessorios());
        comboRoupaIntima.setSelectedItem(lookParaEditar.getRoupaIntima());
    }

    private <T extends Item> JComboBox<T> criarComboBox(Class<T> tipo) {
        Vector<T> itensFiltrados = new Vector<>();
        itensFiltrados.add(null);

        for (Item item : itemDAO.listarTodosItens()) {
            if (tipo.isInstance(item)) {
                itensFiltrados.add(tipo.cast(item));
            }
        }

        JComboBox<T> comboBox = new JComboBox<>(itensFiltrados);

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText("Nenhum");
                } else {
                    setText(((Item) value).getNome());
                }
                return this;
            }
        });
        return comboBox;
    }

    private void salvarAlteracoes() {
        String nomeLook = txtNomeLook.getText().trim();
        Superior superior = (Superior) comboSuperior.getSelectedItem();
        Inferior inferior = (Inferior) comboInferior.getSelectedItem();
        Calcado calcado = (Calcado) comboCalcado.getSelectedItem();

        if (nomeLook.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do look é obrigatório.",
            "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (superior == null || inferior == null || calcado == null) {
            JOptionPane.showMessageDialog(this, "Um look deve ter no mínimo  uma peça superior, inferior e calçado.",
            "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lookParaEditar.setNome(nomeLook);
        lookParaEditar.setSuperior(superior);
        lookParaEditar.setInferior(inferior);
        lookParaEditar.setCalcado(calcado);
        lookParaEditar.setAcessorios((Acessorios) comboAcessorios.getSelectedItem());
        lookParaEditar.setRoupaIntima((RoupaIntima) comboRoupaIntima.getSelectedItem());

        LookDAO lookDAO = new LookDAO();
        lookDAO.atualizarLook(lookParaEditar);

        telaPai.carregarLooks(); 
        dispose();
        JOptionPane.showMessageDialog(telaPai, "Look '" + nomeLook + "' atualizado com sucesso!",
        "", JOptionPane.INFORMATION_MESSAGE);
    }
}
