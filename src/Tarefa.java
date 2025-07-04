import java.sql.Timestamp;

public class Tarefa {
    public String task;
    public boolean concluido;
    public Timestamp data_de_alteracao;

    public Tarefa(String task, boolean concluida, Timestamp data_de_alteracao) {
        this.task = task;
        this.concluido = concluida;
        this.data_de_alteracao = data_de_alteracao;
    }

    @Override
    public String toString() {
        return "\nTarefa{\n" +
                "texto: " + task +
                "\nstatus: " + concluido +
                "\ndata_alteraçao: " + data_de_alteracao +
                "\n" + "}";
    }
}
