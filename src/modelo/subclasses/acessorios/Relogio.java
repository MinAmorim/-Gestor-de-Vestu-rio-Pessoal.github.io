package modelo.subclasses.acessorios;

import modelo.subclasses.Acessorios;
public class Relogio extends Acessorios {
    public Relogio(String nome, String cor, String tamanho, String loja, String conservacao){
        super(nome, cor, tamanho, loja, conservacao);
    }

    @Override
    public String getTipo(){
        return "Relogio";
    }
    


    }

   