package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.questions.TotalElementosActual;

public class PrepararContextoDeSiembra implements Task {

    private final KudosContexto contexto;

    private PrepararContextoDeSiembra(KudosContexto contexto) {
        this.contexto = contexto;
    }

    public static PrepararContextoDeSiembra con(KudosContexto contexto) {
        return new PrepararContextoDeSiembra(contexto);
    }

    @Override
    @Step("{0} prepara el contexto de siembra")
    public <T extends Actor> void performAs(T actor) {
        contexto.reset();
        contexto.setTotalElementsAntesDeSeed(actor.asksFor(TotalElementosActual.enElSistema()));
    }
}
