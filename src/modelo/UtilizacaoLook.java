package modelo;

import java.time.LocalDate;


public class UtilizacaoLook {
    private final LocalDate data;
    private final String periodo; 
    private final String ocasiao; 

    public UtilizacaoLook(LocalDate data, String periodo, String ocasiao) {
        this.data = data;
        this.periodo = periodo;
        this.ocasiao = ocasiao;
    }

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