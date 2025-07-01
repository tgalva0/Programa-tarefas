import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BancoDadosAPI {
    //teste
    private Optional<Connection> db = Optional.empty();
    private static final String INSERIR_TAREFA_SQL = "INSERT INTO tarefas (task, concluida, data_de_alteracao) VALUES (?, ?, CURRENT_TIMESTAMP)";
    // SQL para criar a tabela tarefas com data_de_alteracao
    private static final String CRIAR_TABELA_TAREFAS_SQL = "CREATE TABLE IF NOT EXISTS tarefas (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "task TEXT NOT NULL," +
            "concluida BOOLEAN NOT NULL," +
            "data_de_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")";
    private static final String MUDAR_STATUS = "UPDATE tarefas SET concluida = ?, data_de_alteracao = CURRENT_TIMESTAMP WHERE task = ?";


    public BancoDadosAPI() {
        this.db = MyJDBC.pegarConexao();

        if(db.isPresent()) {
            configurarBanco();
            System.out.println("Conectado com sucesso!");
        } else {
            System.out.println("Erro ao conectar ao Banco de Dados!");
        }
    }

    public void configurarBanco() { //void pois não retorna nenhum valor
        try {
            PreparedStatement criarTabela = db.get().prepareStatement(CRIAR_TABELA_TAREFAS_SQL);
            criarTabela.execute();
            criarTabela.close();
        } catch (SQLException e) {
            System.out.println(String.format("Falha ao criar tabela: %s", e.getMessage()));
        }
    }

    public Optional<List<Tarefa>> selecionarTodasTarefas()  {
        String sql = "SELECT task, concluida, data_de_alteracao FROM tarefas ORDER BY data_de_alteracao DESC";
        List<Tarefa> tarefas = new ArrayList<>();
        try {
            PreparedStatement requisicao = db.get().prepareStatement(sql);
            ResultSet resultado = requisicao.executeQuery();

            while (resultado.next()) {
                String task = resultado.getString("task");
                boolean concluida = resultado.getBoolean("concluida");
                String atualizacao = resultado.getString("data_de_alteracao");



                // Adicionar livro à lista
                tarefas.add(new Tarefa(task, concluida, atualizacao));
            } resultado.close();

        } catch (SQLException e) {
            System.out.println("Erro ao selecionar todas as Tarefas: " + e.getMessage());
            return Optional.empty();
        }
        return Optional.of(tarefas);
    }


    public boolean inserirTarefa(String texto, Boolean concluida) throws SQLException {
        Optional<List<Tarefa>>tarefas= selecionarTodasTarefas();
        if (tarefas.isPresent()){
            for (Tarefa tarefa : tarefas.get())
                if (tarefa.task.equals(texto)) {
                    System.out.println("Essa tarefa já existe");
                    return false;
                }
        }
        PreparedStatement comando = db.get().prepareStatement(INSERIR_TAREFA_SQL);
        comando.setString(1, texto);
        comando.setBoolean(2, concluida);
        try {
            comando.executeUpdate();
            comando.close();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir tarefas: " + e.getMessage());
            return false;
        }
        System.out.println("Tarefa Criada com sucesso!");
        return true;
    }


    public boolean MudarStatus(Tarefa tarefa) throws SQLException {
        boolean status = true;

        if (tarefa.concluida){
            status= false;
        }
        PreparedStatement comando = db.get().prepareStatement(MUDAR_STATUS);
        comando.setBoolean(1, status);
        comando.setString(2, tarefa.task);

        try {
            comando.executeUpdate();
            comando.close();
        } catch (SQLException e) {
            System.out.println("Erro ao mudar status: " + e.getMessage());
            return false;
        }
        System.out.println("Status concluído!");
        return true;


    }
}
