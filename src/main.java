import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
        BancoDadosAPI db = new BancoDadosAPI();

        MyJDBC.configurarBanco();
//        db.inserirTarefa("tarefa1",false);

        System.out.println(db.selecionarTodasTarefas());
    }
}
