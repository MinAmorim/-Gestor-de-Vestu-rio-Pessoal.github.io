package gui;

import javax.swing.*;
import java.awt.*;
import modelo.Pessoa;
// Importe todas as suas classes de itens concretos
import modelo.subclasses.acessorios.*;
import modelo.subclasses.roupasdiaadia.calcado.*;
import modelo.subclasses.roupasdiaadia.inferior.*;
import modelo.subclasses.roupasdiaadia.superior.*;
import modelo.subclasses.roupasintimas.*;

public class TelaAdicionarItem extends JDialog {

    private Pessoa pessoa;
    private TelaGerenciarItens telaPai; // Referência para a tela que a chamou

    // Campos do formulário
    private JComboBox<String> comboTipo;
    private JTextField txtNome;
    private JTextField txtCor;
    private JTextField txtTamanho;
    private JTextField txtLoja;
    private JTextField txtConservacao;
    private JTextField txtImagem;

    public TelaAdicionarItem(TelaGerenciarItens telaPai, Pessoa pessoa) {
        super(telaPai, "Adicionar Novo Item", true); // O 'true' torna o diálogo modal
        this.telaPai = telaPai;
        this.pessoa = pessoa;

        setSize(400, 500);
        setLocationRelativeTo(telaPai);
        setLayout(new BorderLayout(10, 10));

        // --- Painel do Formulário ---
        JPanel painelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tipos de itens disponíveis para seleção
        String[] tipos = {
            "Camisa", "Regata", "Calca", "Bermuda", "Sapato",
            "Relogio", "Pulseira", "Calcinha", "Cueca"
        };
        comboTipo = new JComboBox<>(tipos);
        txtNome = new JTextField();
        txtCor = new JTextField();
        txtTamanho = new JTextField();
        txtLoja = new JTextField();
        txtConservacao = new JTextField();
        txtImagem = new JTextField();

        painelFormulario.add(new JLabel("Tipo de Item:"));
        painelFormulario.add(comboTipo);
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
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- Ações dos Botões ---
        btnSalvar.addActionListener(_ -> salvarItem());
        btnCancelar.addActionListener(_ -> dispose()); // 'dispose' fecha o JDialog
    }

    private void salvarItem() {
        // 1. Coletar os dados dos campos
        String tipo = (String) comboTipo.getSelectedItem();
        String nome = txtNome.getText();
        String cor = txtCor.getText();
        String tamanho = txtTamanho.getText();
        String loja = txtLoja.getText();
        String conservacao = txtConservacao.getText();

        // 2. Validar se os campos não estão vazios (validação simples)
        if (nome.isEmpty() || cor.isEmpty() || tamanho.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome, Cor e Tamanho são campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Criar o objeto do item correto com base no tipo selecionado
        switch (tipo) {
            case "Camisa":
                pessoa.adicionarItem(new Camisa(nome, cor, tamanho, loja, conservacao));
                break;
            case "Regata":
                pessoa.adicionarItem(new Regata(nome, cor, tamanho, loja, conservacao));
                break;
            case "Calca":
                pessoa.adicionarItem(new Calca(nome, cor, tamanho, loja, conservacao));
                break;
            case "Bermuda":
                pessoa.adicionarItem(new Bermuda(nome, cor, tamanho, loja, conservacao));
                break;
            case "Sapato":
                pessoa.adicionarItem(new Sapato(nome, cor, tamanho, loja, conservacao));
                break;
            case "Relogio":
                pessoa.adicionarItem(new Relogio(nome, cor, tamanho, loja, conservacao));
                break;
            case "Pulseira":
                pessoa.adicionarItem(new Pulseira(nome, cor, tamanho, loja, conservacao));
                break;
            case "Calcinha":
                pessoa.adicionarItem(new Calcinha(nome, cor, tamanho, loja, conservacao));
                break;
            case "Cueca":
                pessoa.adicionarItem(new Cueca(nome, cor, tamanho, loja, conservacao));
                break;
        }

        // 4. Avisar a tela pai para atualizar a tabela
        telaPai.atualizarTabela();
        
        // 5. Fechar a janela de adicionar item
        dispose();
        JOptionPane.showMessageDialog(telaPai, "Item salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}