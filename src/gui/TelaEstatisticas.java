package gui;

import javax.swing.*;
import java.awt.*;
import modelo.Pessoa;
import estatisticas.Estatisticas;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TelaEstatisticas extends JFrame {

    private Pessoa pessoa;
    private Estatisticas estatisticas;

    public TelaEstatisticas(Pessoa pessoa) {
        this.pessoa = pessoa;
        this.estatisticas = new Estatisticas();

        setTitle("Estatísticas do Guarda-Roupa");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Título ---
        JLabel lblTitulo = new JLabel("Relatórios e Estatísticas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        // --- Área de Texto para os Resultados ---
        JTextArea areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaResultados.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(areaResultados), BorderLayout.CENTER);

        // --- Botão para Voltar ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnVoltar = new JButton("Voltar");
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- Ações ---
        btnVoltar.addActionListener(_ -> dispose());

        // --- Gerar e Exibir as Estatísticas ---
        gerarRelatorio(areaResultados);
    }

    /**
     * Captura a saída do console dos métodos da classe Estatisticas e a exibe no JTextArea.
     * @param area O JTextArea onde o relatório será exibido.
     */
    private void gerarRelatorio(JTextArea area) {
        // Redireciona a saída padrão (System.out) para um buffer em memória
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out; // Salva a saída original do console
        System.setOut(ps);

        // Chama todos os métodos de estatísticas. A saída deles irá para o buffer.
        estatisticas.visualizarItensMaisUsados(pessoa.getGuardaRoupa());
        area.append("\n\n"); // Adiciona um espaçamento
        estatisticas.visualizarItensMaisLavados(pessoa.getGuardaRoupa());
        area.append("\n\n");
        estatisticas.visualizarLookMaisUsado(pessoa.getLooks());
        area.append("\n\n");
        estatisticas.visualizarItensEmprestados(pessoa.getGuardaRoupa());
        area.append("\n\n");
        estatisticas.visualizarTotalDeLavagens(pessoa.getGuardaRoupa());
        area.append("\n\n");
        estatisticas.visualizarQuantidadeDeUtilizacoes(pessoa.getGuardaRoupa(), pessoa.getLooks());
        
        // Restaura a saída original do console
        System.out.flush();
        System.setOut(old);

        // Define o texto do JTextArea com o conteúdo capturado do buffer
        area.setText(baos.toString());
    }
}