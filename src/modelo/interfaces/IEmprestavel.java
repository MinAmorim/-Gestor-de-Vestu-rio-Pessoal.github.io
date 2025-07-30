package modelo.interfaces;

public interface IEmprestavel {
    boolean isEmprestado();
    void setEmprestado(boolean emprestado);
    void registrarEmprestimo(String paraQuem);
    void registrarDevolucao();
    long quantidadeDeDiasDesdeOEmprestimo();
    String getParaQuemEstaEmprestado();
    void setParaQuemEstaEmprestado(String paraQuemEstaEmprestado);

}
