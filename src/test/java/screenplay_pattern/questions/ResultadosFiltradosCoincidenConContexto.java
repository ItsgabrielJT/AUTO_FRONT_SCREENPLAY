package screenplay_pattern.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.userinterface.KudosListPage;

public class ResultadosFiltradosCoincidenConContexto implements Question<Boolean> {

    private final KudosContexto contexto;

    private ResultadosFiltradosCoincidenConContexto(KudosContexto contexto) {
        this.contexto = contexto;
    }

    public static ResultadosFiltradosCoincidenConContexto enLaTabla(KudosContexto contexto) {
        return new ResultadosFiltradosCoincidenConContexto(contexto);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        var filas = KudosListPage.FILAS_TABLA.resolveAllFor(actor);
        if (filas.isEmpty()) {
            return false;
        }

        boolean categoriaOk = true;
        if (contexto.getCategoriaSeleccionada() != null) {
            var categorias = KudosListPage.BADGES_CATEGORIA.resolveAllFor(actor);
            categoriaOk = !categorias.isEmpty() && categorias.stream()
                    .allMatch(c -> c.getText().trim().equalsIgnoreCase(contexto.getCategoriaSeleccionada()));
        }

        boolean mensajeOk = true;
        if (contexto.getMensajeBuscado() != null) {
            var mensajes = KudosListPage.CELDAS_MENSAJE.resolveAllFor(actor);
            mensajeOk = !mensajes.isEmpty() && mensajes.stream()
                    .anyMatch(m -> m.getText().contains(contexto.getMensajeBuscado()));
        }

        return categoriaOk && mensajeOk;
    }
}
