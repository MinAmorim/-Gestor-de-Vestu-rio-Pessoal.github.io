package modelo;

import java.time.LocalDate;


public class RegistrarUso {
    private String descricao;
    private LocalDate data;

    public RegistrarUso(String descricao, LocalDate data) {
        this.descricao = descricao;
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }
}

    
