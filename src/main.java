import java.sql.SQLException;
import java.util.Optional;

public class main {
    public static void main(String[] args) throws SQLException {
        BancoDadosAPI db = new BancoDadosAPI();

        MyJDBC.configurarBanco();
        db.inserirTarefa("tarefa1", false);
        System.out.println(db.selecionarTarefa("tarefa1"));
//        System.out.println(db.selecionarTodasTarefas());
        System.out.println(db.MudarStatus(db.selecionarTarefa("tarefa1")));
    }
}
