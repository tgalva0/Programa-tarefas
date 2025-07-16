import org.example.database.BancoDadosAPI;
import org.example.model.Tarefa;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BancoDadosAPITest {

    private BancoDadosAPI banco;

    @BeforeEach
    void setUp() {
        banco = new BancoDadosAPI();
    }

    @Test
    @Order(1)
    void deveInserirUmaTarefaNova() throws SQLException {
        Optional<Tarefa> tarefaInserida = banco.inserirTarefa("Testar JUnit", false);
        assertTrue(tarefaInserida.isPresent());
    }

    @Test
    @Order(2)
    void deveRecusarInsercaoDeTarefaDuplicada() throws SQLException {
        Optional<Tarefa> duplicada = banco.inserirTarefa("Testar JUnit", false);
        assertTrue(duplicada.isEmpty());
    }

    @Test
    @Order(3)
    void deveSelecionarTodasAsTarefas() {
        Optional<List<Tarefa>> todas = banco.selecionarTodasTarefas();
        assertTrue(todas.isPresent());
        assertFalse(todas.get().isEmpty());
    }

    @Test
    @Order(4)
    void deveSelecionarTarefasPorStatus() {
        Optional<List<Tarefa>> abertas = banco.selecionarTodasTarefasPorStatus(false);
        Optional<List<Tarefa>> concluidas = banco.selecionarTodasTarefasPorStatus(true);
        assertTrue(abertas.isPresent());
        assertTrue(concluidas.isPresent());
    }

    @Test
    @Order(5)
    void deveSelecionarTarefaEspecifica() throws SQLException {
        Optional<Tarefa> tarefa = banco.selecionarTarefa("Testar JUnit");
        assertTrue(tarefa.isPresent());
        assertEquals("Testar JUnit", tarefa.get().task);
    }

    @Test
    @Order(6)
    void deveMudarStatusDeTarefa() throws SQLException {
        Optional<Tarefa> tarefa = banco.selecionarTarefa("Testar JUnit");
        Optional<Tarefa> alterada = banco.MudarStatus(tarefa);
        assertTrue(alterada.isPresent());
        assertTrue(alterada.get().concluida);
    }
}