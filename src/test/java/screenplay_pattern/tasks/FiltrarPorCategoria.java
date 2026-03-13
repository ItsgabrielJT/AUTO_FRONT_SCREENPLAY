package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import screenplay_pattern.interactions.EstablecerValorSelect;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.userinterface.KudosListPage;

public class FiltrarPorCategoria implements Task {

    private final String        categoria;
    private final KudosContexto contexto;

    private FiltrarPorCategoria(String categoria, KudosContexto contexto) {
        this.categoria = categoria;
        this.contexto  = contexto;
    }

    public static FiltrarPorCategoria conValor(String categoria, KudosContexto contexto) {
        return new FiltrarPorCategoria(categoria, contexto);
    }

    @Override
    @Step("{0} filtra el listado por la categoría '#categoria'")
    public <T extends Actor> void performAs(T actor) {
        contexto.categoriaSeleccionada = categoria;
        actor.attemptsTo(EstablecerValorSelect.en(KudosListPage.FILTRO_CATEGORIA, categoria));
    }
}
