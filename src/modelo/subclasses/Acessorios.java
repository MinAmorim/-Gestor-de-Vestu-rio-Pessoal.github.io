package modelo.subclasses;

import java.time.LocalDate;

import modelo.EstadoConservacao;
import modelo.Item;
import modelo.interfaces.IEmprestavel;

public class Acessorios extends Item implements IEmprestavel {

    private String tipo; 
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean isEmprestado;
    private String paraQuemEstaEmprestado;

    public Acessorios(String tipo, String nome, String cor, String tamanho, String loja, EstadoConservacao conservacao) {
        super(nome, cor, tamanho, loja, conservacao);
        this.tipo = tipo; 
        this.isEmprestado = false;
        this.paraQuemEstaEmprestado = null;
    }

    @Override
    public String getTipo() {
        return this.tipo; 
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
    public boolean isEmprestado() {
        return isEmprestado;
    }
    
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

        @Override
        public String getParaQuemEstaEmprestado(){
            return this.paraQuemEstaEmprestado;
        }

}        