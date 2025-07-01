import java.sql.SQLException;
import java.util.Optional;

public class main {
    public static void main(String[] args) throws SQLException {
        BancoDadosAPI db = new BancoDadosAPI();

        MyJDBC.configurarBanco();
        var tarefa = db.selecionarTarefa("tarefa1");
//        System.out.println(db.selecionarTodasTarefas());
        System.out.println(db.MudarStatus(tarefa));
    }
}
