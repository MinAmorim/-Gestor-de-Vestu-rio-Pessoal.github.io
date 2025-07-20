package modelo.subclasses.roupasintimas;

import modelo.subclasses.RoupaIntima;
public class Calcinha extends RoupaIntima {
    public Calcinha(String nome, String cor, String tamanho, String loja, String conservacao){
        super(nome, cor, tamanho, loja, conservacao);
    }

    @Override
    public String getTipo(){
        return "Calcinha";
    }
    


    }

   