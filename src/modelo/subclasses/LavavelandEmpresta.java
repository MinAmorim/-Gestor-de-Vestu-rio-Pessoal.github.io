package modelo.subclasses;

import modelo.Item;
import modelo.interfaces.IEmprestavel;
import modelo.interfaces.ILavavel;
import java.time.LocalDate;



public abstract class LavavelandEmpresta extends Item implements IEmprestavel, ILavavel {
    private int quantidadeLavagens;

    private LocalDate dataEmprestimo; // Mudar tipo
    private LocalDate dataDevolucao;
    private boolean isEmprestado;    
    public LavavelandEmpresta (String nome, String cor, String tamanho, String loja, String conservacao){
        super(nome, cor, tamanho, loja, conservacao);
        this.quantidadeLavagens = 0;
        this.isEmprestado = false;
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
    @Override
    public void registrarLavagem(){
        quantidadeLavagens++;
    }
    @Override
    public int getQuantidadeLavagens(){
        return quantidadeLavagens;
    }


    public boolean isEmprestado(){
        return isEmprestado;
    }
    public LocalDate getDataDevolucao(){
    return dataDevolucao;
}

    public int getLavagens(){
        return quantidadeLavagens;
    }

}
