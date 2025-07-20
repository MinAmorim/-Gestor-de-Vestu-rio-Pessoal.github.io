package estatisticas;

import java.util.ArrayList;
import java.util.List;
import modelo.Item;
import modelo.Look;
import modelo.interfaces.IEmprestavel;
import modelo.interfaces.ILavavel;

/**
 * Versão com a lógica original mantida e novas funcionalidades adicionadas.
 */
public class Estatisticas {

    /**
     * Mostra uma lista dos itens, começando pelo mais usado.
     */
    public void visualizarItensMaisUsados(List<Item> todosOsItens) {
        List<Item> copiaDaLista = new ArrayList<>(todosOsItens);
        List<Item> listaOrdenada = new ArrayList<>();

        while (!copiaDaLista.isEmpty()) {
            Item itemMaisUsado = copiaDaLista.get(0);
            for (Item itemAtual : copiaDaLista) {
                if (itemAtual.getTotalUsos() > itemMaisUsado.getTotalUsos()) {
                    itemMaisUsado = itemAtual;
                }
            }
            listaOrdenada.add(itemMaisUsado);
            copiaDaLista.remove(itemMaisUsado);
        }
        
        System.out.println("--- Itens por Ordem de Utilização ---");
        for(Item item : listaOrdenada) {
            System.out.println("- " + item.getNome() + " (Usado " + item.getTotalUsos() + " vezes)");
        }
    }

    /**
     * Calcula a SOMA de todas as lavagens de todos os itens.
     */
    public void visualizarTotalDeLavagens(List<Item> todosOsItens) {
        int totalLavagens = 0;
        for (Item item : todosOsItens) {
            // Usando o método padronizado getQuantidadeLavagens()
            totalLavagens += item.getQuantidadeLavagens();
        }
        System.out.println("Total de lavagens de todos os itens: " + totalLavagens);
    }

    /**
     * Conta e MOSTRA QUAIS itens estão emprestados.
     */
    public void visualizarItensEmprestados(List<Item> todosOsItens) {
        List<Item> itensEmprestados = new ArrayList<>();
        for (Item item : todosOsItens) {
            if (item instanceof IEmprestavel) {
                IEmprestavel itemEmprestavel = (IEmprestavel) item;
                if (itemEmprestavel.isEmprestado()) {
                    itensEmprestados.add(item);
                }
            }
        }

        System.out.println("--- Itens Emprestados Atualmente (" + itensEmprestados.size() + ") ---");
        if (itensEmprestados.isEmpty()) {
            System.out.println("Nenhum item emprestado no momento.");
        } else {
            for (Item item : itensEmprestados) {
                System.out.println("- " + item.getNome());
            }
        }
    }

    /**
     * Encontra e exibe o look mais utilizado.
     */
    public void visualizarLookMaisUsado(List<Look> todosOsLooks) {
        if (todosOsLooks == null || todosOsLooks.isEmpty()){
            System.out.println("Nenhum look para analisar.");
            return;
        }
        
        Look lookMaisUsado = todosOsLooks.get(0);
        int maxUsos = lookMaisUsado.getTotalUsos();

        for (Look look : todosOsLooks) {
            if (look.getTotalUsos() > maxUsos) {
                lookMaisUsado = look;
                maxUsos = look.getTotalUsos();
            }
        }
        System.out.println("Look mais usado: " + lookMaisUsado.getNome() + " (usado " + maxUsos + " vezes)");
    }
    
    /**
     * Calcula a SOMA de todas as utilizações de itens e looks.
     */
    public void visualizarQuantidadeDeUtilizacoes(List<Item> todosOsItens, List<Look> todosOsLooks) {
        int totalItens = 0;
        int totalLooks = 0;

        for (Item item : todosOsItens) {
            totalItens += item.getTotalUsos();
        }

        for (Look look : todosOsLooks) {
            totalLooks += look.getTotalUsos();
        }

        System.out.println("Soma total de utilizações de itens: " + totalItens);
        System.out.println("Soma total de utilizações de looks: " + totalLooks);
    }
    
    // --- MÉTODO NOVO ADICIONADO ---
    
    /**
     * NOVO: Mostra uma lista dos itens, começando pelo mais lavado.
     * @param todosOsItens A lista de itens a serem analisados.
     */
    public void visualizarItensMaisLavados(List<Item> todosOsItens) {
        List<Item> copiaDaLista = new ArrayList<>(todosOsItens);
        List<Item> listaOrdenada = new ArrayList<>();

        // Mesma lógica do "mais usados", mas para lavagens
        while (!copiaDaLista.isEmpty()) {
            Item itemMaisLavado = null;
            
            // Encontra o primeiro item lavável para iniciar a comparação
            for(Item item : copiaDaLista){
                if(item instanceof ILavavel){
                    itemMaisLavado = item;
                    break;
                }
            }

            // Se não encontrar nenhum item lavável, para o loop
            if(itemMaisLavado == null) break;


            // Procura na lista por um item que tenha sido mais lavado
            for (Item itemAtual : copiaDaLista) {
                if (itemAtual.getQuantidadeLavagens() > itemMaisLavado.getQuantidadeLavagens()) {
                    itemMaisLavado = itemAtual;
                }
            }

            listaOrdenada.add(itemMaisLavado);
            copiaDaLista.remove(itemMaisLavado);
        }
        
        System.out.println("--- Itens por Ordem de Lavagens ---");
        for(Item item : listaOrdenada) {
            System.out.println("- " + item.getNome() + " (Lavado " + item.getQuantidadeLavagens() + " vezes)");
        }
    }
}