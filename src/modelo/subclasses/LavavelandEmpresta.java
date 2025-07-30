package modelo.subclasses;

import modelo.EstadoConservacao;
import modelo.Item;
import modelo.interfaces.IEmprestavel;
import modelo.interfaces.ILavavel;
import java.time.LocalDate;



public abstract class LavavelandEmpresta extends Item implements IEmprestavel, ILavavel {
    private int quantidadeLavagens;

    private LocalDate dataEmprestimo; 
    private LocalDate dataDevolucao;
    private boolean isEmprestado;    
    private String paraQuemEstaEmprestado;
    public LavavelandEmpresta (String nome, String cor, String tamanho, String loja, EstadoConservacao conservacao){
        super(nome, cor, tamanho, loja, conservacao);
        this.quantidadeLavagens = 0;
        this.isEmprestado = false;
        this.paraQuemEstaEmprestado = null;

    }

     @Override
    public void registrarEmprestimo(String paraQuem) {
        this.dataEmprestimo = LocalDate.now();
        this.isEmprestado = true;
        this.paraQuemEstaEmprestado = paraQuem;


    }

    @Override
    public void registrarDevolucao() {
        this.dataDevolucao = LocalDate.now();
        this.isEmprestado = false;
        this.paraQuemEstaEmprestado = null;


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
        super.registrarLavagem();
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

        @Override
        public String getParaQuemEstaEmprestado(){
            return this.paraQuemEstaEmprestado;
}

}
