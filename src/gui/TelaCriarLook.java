package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import modelo.Look;
import modelo.Item;
import modelo.interfaces.IEmprestavel;
import modelo.subclasses.Acessorios;
import modelo.subclasses.RoupaIntima;
import modelo.subclasses.roupasdiaadia.Calcado;
import modelo.subclasses.roupasdiaadia.Inferior;
import modelo.subclasses.roupasdiaadia.Superior;
import persistencia.ItemDAO;
import persistencia.LookDAO;

public class TelaCriarLook extends JDialog {

    private TelaGerenciarLooks telaPai;
    private ItemDAO itemDAO;

    private JTextField txtNomeLook;
    private JComboBox<Superior> comboSuperior;
    private JComboBox<Inferior> comboInferior;
    private JComboBox<Calcado> comboCalcado;
    private JComboBox<Acessorios> comboAcessorios;
    private JComboBox<RoupaIntima> comboRoupaIntima;

    public TelaCriarLook(TelaGerenciarLooks telaPai) {
        super(telaPai, "Criar Novo Look", true);
        this.telaPai = telaPai;
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
        JButton btnSalvar = new JButton("Salvar Look");
        JButton btnCancelar = new JButton("Cancelar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        add(painelBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                salvarLook();
            }
        });

        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
    }

    private <T extends Item> JComboBox<T> criarComboBox(Class<T> tipo) {
        Vector<T> itens = new Vector<>();
        itens.add(null);

        for (Item item : itemDAO.listarTodosItens()) {
            if (tipo.isInstance(item)) {
                itens.add(tipo.cast(item));
            }
        }

        JComboBox<T> comboBox = new JComboBox<>(itens);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "Nenhum" : ((Item) value).getNome());
                return this;
            }
        });

        return comboBox;
    }

    private void salvarLook() {
        String nomeLook = txtNomeLook.getText().trim();
        Superior superior = (Superior) comboSuperior.getSelectedItem();
        Inferior inferior = (Inferior) comboInferior.getSelectedItem();
        Calcado calcado = (Calcado) comboCalcado.getSelectedItem();
        Acessorios acessorio = (Acessorios) comboAcessorios.getSelectedItem();
        RoupaIntima roupaIntima = (RoupaIntima) comboRoupaIntima.getSelectedItem();

        if (nomeLook.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Você precisa preencher o nome do look.",
                "", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (superior == null || inferior == null || calcado == null) {
            JOptionPane.showMessageDialog(this,
                "Seu look precisa ter pelo menos uma roupa inferior, uma superior e um calçado.",
                "", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Item[] itens = {superior, inferior, calcado, acessorio, roupaIntima};

        for (Item item : itens) {
            if (item != null) {
                if (!item.isLimpo()) {
                    JOptionPane.showMessageDialog(this,
                        "O item '" + item.getNome() + "' está sujo",
                        "", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (item instanceof IEmprestavel && ((IEmprestavel) item).isEmprestado()) {
                    JOptionPane.showMessageDialog(this,
                        "O item '" + item.getNome() + "' já foi emprestado",
                        "", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }

        LookDAO lookDAO = new LookDAO();

        if (lookDAO.nomeDeLookJaExiste(nomeLook)) {
            JOptionPane.showMessageDialog(this,
                "Já existe um look com esse nome.",
                "", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Look novoLook = new Look(nomeLook, calcado, inferior, superior);
        novoLook.setAcessorios(acessorio);
        novoLook.setRoupaIntima(roupaIntima);

        boolean sucesso = lookDAO.adicionarLook(novoLook);

        if (sucesso) {
            telaPai.carregarLooks(); 
            dispose();
            JOptionPane.showMessageDialog(telaPai,
                "Look '" + nomeLook + "' criado com sucesso!",
                "", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Não conseguimos salvar o look agora. Tente novamente mais tarde.",
                "", JOptionPane.ERROR_MESSAGE);
        }
    }
}
