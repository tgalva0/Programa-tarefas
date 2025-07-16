package org.example.database;

import org.example.model.Tarefa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BancoDadosAPI {
    //teste
    private Optional<Connection> db = Optional.empty();
    private static final String INSERIR_TAREFA_SQL = "INSERT INTO tarefas (task, concluida, data_de_alteracao) VALUES (?, ?, datetime('now', 'localtime'))";
    // SQL para criar a tabela tarefas com data_de_alteracao
    private static final String CRIAR_TABELA_TAREFAS_SQL = "CREATE TABLE IF NOT EXISTS tarefas (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "task TEXT NOT NULL," +
            "concluida BOOLEAN NOT NULL," +
            "data_de_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")";
    private static final String MUDAR_STATUS = "UPDATE tarefas SET concluida = ?, data_de_alteracao = datetime('now', 'localtime') WHERE task = ?";
    private static final String SELECIONAR_TAREFA_SQL = "SELECT task, concluida, data_de_alteracao FROM tarefas WHERE task = ?";

    public BancoDadosAPI() {
        this.db = MyJDBC.pegarConexao();

        if (db.isPresent()) {
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

    public Optional<List<Tarefa>> selecionarTodasTarefas() {
        String sql = "SELECT task, concluida, data_de_alteracao FROM tarefas ORDER BY data_de_alteracao DESC";
        List<Tarefa> tarefas = new ArrayList<>();

        try {
            PreparedStatement requisicao = db.get().prepareStatement(sql);
            ResultSet resultado = requisicao.executeQuery();

            while (resultado.next()) {
                String task = resultado.getString("task");
                boolean concluida = resultado.getBoolean("concluida");
                Timestamp atualizacao = resultado.getTimestamp("data_de_alteracao");

                // Adicionar livro à lista
                tarefas.add(new Tarefa(task, concluida, atualizacao));
            }
            requisicao.close();
            resultado.close();

        } catch (SQLException e) {
            System.out.println("Erro ao selecionar todas as Tarefas: " + e.getMessage());
            return Optional.empty();
        }
        return Optional.of(tarefas);
    }

    public Optional<List<Tarefa>> selecionarTodasTarefasPorStatus(Boolean concluido) {
        String sql = "SELECT task, concluida, data_de_alteracao FROM tarefas WHERE concluida = ?";
        List<Tarefa> tarefas = new ArrayList<>();

        try {
            PreparedStatement requisicao = db.get().prepareStatement(sql);
            requisicao.setBoolean(1, concluido);
            ResultSet resultado = requisicao.executeQuery();
            requisicao.close();

            while (resultado.next()) {
                String task = resultado.getString("task");
                boolean concluida = resultado.getBoolean("concluida");
                Timestamp atualizacao = resultado.getTimestamp("data_de_alteracao");

                // Adicionar livro à lista
                tarefas.add(new Tarefa(task, concluida, atualizacao));
            }
            resultado.close();

        } catch (SQLException e) {
            System.out.println("Erro ao selecionar todas as Tarefas: " + e.getMessage());
            return Optional.empty();
        }
        return Optional.of(tarefas);
    }

    public Optional<Tarefa> selecionarTarefa(String task) {
        try (PreparedStatement comando = db.get().prepareStatement(SELECIONAR_TAREFA_SQL)) {
            comando.setString(1, task);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    String rsTask = resultado.getString("task");
                    boolean rsConcluida = resultado.getBoolean("concluida");
                    Timestamp rsAtualizacao = resultado.getTimestamp("data_de_alteracao");

                    return Optional.of(new Tarefa(rsTask, rsConcluida, rsAtualizacao));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar tarefa: " + e.getMessage());
        }

        return Optional.empty();
    }


    public Optional<Tarefa> inserirTarefa(String texto, Boolean concluida) throws SQLException {
        Optional<List<Tarefa>> tarefas = selecionarTodasTarefas();
        if (tarefas.isPresent()) {
            for (Tarefa tarefa : tarefas.get())
                if (tarefa.task.equals(texto)) {
                    System.out.println("Essa tarefa já existe");
                    return Optional.empty();
                }
        }

        try {
            PreparedStatement comando = db.get().prepareStatement(INSERIR_TAREFA_SQL);
            comando.setString(1, texto);
            comando.setBoolean(2, concluida);
            comando.executeUpdate();
            comando.close();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir tarefas: " + e.getMessage());
            return Optional.empty();
        }
        System.out.println("Tarefa Criada com sucesso!");

        return selecionarTarefa(texto);
    }


    public Optional<Tarefa> MudarStatus(Optional<Tarefa> tarefa) throws SQLException {
        if(tarefa.isPresent()) {
            boolean status = true;

            if (tarefa.get().concluida) {
                status = false;
            }

            try {
                PreparedStatement comando = db.get().prepareStatement(MUDAR_STATUS);
                comando.setBoolean(1, status);
                comando.setString(2, tarefa.get().task);
                comando.executeUpdate();
                comando.close();
            } catch (SQLException e) {
                System.out.println("Erro ao mudar status: " + e.getMessage());
                return Optional.empty();
            }
            Optional<Tarefa> novaTarefa = selecionarTarefa(tarefa.get().task);
            System.out.println("Status concluído = " + status);
            return Optional.of(novaTarefa.get());
        }
        return Optional.empty();
    }
}