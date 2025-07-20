package modelo.subclasses.roupasdiaadia.inferior;

import modelo.subclasses.roupasdiaadia.Inferior;
public class Bermuda extends Inferior {
    public Bermuda(String nome, String cor, String tamanho, String loja, String conservacao){
        super(nome, cor, tamanho, loja, conservacao);
    }

    @Override
    public String getTipo(){
        return "Bermuda";
    }
    


    }

   