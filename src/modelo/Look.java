package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.subclasses.Acessorios;
import modelo.subclasses.RoupaIntima;
import modelo.subclasses.roupasdiaadia.Calcado;
import modelo.subclasses.roupasdiaadia.Inferior;
import modelo.subclasses.roupasdiaadia.Superior;

public class Look {
    private int id;
    private String nome;
    private Calcado calcado;
    private Inferior inferior;
    private Superior superior;
    private RoupaIntima roupaIntima;
    private Acessorios acessorios;
    private List<RegistrarUso> usosRegistrados = new ArrayList<>();

    public Look(String nome, Calcado calcado, Inferior inferior, Superior superior) {
        this.nome = nome;
        this.calcado = calcado;
        this.inferior = inferior;
        this.superior = superior;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calcado getCalcado() {
        return calcado;
    }

    public void setCalcado(Calcado calcado) {
        this.calcado = calcado;
    }

    public Inferior getInferior() {
        return inferior;
    }

    public void setInferior(Inferior inferior) {
        this.inferior = inferior;
    }

    public Superior getSuperior() {
        return superior;
    }

    public void setSuperior(Superior superior) {
        this.superior = superior;
    }

    public Acessorios getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(Acessorios acessorios) {
        this.acessorios = acessorios;
    }

    public RoupaIntima getRoupaIntima() {
        return roupaIntima;
    }

    public void setRoupaIntima(RoupaIntima roupaIntima) {
        this.roupaIntima = roupaIntima;
    }

    public List<Item> listarItensDoLook() {
        List<Item> lista = new ArrayList<>();
        if (superior != null) lista.add(superior);
        if (inferior != null) lista.add(inferior);
        if (calcado != null) lista.add(calcado);
        if (acessorios != null) lista.add(acessorios);
        if (roupaIntima != null) lista.add(roupaIntima);
        return lista;
    }

    public void registrarUso(LocalDate data, String ocasiao){

    }

    

    public int getTotalUsos() {
        return this.usosRegistrados.size();
    }
    public List<RegistrarUso> getUsosRegistrados() {
        return usosRegistrados;
    }

    public void setUsosRegistrados(List<RegistrarUso> usosRegistrados) {
        this.usosRegistrados = usosRegistrados;
    }
}