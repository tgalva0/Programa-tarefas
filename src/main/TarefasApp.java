package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class TarefasApp extends Application {
    @Override
    public void start(Stage palco) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/view/Main.fxml"));
        Parent raiz = loader.load();
        palco.setScene(new Scene(raiz));
        palco.setTitle("Bloco de Tarefas");
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}