package main.GUI.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.database.BancoDadosAPI;
import main.model.Tarefa;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TarefasController {
    private BancoDadosAPI banco = new BancoDadosAPI();
    private ObservableList<Tarefa> tarefas = FXCollections.observableArrayList();

    @FXML private TableView<Tarefa> tabela;
    @FXML private TableColumn<Tarefa, String> colDescricao;
    @FXML private TableColumn<Tarefa, Boolean> colConcluida;
    @FXML private TableColumn<Tarefa, String> colData;

    @FXML private TextField campoTexto;
    @FXML private ComboBox<String> filtroComboBox;

    @FXML
    public void initialize() {
        tabela.setEditable(true);

        colDescricao.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().task));
        colData.setCellValueFactory(data -> {
            var timestamp = data.getValue().data_de_alteracao;
            String formatada = timestamp.toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            return new SimpleStringProperty(formatada);
        });


        colConcluida.setCellValueFactory(new PropertyValueFactory<>("concluida"));
        colConcluida.setCellFactory(tc -> new CheckBoxTableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            @Override
            public void updateItem(Boolean concluida, boolean empty) {
                super.updateItem(concluida, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                Tarefa tarefa = (Tarefa) getTableRow().getItem();
                checkBox.setSelected(tarefa.concluida);

                checkBox.setOnAction(e -> {
                    try {
                        Optional<Tarefa> nova = banco.MudarStatus(Optional.of(tarefa));
                        nova.ifPresent(t -> {
                            tarefa.concluida = t.concluida;
                            tarefa.data_de_alteracao = t.data_de_alteracao;
                            atualizarTabela();
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                setGraphic(checkBox);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        });

        tabela.setItems(tarefas);

        filtroComboBox.getItems().addAll("Todas", "Concluídas", "Não Concluídas");
        filtroComboBox.setValue("Todas");
        filtroComboBox.setOnAction(e -> atualizarTabela());

        atualizarTabela();
    }

    @FXML
    public void adicionarTarefa() {
        String texto = campoTexto.getText().trim();
        if (!texto.isEmpty()) {
            try {
                banco.inserirTarefa(texto, false);
                campoTexto.clear();
                atualizarTabela();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void atualizarTabela() {
        Optional<List<Tarefa>> resultado = banco.selecionarTodasTarefas();
        if (resultado.isPresent()) {
            List<Tarefa> lista = resultado.get();

            String filtro = filtroComboBox.getValue();
            if (filtro.equals("Concluídas")) {
                lista = lista.stream().filter(t -> t.concluida).collect(Collectors.toList());
            } else if (filtro.equals("Não Concluídas")) {
                lista = lista.stream().filter(t -> !t.concluida).collect(Collectors.toList());
            }

            tarefas.setAll(lista);
        }
    }
}