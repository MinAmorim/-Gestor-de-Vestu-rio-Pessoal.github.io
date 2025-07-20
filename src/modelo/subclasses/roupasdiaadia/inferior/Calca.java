package modelo.subclasses.roupasdiaadia.inferior;

import modelo.subclasses.roupasdiaadia.Inferior;
public class Calca extends Inferior {
    public Calca(String nome, String cor, String tamanho, String loja, String conservacao){
        super(nome, cor, tamanho, loja, conservacao);
    }

    @Override
    public String getTipo(){
        return "Calca";
    }
    


    }

   
   