package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import screenplay_pattern.models.KudosContexto;
import java.util.List;
import java.util.Map;

public class SembrarKudos implements Task {

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
        actor.attemptsTo(
                UsarApiConsumer.configurada(),
                PrepararContextoDeSiembra.con(contexto),
                UsarApiProducer.configurada(),
                PublicarKudosSembrados.para(from, rows, contexto),
                UsarApiConsumer.configurada(),
                EsperarPersistenciaDeKudos.de(contexto)
        );
    }
}
