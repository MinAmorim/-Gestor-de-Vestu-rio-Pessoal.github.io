package modelo.subclasses;

import modelo.Item;
import modelo.interfaces.ILavavel;

public abstract class RoupaIntima extends Item implements ILavavel{
    private int quantidadeLavagens;
    public RoupaIntima (String nome, String cor, String tamanho, String loja, String conservacao){
    super(nome, cor, tamanho, loja, conservacao);
    this.quantidadeLavagens = 0;
}
@Override
public void registrarLavagem(){
    quantidadeLavagens++;
}
@Override
public int getQuantidadeLavagens(){
    return quantidadeLavagens;
}
}

