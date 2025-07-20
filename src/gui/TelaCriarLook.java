package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import modelo.Pessoa;
import modelo.Look;
import modelo.subclasses.Acessorios;
import modelo.subclasses.RoupaIntima;
import modelo.subclasses.roupasdiaadia.Calcado;
import modelo.subclasses.roupasdiaadia.Inferior;
import modelo.subclasses.roupasdiaadia.Superior;

public class TelaCriarLook extends JDialog {

    private Pessoa pessoa;
    private TelaGerenciarLooks telaPai;

    // Campos do formulário
    private JTextField txtNomeLook;
    private JComboBox<Superior> comboSuperior;
    private JComboBox<Inferior> comboInferior;
    private JComboBox<Calcado> comboCalcado;
    private JComboBox<Acessorios> comboAcessorios;
    private JComboBox<RoupaIntima> comboRoupaIntima;

    public TelaCriarLook(TelaGerenciarLooks telaPai, Pessoa pessoa) {
        super(telaPai, "Criar Novo Look", true);
        this.telaPai = telaPai;
        this.pessoa = pessoa;

        setSize(500, 400);
        setLocationRelativeTo(telaPai);
        setLayout(new BorderLayout(10, 10));

        // --- Painel do Formulário ---
        JPanel painelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Inicialização dos Componentes ---
        txtNomeLook = new JTextField();
        
        // Criando e populando os JComboBox
        comboSuperior = criarComboBox(Superior.class);
        comboInferior = criarComboBox(Inferior.class);
        comboCalcado = criarComboBox(Calcado.class);
        comboAcessorios = criarComboBox(Acessorios.class);
        comboRoupaIntima = criarComboBox(RoupaIntima.class);

        // Adicionando componentes ao painel
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

        // --- Botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar Look");
        JButton btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- Ações ---
        btnSalvar.addActionListener(_ -> salvarLook());
        btnCancelar.addActionListener(_ -> dispose());
    }

    /**
     * Método genérico para criar e popular um JComboBox com itens de uma classe específica.
     */
    private <T> JComboBox<T> criarComboBox(Class<T> tipo) {
        Vector<T> itensFiltrados = new Vector<>();
        // Adiciona uma opção nula para "Nenhum"
        itensFiltrados.add(null); 
        
        pessoa.getGuardaRoupa().stream()
            .filter(tipo::isInstance)
            .map(tipo::cast)
            .forEach(itensFiltrados::add);
            
        JComboBox<T> comboBox = new JComboBox<>(itensFiltrados);
        
        // Customiza como o objeto é exibido no ComboBox
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText("Nenhum");
                } else {
                    setText(((modelo.Item) value).getNome());
                }
                return this;
            }
        });

        return comboBox;
    }

    private void salvarLook() {
        String nomeLook = txtNomeLook.getText();
        Superior superior = (Superior) comboSuperior.getSelectedItem();
        Inferior inferior = (Inferior) comboInferior.getSelectedItem();
        Calcado calcado = (Calcado) comboCalcado.getSelectedItem();

        // Validação Mínima: Nome do look e pelo menos as 3 peças principais
        if (nomeLook.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do look é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (superior == null || inferior == null || calcado == null) {
            JOptionPane.showMessageDialog(this, "Um look deve ter, no mínimo, peça superior, inferior e calçado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criando o look
        Look novoLook = new Look(nomeLook, calcado, inferior, superior);
        
        // Adicionando peças opcionais
        novoLook.setAcessorios((Acessorios) comboAcessorios.getSelectedItem());
        novoLook.setRoupaIntima((RoupaIntima) comboRoupaIntima.getSelectedItem());
        
        // Adicionando o look à lista da pessoa
        pessoa.adicionarLook(novoLook);

        // Atualiza a tabela da tela pai e fecha
        telaPai.atualizarTabela();
        dispose();
        JOptionPane.showMessageDialog(telaPai, "Look '" + nomeLook + "' criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}