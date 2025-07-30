package persistencia;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import modelo.Item;
import modelo.Look;
import modelo.RegistrarUso; 
import modelo.subclasses.Acessorios;
import modelo.subclasses.RoupaIntima;
import modelo.subclasses.roupasdiaadia.Calcado;
import modelo.subclasses.roupasdiaadia.Inferior;
import modelo.subclasses.roupasdiaadia.Superior;

public class LookDAO {

    public boolean adicionarLook(Look look) {
        Connection conn = null;
        try {
            conn = ConexaoBD.conectar();
            if (conn == null) return false;

            conn.setAutoCommit(false);

            // Inserir o look
            String sqlLook = "INSERT INTO look(nome) VALUES(?)";
            PreparedStatement psLook = conn.prepareStatement(sqlLook, Statement.RETURN_GENERATED_KEYS);
            psLook.setString(1, look.getNome());
            psLook.executeUpdate();

            ResultSet rs = psLook.getGeneratedKeys();
            if (rs.next()) {
                int idLook = rs.getInt(1);
                look.setId(idLook); // Set the ID back to the look object

                String sqlItem = "INSERT INTO look_item(look_id, item_id, funcao_item) VALUES(?,?,?)";
                PreparedStatement psItem = conn.prepareStatement(sqlItem);

                for (Item i : look.listarItensDoLook()) {
                    if (i != null) {
                        psItem.setInt(1, idLook);
                        psItem.setInt(2, i.getId());
                        psItem.setString(3, descobrirFuncao(i));
                        psItem.addBatch();
                    }
                }

                psItem.executeBatch();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            System.out.println("Erro: " + e.getMessage());
            return false;

        } finally {
            try { if (conn != null) conn.close(); } catch (Exception ex) {}
        }
    }

    public List<Look> listarTodosLooks() {
        List<Look> lista = new ArrayList<>();
        Map<Integer, Look> mapa = new HashMap<>();
        ItemDAO itemDAO = new ItemDAO();

        try (Connection conn = ConexaoBD.conectar()) {
            String sql = "SELECT l.id as idLook, l.nome as nomeLook, li.item_id, li.funcao_item " +
                         "FROM look l LEFT JOIN look_item li ON l.id = li.look_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idLook = rs.getInt("idLook");

                if (!mapa.containsKey(idLook)) {
                    Look look = new Look(rs.getString("nomeLook"), null, null, null);
                    look.setId(idLook);
                    mapa.put(idLook, look);
                }

                int idItem = rs.getInt("item_id");
                String funcao = rs.getString("funcao_item");

                if (idItem > 0) {
                    Item item = itemDAO.buscarItemPorId(idItem);
                    if (item != null) {
                        Look l = mapa.get(idLook);
                        if (funcao.equals("superior")) l.setSuperior((Superior) item);
                        else if (funcao.equals("inferior")) l.setInferior((Inferior) item);
                        else if (funcao.equals("calcado")) l.setCalcado((Calcado) item);
                        else if (funcao.equals("acessorio")) l.setAcessorios((Acessorios) item);
                        else if (funcao.equals("roupa_intima")) l.setRoupaIntima((RoupaIntima) item);
                    }
                }
            }

            // Now, populate the usosRegistrados for each look
            String sqlUsos = "SELECT data_uso, ocasiao FROM uso_look WHERE look_id = ?";
            PreparedStatement psUsos = conn.prepareStatement(sqlUsos);

            for (Look look : mapa.values()) {
                List<RegistrarUso> usosDoLook = new ArrayList<>();
                psUsos.setInt(1, look.getId());
                ResultSet rsUsos = psUsos.executeQuery();
                while (rsUsos.next()) {
                    LocalDate dataUso = LocalDate.parse(rsUsos.getString("data_uso"));
                    String ocasiao = rsUsos.getString("ocasiao");
                    usosDoLook.add(new RegistrarUso(dataUso, ocasiao));
                }
                look.setUsosRegistrados(usosDoLook);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar looks: " + e.getMessage());
        }

        lista.addAll(mapa.values());
        return lista;
    }

    public void removerLookPorId(int id) {
        try (Connection conn = ConexaoBD.conectar()) {
            String sql = "DELETE FROM look WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao remover: " + e.getMessage());
        }
    }

    public void atualizarLook(Look look) {
        Connection conn = null;
        try {
            conn = ConexaoBD.conectar();
            conn.setAutoCommit(false);

            String sql1 = "UPDATE look SET nome = ? WHERE id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, look.getNome());
            ps1.setInt(2, look.getId());
            ps1.executeUpdate();

            String sql2 = "DELETE FROM look_item WHERE look_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, look.getId());
            ps2.executeUpdate();

            String sql3 = "INSERT INTO look_item(look_id, item_id, funcao_item) VALUES(?,?,?)";
            PreparedStatement ps3 = conn.prepareStatement(sql3);

            for (Item i : look.listarItensDoLook()) {
                if (i != null) {
                    ps3.setInt(1, look.getId());
                    ps3.setInt(2, i.getId());
                    ps3.setString(3, descobrirFuncao(i));
                    ps3.addBatch();
                }
            }

            ps3.executeBatch();
            conn.commit();

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            System.out.println("Erro ao atualizar: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception ex) {}
        }
    }

    public boolean nomeDeLookJaExiste(String nome) {
        try (Connection conn = ConexaoBD.conectar()) {
            String sql = "SELECT 1 FROM look WHERE nome = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Erro ao verificar nome: " + e.getMessage());
            return true;
        }
    }

    public void registrarUsoLook(Look look, LocalDate data, String ocasiao) {
        try (Connection conn = ConexaoBD.conectar()) {
            String sql = "INSERT INTO uso_look(look_id, data_uso, ocasiao) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, look.getId());
            ps.setString(2, data.toString());
            ps.setString(3, ocasiao);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao registrar uso: " + e.getMessage());
        }
    }

    private String descobrirFuncao(Item item) {
        if (item instanceof Superior) return "superior";
        if (item instanceof Inferior) return "inferior";
        if (item instanceof Calcado) return "calcado";
        if (item instanceof Acessorios) return "acessorio";
        if (item instanceof RoupaIntima) return "roupa_intima";
        return "";
    }
}