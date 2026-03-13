package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import screenplay_pattern.interactions.EsperarResultadosFiltrados;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.userinterface.KudosListPage;

public class AplicarFiltros implements Task {

    private final KudosContexto contexto;

    private AplicarFiltros(KudosContexto contexto) {
        this.contexto = contexto;
    }

    public static AplicarFiltros delEscenario(KudosContexto contexto) {
        return new AplicarFiltros(contexto);
    }

    @Override
    @Step("{0} aplica los filtros y espera el resultado actualizado")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(KudosListPage.BOTON_APLICAR_FILTROS),
                EsperarResultadosFiltrados.para(contexto)
        );
    }
}
