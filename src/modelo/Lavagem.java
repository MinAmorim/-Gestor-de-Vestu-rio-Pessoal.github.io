package modelo;

import modelo.interfaces.ILavavel;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;


public class Lavagem {
    private final String id;
    private final LocalDate data;
    private final List<ILavavel> itensLavados;

    public Lavagem(List<ILavavel> itens) {
        this.id = UUID.randomUUID().toString();
        this.data = LocalDate.now();
        this.itensLavados = itens;
    }

    public String getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public List<ILavavel> getItensLavados() {
        return Collections.unmodifiableList(itensLavados);
    }
}