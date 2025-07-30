package estatisticas;

import java.util.ArrayList;
import java.util.List;
import modelo.Item;
import modelo.Look;
import modelo.interfaces.IEmprestavel;
import persistencia.ItemDAO; 
import persistencia.LookDAO;

public class Estatisticas {

    private ItemDAO itemDAO;
    private LookDAO lookDAO;

    public Estatisticas() {
        itemDAO = new ItemDAO();
        lookDAO = new LookDAO();
    }

    public void visualizarItensMaisUsados() {
        List<Item> todosItens = itemDAO.listarTodosItens();

        // isso aqui é [para eu ordenar os itens
        for (int i = 0; i < todosItens.size() - 1; i++) {
            for (int j = i + 1; j < todosItens.size(); j++) {
                if (todosItens.get(j).getTotalUsos() > todosItens.get(i).getTotalUsos()) {
                    Item temp = todosItens.get(i);
                    todosItens.set(i, todosItens.get(j));
                    todosItens.set(j, temp);
                }
            }
        }

        System.out.println("\n============================");
        System.out.println("      ITENS MAIS USADOS     ");
        System.out.println("============================\n");

        for (Item item : todosItens) {
            System.out.println("- " + item.getNome() + " (Usado " + item.getTotalUsos() + " vezes)");
        }
    }

    public void visualizarTotalDeLavagens() {
        List<Item> todosItens = itemDAO.listarTodosItens();
        int totalLavagens = 0;
        for (Item item : todosItens) {
            totalLavagens += item.getContadorLavagem();
        }
        System.out.println("Total de lavagens: " + totalLavagens);
    }

    public void visualizarItensEmprestados() {
        List<Item> todosItens = itemDAO.listarTodosItens();
        List<Item> itensEmprestados = new ArrayList<>();

        for (Item item : todosItens) {
            if (item instanceof IEmprestavel) {
                IEmprestavel emprestavel = (IEmprestavel) item;
                if (emprestavel.isEmprestado()) {
                    itensEmprestados.add(item);
                }
            }
        }

        System.out.println("\n--- Itens emprestados (" + itensEmprestados.size() + ") ---");
        if (itensEmprestados.size() == 0) {
            System.out.println("Nenhum item emprestado.");
        } else {
            for (Item item : itensEmprestados) {
                System.out.println("- " + item.getNome());
            }
        }
    }

    public void visualizarLookMaisUsado() {
        List<Look> todosLooks = lookDAO.listarTodosLooks();
        if (todosLooks == null || todosLooks.size() == 0) {
            System.out.println("Nenhum look para analisar.");
            return;
        }

        Look lookMaisUsado = todosLooks.get(0);
        for (int i = 1; i < todosLooks.size(); i++) {
            if (todosLooks.get(i).getTotalUsos() > lookMaisUsado.getTotalUsos()) {
                lookMaisUsado = todosLooks.get(i);
            }
        }

        System.out.println("Look mais usado: " + lookMaisUsado.getNome() + " (usado " + lookMaisUsado.getTotalUsos() + " vezes)");
    }

    public void visualizarItensMaisLavados() {
        List<Item> todosItens = itemDAO.listarTodosItens();

        
        for (int i = 0; i < todosItens.size() - 1; i++) {
            for (int j = i + 1; j < todosItens.size(); j++) {
                if (todosItens.get(j).getContadorLavagem() > todosItens.get(i).getContadorLavagem()) {
                    Item temp = todosItens.get(i);
                    todosItens.set(i, todosItens.get(j));
                    todosItens.set(j, temp);
                }
            }
        }
        System.out.println("\n============================");
        System.out.println("\n ITENS MAIS LAVADOS         ");
        System.out.println("\n============================");


        for (Item item : todosItens) {
            if (item instanceof modelo.interfaces.ILavavel) {
                System.out.println("- " + item.getNome() + " (Lavado " + item.getContadorLavagem() + " vezes)");
            }
        }
    }
}
