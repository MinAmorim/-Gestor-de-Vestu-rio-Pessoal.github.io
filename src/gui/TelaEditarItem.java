package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Item;
import modelo.EstadoConservacao;
import persistencia.ItemDAO;

public class TelaEditarItem extends JDialog {

    private Item itemParaEditar;
    private TelaGerenciarItens telaPai;

    private JTextField txtNome;
    private JTextField txtCor;
    private JTextField txtTamanho;
    private JTextField txtLoja;
    private JComboBox<EstadoConservacao> cmbConservacao;
    private JLabel lblTipoValor;

    public TelaEditarItem(TelaGerenciarItens telaPai, Item item) {
        super(telaPai, "Editar Item", true);
        this.telaPai = telaPai;
        this.itemParaEditar = item;

        setSize(400, 500);
        setLocationRelativeTo(telaPai);
        setLayout(new BorderLayout(10, 10));

        
        JPanel painelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblTipoValor = new JLabel();
        txtNome = new JTextField();
        txtCor = new JTextField();
        txtTamanho = new JTextField();
        txtLoja = new JTextField();
        cmbConservacao = new JComboBox<>(EstadoConservacao.values());

        
        cmbConservacao.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof EstadoConservacao) {
                    setText(((EstadoConservacao) value).getDescricao());
                }
                return this;
            }
        });

        
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
        painelFormulario.add(cmbConservacao);

        painelFormulario.add(new JLabel("")); 
        painelFormulario.add(new JLabel(""));

        add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar Alterações");
        JButton btnCancelar = new JButton("Cancelar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        preencherFormulario();

        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarAlteracoes();
            }
        });

        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });
    }

    private void preencherFormulario() {
        lblTipoValor.setText(itemParaEditar.getTipo());
        txtNome.setText(itemParaEditar.getNome());
        txtCor.setText(itemParaEditar.getCor());
        txtTamanho.setText(itemParaEditar.getTamanho());
        txtLoja.setText(itemParaEditar.getLoja());
        cmbConservacao.setSelectedItem(itemParaEditar.getConservacaoEnum());
    }

    private void salvarAlteracoes() {
        String nome = txtNome.getText().trim();
        String cor = txtCor.getText().trim();
        String tamanho = txtTamanho.getText().trim();
        EstadoConservacao conservacao = (EstadoConservacao) cmbConservacao.getSelectedItem();

        if (nome.isEmpty() || cor.isEmpty() || tamanho.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nome, Cor e Tamanho são campos obrigatórios.",
                "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        itemParaEditar.setNome(nome);
        itemParaEditar.setCor(cor);
        itemParaEditar.setTamanho(tamanho);
        itemParaEditar.setLoja(txtLoja.getText().trim());
        itemParaEditar.setConservacao(conservacao);

        ItemDAO itemDAO = new ItemDAO();
        itemDAO.atualizarItem(itemParaEditar);

        telaPai.atualizarTabela(); 

        dispose(); 

        JOptionPane.showMessageDialog(telaPai,
            "Item atualizado com sucesso!",
            "", JOptionPane.INFORMATION_MESSAGE);
    }
}
