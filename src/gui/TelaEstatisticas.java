package gui;

import javax.swing.*;
import java.awt.*;
import estatisticas.Estatisticas;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TelaEstatisticas extends JFrame {
    private static final Color COR_FUNDO = new Color(255, 223, 229);


    private Estatisticas estatisticas;

    public TelaEstatisticas() { 
        this.estatisticas = new Estatisticas();

        setTitle("Estatísticas do Guarda-Roupa");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COR_FUNDO);
        


        JLabel lblTitulo = new JLabel("Relatórios e Estatísticas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        JTextArea areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaResultados.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(areaResultados), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnVoltar = new JButton("Voltar");
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnVoltar.addActionListener(_ -> dispose());

        gerarRelatorio(areaResultados);
    }

    
    private void gerarRelatorio(JTextArea area) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out; 
        System.setOut(ps);

        estatisticas.visualizarItensMaisUsados();
        estatisticas.visualizarItensMaisLavados();
        estatisticas.visualizarLookMaisUsado();
        estatisticas.visualizarItensEmprestados();
        estatisticas.visualizarTotalDeLavagens();
        
        System.out.flush();
        System.setOut(old);

        area.setText(baos.toString());
    }
}