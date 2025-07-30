package modelo;

public enum TamanhoRoupa {
    PP("PP"),
    P("P"),
    M("M"),
    G("G"),
    GG("GG");

    private final String descricao;

    TamanhoRoupa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao; 
    }
}