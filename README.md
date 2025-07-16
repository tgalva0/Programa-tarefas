# 📋 Programa Tarefas - JavaFX com JDK 22

Este projeto é uma aplicação Java de tarefas utilizando **JavaFX SDK 17.0.1** aliado ao CSS na inteface gráfica, **SQLite JDBC** e **JUnit para testes**, configurado utilizando Gradle.

### 🖥️ Para Instalar e Usar a Aplicação (Usuário Final)

Graças ao instalador moderno criado com `jpackage`, o Java já está embutido no programa. Portanto, você **não precisa ter o Java instalado** no seu computador!

Os requisitos são mínimos:

* **Sistema Operacional:** Windows 10 ou superior (64-bit).
* **Espaço em Disco:** Aproximadamente 150 MB de espaço livre para a instalação.
* **Memória RAM:** Mínimo de 2 GB
  **Como instalar:**

1.  Vá para a seção de [**Releases**](https://github.com/tgalva0/Programa-tarefas/releases) deste repositório.
2.  Baixe o arquivo de instalação mais recente (ex: `NoteTakingApp-1.0.0.exe`).
3.  Execute o instalador e siga as instruções na tela.

### 🛠️ Para Compilar e Desenvolver a Partir do Código-Fonte (Desenvolvedor)

Se você deseja clonar este repositório para estudar, modificar ou compilar o código, você precisará do seguinte ambiente de desenvolvimento:

* **Git:** Necessário para clonar o repositório.
* **JDK (Java Development Kit):** Versão **22**. O projeto foi desenvolvido e testado com o JDK 22. Versões mais recentes podem funcionar, mas não são garantidas.
    * Você pode baixar o OpenJDK ou o Oracle JDK.
* **IDE (Ambiente de Desenvolvimento Integrado) (Recomendado):**
    * [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Community Edition é suficiente).
    * [Visual Studio Code](https://code.visualstudio.com/) com o "Extension Pack for Java".

> **Nota sobre o Gradle:** Você **não precisa** instalar o Gradle manualmente no seu sistema. O projeto inclui o Gradle Wrapper (`gradlew`), que baixará e usará automaticamente a versão correta do Gradle ao executar os comandos abaixo.

**Como compilar e rodar:**

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/tgalva0/Programa-tarefas.git
    ```
2.  **Para rodar a aplicação em modo de desenvolvimento:**
    ```bash
    ./gradlew run
    ```
3.  **Para gerar o instalador localmente:**
    ```bash
    ./gradlew jpackage
    ```
    O instalador será gerado na pasta `build/jpackage/`.

