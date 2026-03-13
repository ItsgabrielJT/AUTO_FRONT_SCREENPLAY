package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import screenplay_pattern.interactions.PublicarKudo;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.questions.TotalElementosActual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SembrarKudos implements Task {

    private static final String CLAVE_MENSAJE = "mensaje";

    private final String        from;
    private final List<Map<String, String>> rows;
    private final KudosContexto contexto;

    private SembrarKudos(String from, List<Map<String, String>> rows, KudosContexto contexto) {
        this.from     = from;
        this.rows     = rows;
        this.contexto = contexto;
    }

    public static SembrarKudos para(String from, List<Map<String, String>> rows,
                                    KudosContexto contexto) {
        return new SembrarKudos(from, rows, contexto);
    }

    @Override
    @Step("{0} siembra kudos vía API para el emisor")
    public <T extends Actor> void performAs(T actor) {
        contexto.reset();
        contexto.totalElementsAntesDeSeed = actor.asksFor(TotalElementosActual.enElSistema());

        String runId = "RUN-" + System.currentTimeMillis();
        List<Map<String, String>> rowsEnriquecidas = rows.stream()
                .map(row -> enriquecerMensaje(row, runId))
                .collect(Collectors.toList());

        contexto.cantidadSembrada = rowsEnriquecidas.size();
        rowsEnriquecidas.forEach(row -> actor.attemptsTo(PublicarKudo.desde(from).conDatos(row)));
        esperarPersistencia(actor);
    }

    private Map<String, String> enriquecerMensaje(Map<String, String> row, String runId) {
        String mensajeBase   = row.get(CLAVE_MENSAJE);
        String mensajeUnico  = mensajeBase + " [" + runId + "]";
        contexto.mensajesUnicos.put(mensajeBase, mensajeUnico);
        Map<String, String> enriched = new HashMap<>(row);
        enriched.put(CLAVE_MENSAJE, mensajeUnico);
        return enriched;
    }

    private <T extends Actor> void esperarPersistencia(T actor) {
        long deadline = System.currentTimeMillis() + 8_000;
        while (System.currentTimeMillis() < deadline) {
            if (actor.asksFor(TotalElementosActual.enElSistema()) >=
                    contexto.totalElementsAntesDeSeed + contexto.cantidadSembrada) {
                return;
            }
            try { Thread.sleep(300); } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
