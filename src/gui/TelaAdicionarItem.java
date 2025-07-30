package gui;

import javax.swing.*;
import java.awt.*;
import modelo.Pessoa;
// Importe as classes de CATEGORIA agora
import modelo.subclasses.Acessorios;
import modelo.subclasses.RoupaIntima;
import modelo.subclasses.roupasdiaadia.Calcado;
import modelo.subclasses.roupasdiaadia.Inferior;
import modelo.subclasses.roupasdiaadia.Superior;

public class TelaAdicionarItem extends JDialog {

    private Pessoa pessoa;
    private TelaGerenciarItens telaPai;

    // Campos do formulário atualizados
    private JComboBox<String> comboCategoria; // NOVO: para escolher a categoria
    private JTextField txtTipo;             // NOVO: para escrever o tipo
    private JTextField txtNome;
    private JTextField txtCor;
    private JTextField txtTamanho;
    private JTextField txtLoja;
    private JTextField txtConservacao;
    private JTextField txtImagem;

    public TelaAdicionarItem(TelaGerenciarItens telaPai, Pessoa pessoa) {
        super(telaPai, "Adicionar Novo Item", true);
        this.telaPai = telaPai;
        this.pessoa = pessoa;

        setSize(400, 550); // Aumentar um pouco a altura
        setLocationRelativeTo(telaPai);
        setLayout(new BorderLayout(10, 10));

        JPanel painelFormulario = new JPanel(new GridLayout(8, 2, 5, 5)); // 8 linhas agora
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Lista de categorias disponíveis
        String[] categorias = {"Peça Superior", "Peça Inferior", "Calçado", "Acessório", "Roupa Íntima"};
        comboCategoria = new JComboBox<>(categorias);
        
        txtTipo = new JTextField();
        txtNome = new JTextField();
        txtCor = new JTextField();
        txtTamanho = new JTextField();
        txtLoja = new JTextField();
        txtConservacao = new JTextField();
        txtImagem = new JTextField();

        painelFormulario.add(new JLabel("Categoria do Item:"));
        painelFormulario.add(comboCategoria);
        painelFormulario.add(new JLabel("Tipo Específico (ex: Camisa):"));
        painelFormulario.add(txtTipo);
        painelFormulario.add(new JLabel("Nome/Descrição:"));
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

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(_ -> salvarItem());
        btnCancelar.addActionListener(_ -> dispose());
    }

    private void salvarItem() {
        // Coleta dos dados
        String categoria = (String) comboCategoria.getSelectedItem();
        String tipo = txtTipo.getText();
        String nome = txtNome.getText();
        String cor = txtCor.getText();
        String tamanho = txtTamanho.getText();
        String loja = txtLoja.getText();
        String conservacao = txtConservacao.getText();
        String imagem = txtImagem.getText();

        if (tipo.isEmpty() || nome.isEmpty() || cor.isEmpty() || tamanho.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tipo, Nome, Cor e Tamanho são campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cria o objeto da CATEGORIA correta, passando o TIPO como texto
        switch (categoria) {
            case "Peça Superior":
                pessoa.adicionarItem(new Superior(tipo, nome, cor, tamanho, loja, conservacao));
                break;
            case "Peça Inferior":
                pessoa.adicionarItem(new Inferior(tipo, nome, cor, tamanho, loja, conservacao));
                break;
            case "Calçado":
                pessoa.adicionarItem(new Calcado(tipo, nome, cor, tamanho, loja, conservacao));
                break;
            case "Acessório":
                pessoa.adicionarItem(new Acessorios(tipo, nome, cor, tamanho, loja, conservacao));
                break;
            case "Roupa Íntima":
                pessoa.adicionarItem(new RoupaIntima(tipo, nome, cor, tamanho, loja, conservacao));
                break;
        }

        telaPai.atualizarTabela();
        dispose();
        JOptionPane.showMessageDialog(telaPai, "Item salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}