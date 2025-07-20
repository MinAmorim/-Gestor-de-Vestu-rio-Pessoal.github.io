package modelo.subclasses.acessorios;

import modelo.subclasses.Acessorios;
public class Pulseira extends Acessorios {
    public Pulseira(String nome, String cor, String tamanho, String loja, String conservacao){
        super(nome, cor, tamanho, loja, conservacao);
    }

    @Override
    public String getTipo(){
        return "Pulseira";
    }
    


    }

   