package modelo.subclasses.roupasintimas;

import modelo.subclasses.RoupaIntima;
public class Cueca extends RoupaIntima {
    public Cueca(String nome, String cor, String tamanho, String loja, String conservacao){
        super(nome, cor, tamanho, loja, conservacao);
    }

    @Override
    public String getTipo(){
        return "Cueca";
    }
    


    }

   