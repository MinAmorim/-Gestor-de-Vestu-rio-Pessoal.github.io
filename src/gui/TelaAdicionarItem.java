package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.*;
import modelo.subclasses.Acessorios;
import modelo.subclasses.RoupaIntima;
import modelo.subclasses.roupasdiaadia.*;
import persistencia.ItemDAO;

public class TelaAdicionarItem extends JDialog {

    private TelaGerenciarItens telaPai;

    private JComboBox<String> cmbCategoria;
    private JTextField txtTipo, txtNome, txtCor, txtLoja, txtTamAcessorio;
    private JComboBox<TamanhoRoupa> tamRoupa;
    private JComboBox<TamanhoCalcado> tamCalcado;
    private JComboBox<EstadoConservacao> cmbConservacao;
    private JPanel painelTamanho;
    private CardLayout layoutTamanho;

    public TelaAdicionarItem(TelaGerenciarItens telaPai) {
        super(telaPai, "Adicionar Item", true);
        this.telaPai = telaPai;

        setSize(400, 500);
        setLocationRelativeTo(telaPai);
        setLayout(new BorderLayout());

        
        JPanel form = new JPanel(new GridLayout(8, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] categorias = {"Peça Superior", "Peça Inferior", "Roupa Íntima", "Calçado", "Acessório"};
        cmbCategoria = new JComboBox<>(categorias);

        txtTipo = new JTextField();
        txtNome = new JTextField();
        txtCor = new JTextField();
        txtLoja = new JTextField();
        cmbConservacao = new JComboBox<>(EstadoConservacao.values());

        layoutTamanho = new CardLayout();
        painelTamanho = new JPanel(layoutTamanho);

        tamRoupa = new JComboBox<>(TamanhoRoupa.values());
        tamCalcado = new JComboBox<>(TamanhoCalcado.values());
        txtTamAcessorio = new JTextField("Único");

        painelTamanho.add(tamRoupa, "ROUPA");
        painelTamanho.add(tamCalcado, "CALCADO");
        painelTamanho.add(txtTamAcessorio, "ACESSORIO");

        
        cmbCategoria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTamanho();
            }
        });

       
        form.add(new JLabel("Categoria:"));
        form.add(cmbCategoria);
        form.add(new JLabel("Tipo:"));
        form.add(txtTipo);
        form.add(new JLabel("Nome:"));
        form.add(txtNome);
        form.add(new JLabel("Cor:"));
        form.add(txtCor);
        form.add(new JLabel("Tamanho:"));
        form.add(painelTamanho);
        form.add(new JLabel("Loja:"));
        form.add(txtLoja);
        form.add(new JLabel("Conservação:"));
        form.add(cmbConservacao);

        add(form, BorderLayout.CENTER);

        //  sao os botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        botoes.add(btnSalvar);
        botoes.add(btnCancelar);
        add(botoes, BorderLayout.SOUTH);

        
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvar();
            }
        });

        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        atualizarTamanho(); 
    }

    private void atualizarTamanho() {
        String categoriaSelecionada = (String) cmbCategoria.getSelectedItem();

        
        if (categoriaSelecionada.equals("Calçado")) {
            layoutTamanho.show(painelTamanho, "CALCADO");
        } else if (categoriaSelecionada.equals("Acessório")) {
            layoutTamanho.show(painelTamanho, "ACESSORIO");
        } else {
            layoutTamanho.show(painelTamanho, "ROUPA");
        }
    }

    private void salvar() {
        String cat = (String) cmbCategoria.getSelectedItem();
        String tipo = txtTipo.getText().trim();
        String nome = txtNome.getText().trim();
        String cor = txtCor.getText().trim();
        String loja = txtLoja.getText().trim();
        EstadoConservacao estado = (EstadoConservacao) cmbConservacao.getSelectedItem();

        // esses sao os campos obrigatorios para poder adicionar um item
        if (tipo.isEmpty() || nome.isEmpty() || cor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha Tipo, Nome e Cor!");
            return;
        }

        Item item = null;

        
        if (cat.equals("Peça Superior")) {
            item = new Superior(tipo, nome, cor, (TamanhoRoupa) tamRoupa.getSelectedItem(), loja, estado);
        } else if (cat.equals("Peça Inferior")) {
            item = new Inferior(tipo, nome, cor, (TamanhoRoupa) tamRoupa.getSelectedItem(), loja, estado);
        } else if (cat.equals("Roupa Íntima")) {
            item = new RoupaIntima(tipo, nome, cor, (TamanhoRoupa) tamRoupa.getSelectedItem(), loja, estado);
        } else if (cat.equals("Calçado")) {
            item = new Calcado(tipo, nome, cor, (TamanhoCalcado) tamCalcado.getSelectedItem(), loja, estado);
        } else if (cat.equals("Acessório")) {
            item = new Acessorios(tipo, nome, cor, txtTamAcessorio.getText().trim(), loja, estado);
        }

        if (item != null) {
            ItemDAO dao = new ItemDAO();
            dao.adicionarItem(item);
            telaPai.atualizarTabela();

            JOptionPane.showMessageDialog(this, "Item salvo com sucesso :)");
            dispose();
        }
    }
}
