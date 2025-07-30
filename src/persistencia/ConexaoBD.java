package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBD {
    //esta classe configura o meu banco de dados

    private static final String URL = "jdbc:sqlite:meu_guarda_roupa.db";
    
    //aqui faz a conexão do banco de dados
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar no banco: " + e.getMessage());
            return null;
        }
    }

    // Cria as tabelas
    public static void inicializarBanco() {
        String sqlItem = """
            CREATE TABLE IF NOT EXISTS item (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                categoria TEXT NOT NULL,
                tipo TEXT NOT NULL,
                nome TEXT NOT NULL,
                cor TEXT,
                tamanho TEXT,
                loja TEXT,
                conservacao TEXT NOT NULL,
                totalUsos INTEGER DEFAULT 0,
                totalLavagens INTEGER DEFAULT 0,
                isLimpo BOOLEAN DEFAULT 1,
                isEmprestado BOOLEAN DEFAULT 0,
                dataEmprestimo TEXT,
                emprestado_para TEXT
            );
        """;

        String sqlLook = """
            CREATE TABLE IF NOT EXISTS look (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL UNIQUE
            );
        """;

        String sqlLookItem = """
            CREATE TABLE IF NOT EXISTS look_item (
                look_id INTEGER NOT NULL,
                item_id INTEGER NOT NULL,
                funcao_item TEXT NOT NULL,
                FOREIGN KEY (look_id) REFERENCES look(id) ON DELETE CASCADE,
                FOREIGN KEY (item_id) REFERENCES item(id) ON DELETE CASCADE,
                PRIMARY KEY (look_id, item_id, funcao_item)
            );
        """;

        String sqlUsoLook = """
            CREATE TABLE IF NOT EXISTS uso_look (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                look_id INTEGER NOT NULL,
                data_uso TEXT NOT NULL,
                ocasiao TEXT,
                FOREIGN KEY (look_id) REFERENCES look(id) ON DELETE CASCADE
            );
        """;

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {

            if (conn != null) {
                stmt.execute(sqlItem);
                stmt.execute(sqlLook);
                stmt.execute(sqlLookItem);
                stmt.execute(sqlUsoLook);
                System.out.println("Banco de dados pronto");
            }

        } catch (SQLException e) {
            System.err.println("Erro na criação das tabelas: " + e.getMessage());
        }
    }
}
