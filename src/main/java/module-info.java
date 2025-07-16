/**
 * Este é o descritor do módulo para a sua aplicação.
 */
module org.example.NoteTakingApp { // 1. Use um nome de módulo único (geralmente o nome do seu pacote principal)

    // 2. Declare os módulos do JavaFX que você precisa
    requires javafx.controls;
    requires javafx.fxml;

    // 3. Declare outros módulos, como o de SQL para o SQLite
    requires java.sql;

    // 4. Abra seu pacote para o JavaFX FXML (MUITO IMPORTANTE!)
    // Isso permite que o FXMLLoader acesse seus controllers e componentes via reflexão.
    opens org.example to javafx.fxml;
    opens org.example.GUI.controller to javafx.fxml;
    opens org.example.model to javafx.base;
    opens org.example.database to javafx.fxml;

    // 5. Exponha seu pacote principal para que o JavaFX possa iniciar a aplicação
    exports org.example;
}