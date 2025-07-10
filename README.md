# 📋 Programa Tarefas - JavaFX com JDK 24

Este projeto é uma aplicação Java de tarefas utilizando **JavaFX SDK 24.0.1**, **SQLite JDBC** e **JUnit para testes**, configurado manualmente e executado via IntelliJ IDEA (sem Gradle ou Maven).

## ✅ Requisitos

- **JDK 24 instalado**
    - Download oficial: https://jdk.java.net/
- **JavaFX SDK 24.0.1 baixado**
    - Download oficial: https://gluonhq.com/products/javafx/
- **IntelliJ IDEA** (Community ou Ultimate)
- Sistema operacional:
    - Windows, macOS ou Linux


## ⚙️ Configuração no IntelliJ IDEA

### 🔧 Adicionando bibliotecas (JavaFX e SQLite)

1. Acesse **File > Project Structure (Ctrl+Alt+Shift+S)**
2. Vá em **Modules > Dependencies**
3. Clique em **+ > JARs or directories**
4. Adicione todos os arquivos `.jar` da pasta:
    - `lib/javafx-sdk-24.0.1/lib`
    - `lib/sqlite-jdbc-<versao>.jar`

### 🎯 Configurando o Run Configuration

1. Vá em **Run > Edit Configurations**
2. Crie uma nova configuração do tipo **Application**
3. Configure os campos:

- **Main class**: `main.TarefasApp`
- **Working directory**: diretório raiz do projeto
- **VM options**: -`--module-path=lib/javafx-sdk-24.0.1/lib --add-modules=javafx.controls,javafx.fxml -Djava.library.path=lib/javafx-sdk-24.0.1/bin`


## 🖥️ Instruções por sistema operacional

### 🪟 Windows

- Certifique-se de que os arquivos `.dll` estão em `javafx-sdk-24.0.1/bin`
- Use `-Djava.library.path` para apontar para essa pasta

### 🐧 Linux

- Certifique-se de que os `.so` em `bin/` têm permissão de execução: -`chmod +x lib/javafx-sdk-24.0.1/bin/*.so`


### 🍎 macOS

- Os nativos são `.dylib`
- Desative restrições de segurança com: -`sudo xattr -r -d com.apple.quarantine lib/javafx-sdk-24.0.1/bin`


## 🧪 Executando o projeto

- Execute `TarefasApp.java` pela configuração criada

## 📚 Observações
- O projeto dispensa Gradle e Maven
- Todas as dependências estão empacotadas localmente
- Testado com JDK 24 e JavaFX SDK 24.0.1

## 🧠 Créditos
  Desenvolvido por Leticia e Thiago

