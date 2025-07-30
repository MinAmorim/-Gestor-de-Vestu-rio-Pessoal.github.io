package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Pessoa;
import modelo.Look;
// --- IMPORTAÇÕES ATUALIZADAS ---
// Importamos as classes de categoria, não mais as específicas
import modelo.subclasses.roupasdiaadia.Calcado;
import modelo.subclasses.roupasdiaadia.Inferior;
import modelo.subclasses.roupasdiaadia.Superior;

public class TelaPrincipal extends JFrame {

    private Pessoa pessoa;
    private static final Color COR_FUNDO = new Color(255, 223, 229);
    private static final Color COR_BOTAO_FUNDO = Color.WHITE;
    private static final Color COR_TEXTO_BOTAO = new Color(255, 105, 180);
    private static final Font FONTE_TITULO = new Font("SansSerif", Font.BOLD, 32);
    private static final Font FONTE_BOTAO = new Font("SansSerif", Font.BOLD, 18);

    public TelaPrincipal() {
        this.pessoa = new Pessoa("Meu Guarda-Roupa");
        carregarDadosDeExemplo();

        setTitle("Guarda-Roupa Virtual");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new GridBagLayout());
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

        JPanel painelBotoes = new JPanel(new GridLayout(5, 1, 0, 15));
        painelBotoes.setOpaque(false);

        String[] textosBotoes = {
            "° Gerenciar Itens",
            "° Gerenciar Looks",
            "° Registrar Uso/Lavagem",
            "° Visualizar Estatísticas",
            "° Sair"
        };

        for (String texto : textosBotoes) {
            JButton botao = criarBotaoEstilizado(texto);
            final String acao = texto.substring(2);

            botao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (acao) {
                        case "Gerenciar Itens":
                            TelaGerenciarItens telaItens = new TelaGerenciarItens(pessoa);
                            telaItens.setVisible(true);
                            break;
                        case "Gerenciar Looks":
                            TelaGerenciarLooks telaLooks = new TelaGerenciarLooks(pessoa);
                            telaLooks.setVisible(true);
                            break;
                        case "Registrar Uso/Lavagem":
                            TelaRegistrarUsoLavagem telaRegistro = new TelaRegistrarUsoLavagem(pessoa);
                            telaRegistro.setVisible(true);
                            break;
                        case "Visualizar Estatísticas":
                            TelaEstatisticas telaEstatisticas = new TelaEstatisticas(pessoa);
                            telaEstatisticas.setVisible(true);
                            break;
                        case "Sair":
                            System.exit(0);
                            break;
                    }
                }
            });
            painelBotoes.add(botao);
        }

        gbc.gridy = 1;
        gbc.weighty = 0.8;
        add(painelBotoes, gbc);

        JLabel lblRodape = new JLabel("Desenvolvido por [Seu Nome]");
        lblRodape.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblRodape.setForeground(Color.DARK_GRAY);
        gbc.gridy = 2;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(lblRodape, gbc);
    }

    private JButton criarBotaoEstilizado(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(COR_BOTAO_FUNDO);
        botao.setForeground(COR_TEXTO_BOTAO);
        botao.setFont(FONTE_BOTAO);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        return botao;
    }

    /**
     * Carrega dados de exemplo usando os novos construtores generalizados.
     */
    private void carregarDadosDeExemplo() {
        // --- MÉTODO ATUALIZADO ---
        // Agora criamos um objeto 'Superior' e dizemos que o seu tipo é "Camisa"
        Superior camisa = new Superior("Camisa", "Camisa Social Branca", "Branca", "M", "Riachuelo", "Boa");
        camisa.registrarUso();
        camisa.registrarUso();
        camisa.registrarLavagem();
        
        // Criamos um objeto 'Inferior' e dizemos que o seu tipo é "Calça Jeans"
        Inferior calca = new Inferior("Calça Jeans", "Jeans Skinny", "Azul", "40", "Renner", "Ótima");
        calca.registrarUso();

        // Criamos um objeto 'Calcado' e dizemos que o seu tipo é "Tênis"
        Calcado tenis = new Calcado("Tênis", "Tênis de Corrida", "Preto", "41", "Nike", "Nova");
        tenis.registrarUso();
        tenis.registrarUso();
        tenis.registrarUso();
        
        pessoa.adicionarItem(camisa);
        pessoa.adicionarItem(calca);
        pessoa.adicionarItem(tenis);

        Look lookCasual = new Look("Look do Dia a Dia", tenis, calca, camisa);
        pessoa.adicionarLook(lookCasual);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}