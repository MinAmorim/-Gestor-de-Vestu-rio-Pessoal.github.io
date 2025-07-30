package modelo.subclasses.roupasdiaadia;

import modelo.EstadoConservacao;
import modelo.TamanhoRoupa;
import modelo.subclasses.LavavelandEmpresta;

public class Inferior extends LavavelandEmpresta {
    
    private String tipo; 

    public Inferior(String tipo, String nome, String cor, TamanhoRoupa tamanho, String loja, EstadoConservacao conservacao) {
        super(nome, cor, tamanho.getDescricao(), loja, conservacao);
        this.tipo = tipo;
    }

    @Override
    public String getTipo() {
        return this.tipo; 
}
}
