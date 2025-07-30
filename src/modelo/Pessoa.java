package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private List<Item> guardaRoupa;
    private List<Look> looks;

    public Pessoa(String nome) {
        this.nome = nome;
        this.guardaRoupa = new ArrayList<>();
        this.looks = new ArrayList<>();
    }

    public void adicionarItem(Item item) {
        this.guardaRoupa.add(item);
    }

    public void removerItem(int index) {
        if (index >= 0 && index < this.guardaRoupa.size()) {
            this.guardaRoupa.remove(index);
        }
    }

    public void adicionarLook(Look look) {
        this.looks.add(look);
    }

    public void removerLook(Look look) {
        this.looks.remove(look);
    }
    
    public void removerLook(int index) {
        if (index >= 0 && index < this.looks.size()) {
            this.looks.remove(index);
        }
    }


    public String getNome() {
        return nome;
    }

    public List<Item> getGuardaRoupa() {
        return guardaRoupa;
    }

    public List<Look> getLooks() {
        return looks;
    }
}