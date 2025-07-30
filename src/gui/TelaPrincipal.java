package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import persistencia.ConexaoBD;

public class TelaPrincipal extends JFrame {

    private static final Color COR_FUNDO = new Color(255, 223, 229);
    private static final Color COR_BOTAO_FUNDO = Color.WHITE;
    private static final Color COR_TEXTO_BOTAO = new Color(255, 105, 180);
    private static final Font FONTE_TITULO = new Font("SansSerif", Font.BOLD, 32);
    private static final Font FONTE_BOTAO = new Font("SansSerif", Font.BOLD, 18);

    public TelaPrincipal() {
        // aqui inicializa o banco de dados 
        ConexaoBD.inicializarBanco();
        
        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setTitle("Guarda-Roupa Virtual");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new GridBagLayout());
    }

    private void inicializarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 30, 15, 30);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Guarda-Roupa Virtual");
        lblTitulo.setFont(FONTE_TITULO);
        lblTitulo.setForeground(COR_TEXTO_BOTAO);
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        JPanel painelBotoes = criarPainelDeBotoes();
        gbc.gridy = 1;
        gbc.weighty = 0.8;
        add(painelBotoes, gbc);
    }

    private JPanel criarPainelDeBotoes() {
        JPanel painelBotoes = new JPanel(new GridLayout(6, 1, 0, 15));
        painelBotoes.setOpaque(false);

        painelBotoes.add(criarBotaoMenu("° Gerenciar Itens", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirGerenciarItens();
            }
        }));

        painelBotoes.add(criarBotaoMenu("° Gerenciar Looks", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirGerenciarLooks();
            }
        }));

        painelBotoes.add(criarBotaoMenu("° Gerenciar Empréstimos", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirGerenciarEmprestimos();
            }
        }));

        painelBotoes.add(criarBotaoMenu("° Registrar Uso/Lavagem", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirRegistrarUsoLavagem();
            }
        }));

        painelBotoes.add(criarBotaoMenu("° Visualizar Estatísticas", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirVisualizarEstatisticas();
            }
        }));

        painelBotoes.add(criarBotaoMenu("° Sair", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sair();
            }
        }));

        return painelBotoes;
    }

    private JButton criarBotaoMenu(String texto, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setBackground(COR_BOTAO_FUNDO);
        botao.setForeground(COR_TEXTO_BOTAO);
        botao.setFont(FONTE_BOTAO);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.addActionListener(acao);
        return botao;
    }

    
    private void abrirGerenciarItens() {
        new TelaGerenciarItens().setVisible(true);
    }

    private void abrirGerenciarLooks() {
        new TelaGerenciarLooks().setVisible(true);
    }

    private void abrirGerenciarEmprestimos() {
        new TelaGerenciarEmprestimos().setVisible(true);
    }

    private void abrirRegistrarUsoLavagem() {
        new TelaRegistrarUsoLavagem().setVisible(true);
    }

    private void abrirVisualizarEstatisticas() {
        new TelaEstatisticas().setVisible(true);
    }

    private void sair() {
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaInicial().setVisible(true);
            }
        });
    }
}
