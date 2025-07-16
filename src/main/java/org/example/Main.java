package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {
    @Override
    public void start(Stage palco) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/Main.fxml"));
        Parent raiz = loader.load();
        Scene cena = new Scene(raiz);
        cena.getStylesheets().add(getClass().getResource("/org/example/style.css").toExternalForm());
        Button botao = (Button) raiz.lookup(".adicionar-button");

        botao.setOnMouseEntered((event) -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.3),
                            new KeyValue(botao.backgroundProperty(),
                                    new Background(new BackgroundFill(Color.web("#044787"), new CornerRadii(10), Insets.EMPTY))))
            );
            timeline.play();
        });

        botao.setOnMouseExited((event) -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.3),
                            new KeyValue(botao.backgroundProperty(),
                                    new Background(new BackgroundFill(Color.web("#3a3d40"), new CornerRadii(10), Insets.EMPTY))))
            );
            timeline.play();
        });

        palco.initStyle(StageStyle.DECORATED);
        palco.getIcons().add(new Image(getClass().getResourceAsStream("/org/example/icon.png")));
        palco.setScene(cena);
        palco.setTitle("Bloco de Tarefas");
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}