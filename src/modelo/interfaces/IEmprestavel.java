package modelo.interfaces;

public interface IEmprestavel {
    boolean isEmprestado();
    void registrarEmprestimo();
    void registrarDevolucao();
    long quantidadeDeDiasDesdeOEmprestimo();

}
