package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.questions.TotalElementosActual;

public class EsperarPersistenciaDeKudos implements Task {

    private final KudosContexto contexto;

    private EsperarPersistenciaDeKudos(KudosContexto contexto) {
        this.contexto = contexto;
    }

    public static EsperarPersistenciaDeKudos de(KudosContexto contexto) {
        return new EsperarPersistenciaDeKudos(contexto);
    }

    @Override
    @Step("{0} espera la persistencia de los kudos sembrados")
    public <T extends Actor> void performAs(T actor) {
        long deadline = System.currentTimeMillis() + 8_000;
        long totalEsperado = contexto.getTotalElementsAntesDeSeed() + contexto.getCantidadSembrada();

        while (System.currentTimeMillis() < deadline) {
            long totalActual = actor.asksFor(TotalElementosActual.enElSistema());
            if (totalActual >= totalEsperado) {
                return;
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
