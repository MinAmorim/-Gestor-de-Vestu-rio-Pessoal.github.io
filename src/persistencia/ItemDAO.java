package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.EstadoConservacao;
import modelo.Item;
import modelo.TamanhoCalcado;
import modelo.TamanhoRoupa;
import modelo.interfaces.IEmprestavel;
import modelo.subclasses.Acessorios;
import modelo.subclasses.RoupaIntima;
import modelo.subclasses.roupasdiaadia.Calcado;
import modelo.subclasses.roupasdiaadia.Inferior;
import modelo.subclasses.roupasdiaadia.Superior;

public class ItemDAO {

    public void adicionarItem(Item item) {
        String sql = "INSERT INTO item(categoria, tipo, nome, cor, tamanho, loja, conservacao, totalUsos, totalLavagens, isLimpo, isEmprestado) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String categoria = "";
            if (item instanceof Superior) categoria = "Peça Superior";
            else if (item instanceof Inferior) categoria = "Peça Inferior";
            else if (item instanceof RoupaIntima) categoria = "Roupa Íntima";
            else if (item instanceof Calcado) categoria = "Calçado";
            else if (item instanceof Acessorios) categoria = "Acessório";

            pstmt.setString(1, categoria);
            pstmt.setString(2, item.getTipo());
            pstmt.setString(3, item.getNome());
            pstmt.setString(4, item.getCor());
            pstmt.setString(5, item.getTamanho());
            pstmt.setString(6, item.getLoja());
            pstmt.setString(7, item.getConservacao());
            pstmt.setInt(8, item.getTotalUsos());
            pstmt.setInt(9, item.getContadorLavagem());
            pstmt.setBoolean(10, item.isLimpo());
            pstmt.setBoolean(11, item instanceof IEmprestavel && ((IEmprestavel)item).isEmprestado());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar item: " + e.getMessage());
        }
    }

    private Item criarItemDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String categoria = rs.getString("categoria");
        String tipo = rs.getString("tipo");
        String nome = rs.getString("nome");
        String cor = rs.getString("cor");
        String tamanhoStr = rs.getString("tamanho");
        String loja = rs.getString("loja");
        String conservacaoStr = rs.getString("conservacao").toUpperCase().replace("Ç", "C").replace("É", "E").replace("Ó", "O");
        EstadoConservacao conservacao = EstadoConservacao.valueOf(conservacaoStr);
        
        int totalUsos = rs.getInt("totalUsos");
        int totalLavagens = rs.getInt("totalLavagens");
        boolean isLimpo = rs.getBoolean("isLimpo");
        boolean isEmprestado = rs.getBoolean("isEmprestado");
        String emprestadoPara = rs.getString("emprestado_para");

        Item item = null;

        try {
            switch (categoria) {
                case "Peça Superior":
                case "Peça Inferior":
                case "Roupa Íntima":
                    TamanhoRoupa tr = TamanhoRoupa.valueOf(tamanhoStr);
                    if (categoria.equals("Peça Superior")) item = new Superior(tipo, nome, cor, tr, loja, conservacao);
                    else if (categoria.equals("Peça Inferior")) item = new Inferior(tipo, nome, cor, tr, loja, conservacao);
                    else item = new RoupaIntima(tipo, nome, cor, tr, loja, conservacao);
                    break;
                case "Calçado":
                    TamanhoCalcado tc = TamanhoCalcado.valueOf("T" + tamanhoStr);
                    item = new Calcado(tipo, nome, cor, tc, loja, conservacao);
                    break;
                case "Acessório":
                    item = new Acessorios(tipo, nome, cor, tamanhoStr, loja, conservacao);
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao converter enum para o item '" + nome + "'. Valor no BD: '" + tamanhoStr + "'. Erro: " + e.getMessage());
            return null;
        }

        if (item != null) {
            item.setId(id);
            item.setLimpo(isLimpo);
            item.setTotalUsos(totalUsos);
            item.setContadorLavagem(totalLavagens);
            
            if (item instanceof IEmprestavel && isEmprestado) {
                ((IEmprestavel) item).registrarEmprestimo(emprestadoPara);
            }
        }
        return item;
    }
    

    public List<Item> listarTodosItens() {
        List<Item> itens = new ArrayList<>();
        String sql = "SELECT * FROM item";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Item item = criarItemDoResultSet(rs);
                if (item != null) {
                    itens.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os itens: " + e.getMessage());
        }
        return itens;
    }

    public Item buscarItemPorId(int id) {
        String sql = "SELECT * FROM item WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return criarItemDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar item por ID: " + e.getMessage());
        }
        return null;
    }

    public void removerItemPorId(int id) {
        String sql = "DELETE FROM item WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao remover item: " + e.getMessage());
        }
    }

    public void atualizarItem(Item item) {
        String sql = "UPDATE item SET nome = ?, cor = ?, tamanho = ?, loja = ?, conservacao = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getNome());
            pstmt.setString(2, item.getCor());
            pstmt.setString(3, item.getTamanho());
            pstmt.setString(4, item.getLoja());
            pstmt.setString(5, item.getConservacao());
            pstmt.setInt(6, item.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar item: " + e.getMessage());
        }
    }

    public void atualizarStatusEmprestimo(Item item) {
        if (!(item instanceof IEmprestavel)) return;

        String sql = "UPDATE item SET isEmprestado = ?, dataEmprestimo = ?, emprestado_para = ? WHERE id = ?";
        IEmprestavel itemEmprestavel = (IEmprestavel) item;
        try (Connection conn = ConexaoBD.conectar(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, itemEmprestavel.isEmprestado());

            if (itemEmprestavel.isEmprestado()) {
                pstmt.setString(2, java.time.LocalDate.now().toString());
                pstmt.setString(3, itemEmprestavel.getParaQuemEstaEmprestado());
            } else {
                pstmt.setNull(2, java.sql.Types.VARCHAR);
                pstmt.setNull(3, java.sql.Types.VARCHAR);
            }
            pstmt.setInt(4, item.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status de empréstimo: " + e.getMessage());
        }
    }

    public void registrarLavagem(List<Item> itensParaLavar) {
        String sql = "UPDATE item SET isLimpo = 1, totalLavagens = totalLavagens + 1 WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (Item item : itensParaLavar) {
                if (item instanceof modelo.interfaces.ILavavel) {
                    pstmt.setInt(1, item.getId());
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            System.err.println("Erro ao registrar lavagem: " + e.getMessage());
        }
    }

    public void registrarUso(List<Item> itensUsados) {
        String sql = "UPDATE item SET isLimpo = 0, totalUsos = totalUsos + 1 WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (Item item : itensUsados) {
                pstmt.setInt(1, item.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            System.err.println("Erro ao registrar uso de itens: " + e.getMessage());
        }
    }
}