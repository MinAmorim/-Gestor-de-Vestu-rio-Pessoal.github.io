package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.interfaces.IEmprestavel;
import modelo.subclasses.Acessorios;
import modelo.subclasses.RoupaIntima;
import modelo.subclasses.roupasdiaadia.Calcado;
import modelo.subclasses.roupasdiaadia.Inferior;
import modelo.subclasses.roupasdiaadia.Superior;

public class Look {
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

    public Acessorios getAcessorios(){
        return acessorios;
    }

    public void setAcessorios(Acessorios acessorios){
        this.acessorios = acessorios;
    }

    public RoupaIntima getRoupaIntima(){
        return roupaIntima;
    }

    public void setRoupaIntima(RoupaIntima roupaIntima){
        this.roupaIntima = roupaIntima;
    }
    


    // Sobrecarga para modificar o look
    public void modificarLook(Superior mudarSuperior) {
        this.superior = mudarSuperior;
    }

    public void modificarLook(Inferior mudarInferior) {
        this.inferior = mudarInferior;
    }

    public void modificarLook(Calcado mudarCalcado) {
        this.calcado = mudarCalcado;
    }

    public void modificarLook(Superior mudarSuperior, Inferior mudarInferior) {
        this.superior = mudarSuperior;
        this.inferior = mudarInferior;
    }

    public void modificarLook(Acessorios mudarAcessorios) {
        this.acessorios = mudarAcessorios;
    }

    public void modificarLook(RoupaIntima mudarRoupaIntima) {
        this.roupaIntima = mudarRoupaIntima;
    }


    // Mudar todo o look
    public void modificarLook(Superior mudarSuperior, Inferior mudarInferior, Calcado mudarCalcado, Acessorios mudarAcessorios, RoupaIntima mudarRoupaIntima) {
        this.superior = mudarSuperior;
        this.inferior = mudarInferior;
        this.calcado = mudarCalcado;
        this.acessorios = mudarAcessorios;
        this.roupaIntima = mudarRoupaIntima;
        
    }

    // Remoção de peças
    public void removerSuperior() {
        this.superior = null;
    }

    public void removerInferior() {
        this.inferior = null;
    }

    public void removerCalcado() {
        this.calcado = null;
    }
    public void removerAcessorios(){
        this.acessorios = null;
    }
    public void removerRoupaIntima(){
        this.roupaIntima = null;
    }

    // Deletar o look completo
    public void deletarLook() {
        this.superior = null;
        this.inferior = null;
        this.calcado = null;
        this.acessorios = null;
        this.roupaIntima = null;
    }

    public List<Item> listarItensDoLook() {
        List<Item> lista = new ArrayList<>();
        if (superior != null) lista.add(superior);
        if (inferior != null) lista.add(inferior);
        if (calcado != null) lista.add(calcado);
        if (acessorios != null) lista.add(acessorios);
        if(roupaIntima != null) lista.add(roupaIntima);
        return lista;
    }

    
    public void registrarUso(LocalDate data, String ocasiao) {
        if (superior != null && superior instanceof IEmprestavel && ((IEmprestavel) superior).isEmprestado()) {
            System.out.println("Não foi possível registrar o uso: a peça superior '" + superior.getNome() + "' está emprestada.");
            return;
        }

        if (inferior != null && inferior instanceof IEmprestavel && ((IEmprestavel) inferior).isEmprestado()) {
            System.out.println("Não foi possível registrar o uso: a peça inferior '" + inferior.getNome() + "' está emprestada.");
            return;
        }

        if (calcado != null && calcado instanceof IEmprestavel && ((IEmprestavel) calcado).isEmprestado()) {
            System.out.println("Não foi possível registrar o uso: o calçado '" + calcado.getNome() + "' está emprestado.");
            return;
        }

        if (acessorios != null && acessorios instanceof IEmprestavel && ((IEmprestavel) acessorios).isEmprestado()) {
            System.out.println("Não foi possível registrar o uso: o calçado '" + acessorios.getNome() + "' está emprestado.");
            return;
        }




        if ((superior != null && !superior.isLimpo()) ||
            (inferior != null && !inferior.isLimpo()) ||
            (calcado != null && !calcado.isLimpo()) ||
            (acessorios != null && !acessorios.isLimpo()) ||
            (roupaIntima != null && !roupaIntima.isLimpo())) {
            System.out.println("⚠️ Não é possível usar o look: um ou mais itens estão sujos (sem lavagem).");
            return;
        }


        if (this.superior != null) {
            this.superior.registrarUso();
        }
        if (this.inferior != null) {
            this.inferior.registrarUso();
        }
        if (this.calcado != null) {
            this.calcado.registrarUso();
        }
        if (this.acessorios != null){
            this.acessorios.registrarUso();
        }
        if(this.roupaIntima != null){
            this.roupaIntima.registrarUso();
        }

        RegistrarUso uso = new RegistrarUso(ocasiao, data);
        usosRegistrados.add(uso);
        System.out.println("Uso do look '" + this.nome + "' registrado com sucesso para a data " + data + ".");
    }

    
    public void exibirUsosDoLook() {
        if (usosRegistrados.isEmpty()) {
            System.out.println("Este look ainda não foi usado.");
        } else {
            System.out.println("Usos registrados para o look '" + nome + "':");
            for (RegistrarUso uso : usosRegistrados) {
                System.out.println("- Em " + uso.getData() + " | Ocasião: " + uso.getDescricao());
            }
        }
    }
    
    
    public int getTotalUsos() {
        return this.usosRegistrados.size();
    }
}