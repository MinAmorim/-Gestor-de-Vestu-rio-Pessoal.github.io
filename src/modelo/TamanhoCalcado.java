package modelo;

public enum TamanhoCalcado {
    T25("25"),
    T28("28"),
    T30("30"),
    T32("32"),
    T34("34"),
    T35("35"),
    T36("36"),
    T37("37"),
    T38("38"),
    T39("39"),
    T40("40");

    private final String descricao;

    TamanhoCalcado(String descricao) {
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