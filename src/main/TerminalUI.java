package main;

import main.database.BancoDadosAPI;
import main.model.Tarefa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TerminalUI {
    private final BancoDadosAPI banco = new BancoDadosAPI();
    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        while (true) {
            System.out.println("\n=== BLOCO DE TAREFAS ===");
            System.out.println("1. Criar nova tarefa");
            System.out.println("2. Listar todas as tarefas");
            System.out.println("3. Listar tarefas concluídas");
            System.out.println("4. Listar tarefas pendentes");
            System.out.println("5. Alternar status da tarefa");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> criarTarefa();
                case "2" -> listarTarefas("todas");
                case "3" -> listarTarefas("concluidas");
                case "4" -> listarTarefas("pendentes");
                case "5" -> alternarStatus();
                case "0" -> {
                    System.out.println("Saindo... até mais!");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void criarTarefa() {
        System.out.print("Digite a nova tarefa: ");
        String texto = scanner.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("Tarefa não pode ser vazia.");
            return;
        }

        try {
            Optional<Tarefa> resultado = banco.inserirTarefa(texto, false);
            System.out.println(resultado.isPresent() ? "✅ Tarefa criada!" : "⚠️ Tarefa já existe.");
        } catch (Exception e) {
            System.out.println("Erro ao criar tarefa: " + e.getMessage());
        }
    }

    private void listarTarefas(String tipo) {
        Optional<List<Tarefa>> resultado;

        switch (tipo) {
            case "concluidas" -> resultado = banco.selecionarTodasTarefasPorStatus(true);
            case "pendentes" -> resultado = banco.selecionarTodasTarefasPorStatus(false);
            default -> resultado = banco.selecionarTodasTarefas();
        }

        if (resultado.isPresent()) {
            List<Tarefa> tarefas = resultado.get();
            if (tarefas.isEmpty()) {
                System.out.println("📭 Nenhuma tarefa encontrada.");
            } else {
                System.out.println("\n📋 Lista de tarefas:");
                int i = 1;
                for (Tarefa t : tarefas) {
                    String status = t.concluida ? "✅" : "🕓";
                    System.out.printf("%d. %s [%s] - Atualizado em: %s%n", i++, t.task, status, t.data_de_alteracao);
                }
            }
        } else {
            System.out.println("Erro ao buscar tarefas.");
        }
    }

    private void alternarStatus() {
        System.out.print("Digite o nome exato da tarefa a alterar: ");
        String nome = scanner.nextLine().trim();
        try {
            Optional<Tarefa> tarefa = banco.selecionarTarefa(nome);
            if (tarefa.isEmpty()) {
                System.out.println("⚠️ Tarefa não encontrada.");
                return;
            }

            Optional<Tarefa> nova = banco.MudarStatus(tarefa);
            if (nova.isPresent()) {
                System.out.println("🔁 Status alterado com sucesso!");
                System.out.printf("Nova tarefa: %s [%s]%n", nova.get().task, nova.get().concluida ? "✅" : "🕓");
            } else {
                System.out.println("⚠️ Não foi possível alterar o status.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao mudar status: " + e.getMessage());
        }
    }
}