import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Optional;

public class MyJDBC {


        // String de conexão com o banco de dados SQLite
        private static final String STRING_CONEXAO = "jdbc:sqlite:tarefas_db.db";

        public static Optional<Connection> pegarConexao() {
            try {
                return Optional.ofNullable(DriverManager.getConnection(STRING_CONEXAO));
            } catch(SQLException e) {
                System.out.println("Erro ao conectar ao Banco de Dados: " + e.getMessage());
            }
            return Optional.empty();
        }

        // SQL para inserir nova tarefa com data_de_alteracao, inserimos aqui uma nova linha para tabela tarefa
        private static final String INSERIR_TAREFA_SQL = "INSERT INTO tarefas (task, concluida, data_de_alteracao) VALUES (?, ?, CURRENT_TIMESTAMP)";

        // SQL para criar a tabela tarefas com data_de_alteracao
        private static final String CRIAR_TABELA_TAREFAS_SQL = "CREATE TABLE IF NOT EXISTS tarefas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "task TEXT NOT NULL," +
                "concluida BOOLEAN NOT NULL," +
                "data_de_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

    public MyJDBC() throws SQLException {
    }

    // Método para configurar o banco de dados: criar tabela e inserir exemplos
        public static void configurarBanco() { //void pois não retorna nenhum valor
            try (Connection conexao = DriverManager.getConnection(STRING_CONEXAO)) { //tente abrir uma conexão com o banco de dados usando a STring_conexao
                // Cria a tabela tarefas com o campo data_de_alteracao
                try (Statement comando = conexao.createStatement()) { //ferramenta para enviar dados para o banco e comando é o nome da variavel que estamos criar para guardar o statment, estamos abrindo o metodo que usamos e cria um novo obejto statment que podemos usar para executar comandos SQL
                    comando.execute(CRIAR_TABELA_TAREFAS_SQL); //constante que contém a instrução SQL para criar a tabela tarefas
                    System.out.println("Tabela 'tarefas' criada ou já existe.");
                }
                // Insere dados de exemplo
                criarExemplosDeTarefas(conexao);
            } catch (SQLException e) {
                System.out.println(String.format("Falha ao criar tabela: %s", e.getMessage()));
            }
        }


        // Método para inserir dados de exemplo (a data_de_alteracao será setada automaticamente pelo banco)
        private static void criarExemplosDeTarefas(Connection conexao) {
            try (PreparedStatement comandoPreparado = conexao.prepareStatement(INSERIR_TAREFA_SQL)) {
                // Inserindo a primeira tarefa
                comandoPreparado.setString(1, "Estudar Java"); // 'task' no código
                comandoPreparado.setBoolean(2, false); // 'concluida'
                comandoPreparado.executeUpdate();

                // Inserindo a segunda tarefa
                comandoPreparado.setString(1, "Fazer exercícios"); // 'task' no código
                comandoPreparado.setBoolean(2, false); // 'concluida'
                comandoPreparado.executeUpdate();

                System.out.println("Tarefas de exemplo inseridas com sucesso.");
            } catch (SQLException e) {
                System.out.println(String.format("Falha ao inserir tarefas: %s", e.getMessage()));
            }
        }
}
