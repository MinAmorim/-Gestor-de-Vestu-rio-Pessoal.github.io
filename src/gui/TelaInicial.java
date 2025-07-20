package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame {

    private static final Color COR_FUNDO = new Color(255, 182, 193); // Rosa claro (similar ao da imagem)
    private static final Color COR_TEXTO = Color.BLACK;
    private static final Color COR_BOTAO_FUNDO = Color.BLACK;
    private static final Color COR_BOTAO_TEXTO = Color.WHITE;
    private static final Font FONTE_TEXTO = new Font("Arial", Font.PLAIN, 48); // Pode ajustar a fonte
    private static final Font FONTE_BOTAO = new Font("Arial", Font.BOLD, 20);

    public TelaInicial() {
        setTitle("Jogos Para Meninas.com");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.5; // Espaço acima do texto

        // --- Texto Principal ---
        JLabel textoPrincipal = new JLabel("JOGOS PARA");
        textoPrincipal.setFont(FONTE_TEXTO);
        textoPrincipal.setForeground(COR_TEXTO);
        add(textoPrincipal, gbc);

        gbc.gridy++;
        JLabel textoSecundario = new JLabel("MENINAS.COM");
        textoSecundario.setFont(FONTE_TEXTO);
        textoSecundario.setForeground(COR_TEXTO);
        add(textoSecundario, gbc);

        // --- Botão Iniciar ---
        JButton btnIniciar = new JButton("Iniciar");
        btnIniciar.setBackground(COR_BOTAO_FUNDO);
        btnIniciar.setForeground(COR_BOTAO_TEXTO);
        btnIniciar.setFont(FONTE_BOTAO);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Padding no botão
        btnIniciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Mãozinha ao passar o mouse

        // Adiciona um ActionListener para ir para a próxima tela
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaPrincipal telaPrincipal = new TelaPrincipal();
                telaPrincipal.setVisible(true);
                TelaInicial.this.dispose(); // Fecha a tela inicial
            }
        });

        gbc.gridy++;
        gbc.weighty = 0.5; // Espaço abaixo do botão
        add(btnIniciar, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}