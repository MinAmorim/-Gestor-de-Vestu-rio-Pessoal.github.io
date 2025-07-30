package modelo.subclasses.roupasdiaadia;

import modelo.subclasses.LavavelandEmpresta;

// A classe agora é pública e não mais abstrata
public class Calcado extends LavavelandEmpresta {
    
    private String tipo; // Novo campo para guardar o tipo específico (ex: "Camisa", "Vestido")

    // O construtor agora recebe o 'tipo'
    public Calcado(String tipo, String nome, String cor, String tamanho, String loja, String conservacao) {
        super(nome, cor, tamanho, loja, conservacao);
        this.tipo = tipo;
    }

    @Override
    public String getTipo() {
        return this.tipo; 
    }
}