package modelo.subclasses.roupasdiaadia.superior;

import modelo.subclasses.roupasdiaadia.Superior;
public class Regata extends Superior {
    public Regata(String nome, String cor, String tamanho, String loja, String conservacao){
        super(nome, cor, tamanho, loja, conservacao);
    }

    @Override
    public String getTipo(){
        return "Regata";
    }
    


    }

   