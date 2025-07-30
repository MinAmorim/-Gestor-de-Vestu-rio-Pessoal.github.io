package modelo;

public enum EstadoConservacao {
    OTIMA("ótima"),
    BOA("boa"),
    REGULAR("regular"),
    RUIM("ruim"),
    PESSIMA("péssima");

    private final String descricao;

    EstadoConservacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}