package modelo.subclasses;

import modelo.EstadoConservacao;
import modelo.Item;
import modelo.TamanhoRoupa;
import modelo.interfaces.ILavavel;

public class RoupaIntima extends Item implements ILavavel {
    
    private String tipo; 
    private int quantidadeLavagens;

    public RoupaIntima(String tipo, String nome, String cor, TamanhoRoupa tamanho, String loja, EstadoConservacao conservacao) {
        super(nome, cor, tamanho.getDescricao(), loja, conservacao);
        this.tipo = tipo; 
        this.quantidadeLavagens = 0;
    }

    @Override
    public void registrarLavagem() {
        quantidadeLavagens++;
    }

    @Override
    public int getQuantidadeLavagens() {
        return quantidadeLavagens;
    }
    
    @Override
    public String getTipo() {
        return this.tipo; 
    }
}