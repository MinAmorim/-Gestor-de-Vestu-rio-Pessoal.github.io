package modelo;

import java.time.LocalDate;

/**
 * Classe simples para armazenar os dados de uma utilização de Look.
 * Segue o padrão de ser um objeto de dados (POJO).
 */
public class UtilizacaoLook {
    private final LocalDate data;
    private final String periodo; // Ex: "Manhã", "Tarde", "Noite"
    private final String ocasiao; // Ex: "Aniversário da Maria", "Trabalho"

    public UtilizacaoLook(LocalDate data, String periodo, String ocasiao) {
        this.data = data;
        this.periodo = periodo;
        this.ocasiao = ocasiao;
    }

    // Getters para acessar os dados
    public LocalDate getData() {
        return data;
    }

    public String getPeriodo() {
        return periodo;
    }

    public String getOcasiao() {
        return ocasiao;
    }

    @Override
    public String toString() {
        return "Usado em: " + data + " (" + periodo + ") para " + ocasiao;
    }
}