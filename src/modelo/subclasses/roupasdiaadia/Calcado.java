package modelo.subclasses.roupasdiaadia;

import modelo.EstadoConservacao;
import modelo.TamanhoCalcado;
import modelo.subclasses.LavavelandEmpresta;

public class Calcado extends LavavelandEmpresta {
    
    private String tipo; 

    public Calcado(String tipo, String nome, String cor, TamanhoCalcado tamanho, String loja, EstadoConservacao conservacao) {
        super(nome, cor, tamanho.getDescricao(), loja, conservacao);
        this.tipo = tipo;
    }

    @Override
    public String getTipo() {
        return this.tipo; 
    }
}