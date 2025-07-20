package modelo;

import modelo.subclasses.roupasdiaadia.superior.Camisa;
import modelo.subclasses.roupasdiaadia.inferior.Calca;
import modelo.subclasses.roupasdiaadia.calcado.Sapato;
import modelo.subclasses.roupasintimas.Calcinha;
import modelo.subclasses.acessorios.Relogio;
import modelo.subclasses.Acessorios;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.interfaces.ILavavel;

public class Main {
    public static void main(String[] args) {
        // Ordem: nome, cor, tamanho, loja, conservacao, imagem
        Camisa camisa = new Camisa("Camisa Social", "Branca", "M", "Riachuelo", "Boa");
        Calca calca = new Calca("Calça Jeans Skinny", "Azul", "38", "Renner", "Excelente");
        Sapato sapato = new Sapato("Sapato de Couro", "Preto", "39", "Arezzo", "Regular");
        Calcinha calcinha = new Calcinha("Calcinha de Renda", "Rosa", "P", "LingerieX", "Boa");
        Relogio relogio = new Relogio("Relógio Digital", "Dourado", "Unico", "Casio", "Boa");

        Look look1 = new Look("Look Casual", sapato, calca, camisa);

        System.out.println("Exibindo Look:");
        for(Item item : look1.listarItensDoLook()){
            System.out.println("- " + item.getTipo() + ": " + item.getNome());
        }
        
        Camisa novaCamisa = new Camisa("Camisa Polo", "Preta", "M", "Renner", "Boa");
        look1.modificarLook(novaCamisa);

        System.out.println("\nLook após modificação:");
        for(Item item : look1.listarItensDoLook()){
            System.out.println("- " + item.getTipo() + ": " + item.getNome());
        }

        System.out.println("");
        look1.registrarUso(LocalDate.of(2025, 7, 13), "Festa de aniversário");

        // Registrar lavagem (CÓDIGO CORRIGIDO)
        List<ILavavel> paraLavar = new ArrayList<>();
        paraLavar.add(camisa);
        paraLavar.add(calca);
        paraLavar.add(sapato); // Sapato agora está incluído corretamente
        paraLavar.add(calcinha); // Calcinha também

        System.out.println("\nRegistrando lavagens...");
        for (ILavavel item : paraLavar) {
            item.registrarLavagem();
            if(item instanceof Item){
                System.out.println("- Lavando: " + ((Item) item).getNome());
            }
        }
    }
}