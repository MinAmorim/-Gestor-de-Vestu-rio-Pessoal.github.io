package modelo.subclasses.roupasdiaadia;

import modelo.subclasses.LavavelandEmpresta;

// A classe agora é pública e não mais abstrata
public class Superior extends LavavelandEmpresta {
    
    private String tipo; // Novo campo para guardar o tipo específico (ex: "Camisa", "Vestido")

    // O construtor agora recebe o 'tipo'
    public Superior(String tipo, String nome, String cor, String tamanho, String loja, String conservacao) {
        super(nome, cor, tamanho, loja, conservacao);
        this.tipo = tipo;
    }

    @Override
    public String getTipo() {
        return this.tipo; // Retorna o tipo que o utilizador definiu
    }
}