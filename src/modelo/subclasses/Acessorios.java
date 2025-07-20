package modelo.subclasses;

import java.time.LocalDate;

import modelo.Item;
import modelo.interfaces.IEmprestavel;

public abstract class Acessorios extends Item implements IEmprestavel{

    private LocalDate dataEmprestimo; // Mudar tipo
    private LocalDate dataDevolucao;
    private boolean isEmprestado;

    public Acessorios (String nome, String cor, String tamanho, String loja, String conservacao){
    super(nome, cor, tamanho, loja, conservacao);
}
    @Override
    public void registrarEmprestimo() {
        this.dataEmprestimo = LocalDate.now();
        this.isEmprestado = true;

    }

    @Override
    public void registrarDevolucao() {
        this.dataDevolucao = LocalDate.now();
        this.isEmprestado = false;


    }

    @Override
    public long quantidadeDeDiasDesdeOEmprestimo() {
        if (dataEmprestimo == null) return 0;

        LocalDate hoje = LocalDate.now();

        int anoAtual = hoje.getYear();
        int mesAtual = hoje.getMonthValue();
        int diaAtual = hoje.getDayOfMonth();

        int anoEmp = dataEmprestimo.getYear();
        int mesEmp = dataEmprestimo.getMonthValue();
        int diaEmp = dataEmprestimo.getDayOfMonth();


        int diasAno = (anoAtual - anoEmp) * 365;
        int diasMes = (mesAtual - mesEmp) * 30;
        int diasDia = diaAtual - diaEmp;

        return diasAno + diasMes + diasDia;

    }

public boolean isEmprestado(){
    return isEmprestado;
}
public LocalDate getDataEmprestimo(){
    return dataEmprestimo;
}
public LocalDate getDataDevolucao(){
    return dataDevolucao;
}
}