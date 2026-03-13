package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import screenplay_pattern.interactions.EstablecerValorInput;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.userinterface.KudosListPage;

public class BuscarPorMensaje implements Task {

    private final String        mensajeBase;
    private final KudosContexto contexto;

    private BuscarPorMensaje(String mensajeBase, KudosContexto contexto) {
        this.mensajeBase = mensajeBase;
        this.contexto    = contexto;
    }

    public static BuscarPorMensaje conTexto(String mensajeBase, KudosContexto contexto) {
        return new BuscarPorMensaje(mensajeBase, contexto);
    }

    @Override
    @Step("{0} escribe en el buscador de kudos")
    public <T extends Actor> void performAs(T actor) {
        String mensajeUnico = contexto.mensajesUnicos.getOrDefault(mensajeBase, mensajeBase);
        contexto.mensajeBuscado = mensajeUnico;
        actor.attemptsTo(EstablecerValorInput.en(KudosListPage.CAMPO_BUSQUEDA_MENSAJE, mensajeUnico));
    }
}
