package gui;

import javax.swing.*;
import java.awt.*;
import modelo.Item;

public class TelaEditarItem extends JDialog {

    private Item itemParaEditar;
    private TelaGerenciarItens telaPai;

    // Campos do formulário
    private JTextField txtNome;
    private JTextField txtCor;
    private JTextField txtTamanho;
    private JTextField txtLoja;
    private JTextField txtConservacao;
    private JTextField txtImagem;
    private JLabel lblTipoValor; // Label para mostrar o tipo, que não pode ser mudado

    public TelaEditarItem(TelaGerenciarItens telaPai, Item item) {
        super(telaPai, "Editar Item", true);
        this.telaPai = telaPai;
        this.itemParaEditar = item;

        setSize(400, 500);
        setLocationRelativeTo(telaPai);
        setLayout(new BorderLayout(10, 10));

        // --- Painel do Formulário ---
        JPanel painelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos do formulário
        lblTipoValor = new JLabel();
        txtNome = new JTextField();
        txtCor = new JTextField();
        txtTamanho = new JTextField();
        txtLoja = new JTextField();
        txtConservacao = new JTextField();
        txtImagem = new JTextField();

        // Adiciona os componentes ao painel
        painelFormulario.add(new JLabel("Tipo de Item:"));
        painelFormulario.add(lblTipoValor);
        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(txtNome);
        painelFormulario.add(new JLabel("Cor:"));
        painelFormulario.add(txtCor);
        painelFormulario.add(new JLabel("Tamanho:"));
        painelFormulario.add(txtTamanho);
        painelFormulario.add(new JLabel("Loja:"));
        painelFormulario.add(txtLoja);
        painelFormulario.add(new JLabel("Conservação:"));
        painelFormulario.add(txtConservacao);
        painelFormulario.add(new JLabel("Imagem (nome do arquivo):"));
        painelFormulario.add(txtImagem);
        
        add(painelFormulario, BorderLayout.CENTER);

        // --- Botões de Ação ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar Alterações");
        JButton btnCancelar = new JButton("Cancelar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        // Preenche o formulário com os dados do item existente
        preencherFormulario();

        // --- Ações dos Botões ---
        btnSalvar.addActionListener(_ -> salvarAlteracoes());
        btnCancelar.addActionListener(_ -> dispose());
    }

    /**
     * Preenche os campos do formulário com os dados do item a ser editado.
     */
    private void preencherFormulario() {
        lblTipoValor.setText(itemParaEditar.getTipo());
        txtNome.setText(itemParaEditar.getNome());
        txtCor.setText(itemParaEditar.getCor());
        txtTamanho.setText(itemParaEditar.getTamanho());
        txtLoja.setText(itemParaEditar.getLoja());
        txtConservacao.setText(itemParaEditar.getConservacao());
    }

    /**
     * Salva as alterações feitas no formulário de volta para o objeto Item.
     */
    private void salvarAlteracoes() {
        // 1. Coletar os dados dos campos
        String nome = txtNome.getText();
        String cor = txtCor.getText();
        String tamanho = txtTamanho.getText();

        // 2. Validar se os campos obrigatórios não estão vazios
        if (nome.isEmpty() || cor.isEmpty() || tamanho.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome, Cor e Tamanho são campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Atualizar o objeto Item existente com os novos dados
        itemParaEditar.setNome(nome);
        itemParaEditar.setCor(cor);
        itemParaEditar.setTamanho(tamanho);
        itemParaEditar.setLoja(txtLoja.getText());
        itemParaEditar.setConservacao(txtConservacao.getText());

        // 4. Avisar a tela pai para atualizar a tabela
        telaPai.atualizarTabela();
        
        // 5. Fechar a janela de edição
        dispose();
        JOptionPane.showMessageDialog(telaPai, "Item atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}