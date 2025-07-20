package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import modelo.Pessoa;
import modelo.Look;
import modelo.Item;
import modelo.subclasses.Acessorios;
import modelo.subclasses.RoupaIntima;
import modelo.subclasses.roupasdiaadia.Calcado;
import modelo.subclasses.roupasdiaadia.Inferior;
import modelo.subclasses.roupasdiaadia.Superior;

public class TelaEditarLook extends JDialog {

    private Pessoa pessoa;
    private Look lookParaEditar;
    private TelaGerenciarLooks telaPai;

    private JTextField txtNomeLook;
    private JComboBox<Superior> comboSuperior;
    private JComboBox<Inferior> comboInferior;
    private JComboBox<Calcado> comboCalcado;
    private JComboBox<Acessorios> comboAcessorios;
    private JComboBox<RoupaIntima> comboRoupaIntima;

    public TelaEditarLook(TelaGerenciarLooks telaPai, Pessoa pessoa, Look look) {
        super(telaPai, "Editar Look", true);
        this.telaPai = telaPai;
        this.pessoa = pessoa;
        this.lookParaEditar = look;

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

        btnSalvar.addActionListener(_ -> salvarAlteracoes());
        btnCancelar.addActionListener(_ -> dispose());
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
        
        pessoa.getGuardaRoupa().stream()
            .filter(tipo::isInstance)
            .map(tipo::cast)
            .forEach(itensFiltrados::add);
            
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
        String nomeLook = txtNomeLook.getText();
        Superior superior = (Superior) comboSuperior.getSelectedItem();
        Inferior inferior = (Inferior) comboInferior.getSelectedItem();
        Calcado calcado = (Calcado) comboCalcado.getSelectedItem();

        if (nomeLook.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do look é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (superior == null || inferior == null || calcado == null) {
            JOptionPane.showMessageDialog(this, "Um look deve ter, no mínimo, peça superior, inferior e calçado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lookParaEditar.setNome(nomeLook);
        lookParaEditar.setSuperior(superior);
        lookParaEditar.setInferior(inferior);
        lookParaEditar.setCalcado(calcado);
        lookParaEditar.setAcessorios((Acessorios) comboAcessorios.getSelectedItem());
        lookParaEditar.setRoupaIntima((RoupaIntima) comboRoupaIntima.getSelectedItem());

        telaPai.atualizarTabela();
        dispose();
        JOptionPane.showMessageDialog(telaPai, "Look '" + nomeLook + "' atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}