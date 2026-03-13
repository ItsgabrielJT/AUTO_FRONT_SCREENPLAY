package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import screenplay_pattern.interactions.PublicarKudo;
import screenplay_pattern.models.KudosContexto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PublicarKudosSembrados implements Task {

    private static final String CLAVE_MENSAJE = "mensaje";

    private final String from;
    private final List<Map<String, String>> rows;
    private final KudosContexto contexto;

    private PublicarKudosSembrados(String from, List<Map<String, String>> rows, KudosContexto contexto) {
        this.from = from;
        this.rows = rows;
        this.contexto = contexto;
    }

    public static PublicarKudosSembrados para(String from, List<Map<String, String>> rows, KudosContexto contexto) {
        return new PublicarKudosSembrados(from, rows, contexto);
    }

    @Override
    @Step("{0} publica kudos del escenario")
    public <T extends Actor> void performAs(T actor) {
        String runId = "RUN-" + System.currentTimeMillis();
        List<Map<String, String>> rowsEnriquecidas = rows.stream()
                .map(row -> enriquecerMensaje(row, runId))
                .collect(Collectors.toList());

        contexto.setCantidadSembrada(rowsEnriquecidas.size());
        rowsEnriquecidas.forEach(row -> actor.attemptsTo(PublicarKudo.desde(from).conDatos(row)));
    }

    private Map<String, String> enriquecerMensaje(Map<String, String> row, String runId) {
        String mensajeBase = row.get(CLAVE_MENSAJE);
        String mensajeUnico = mensajeBase + " [" + runId + "]";
        contexto.registrarMensajeUnico(mensajeBase, mensajeUnico);

        Map<String, String> enriched = new HashMap<>(row);
        enriched.put(CLAVE_MENSAJE, mensajeUnico);
        return enriched;
    }
}
