import java.sql.Timestamp;
import java.util.Date;

public class Tarefa {
    public String task;
    public boolean concluida;
    public Timestamp data_de_alteracao;

    public Tarefa(String task, boolean concluida, Timestamp data_de_alteracao) {
        this.task = task;
        this.concluida = concluida;
        this.data_de_alteracao = data_de_alteracao;
    }

    @Override
    public String toString() {
        return "\nTarefa{\n" +
                "texto: " + task +
                "\nstatus: " + concluida +
                "\ndata_alteraçao: " + data_de_alteracao +
                "\n" + "}";
    }
}
