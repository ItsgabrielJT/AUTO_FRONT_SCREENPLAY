package screenplay_pattern.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.userinterface.KudosListPage;

public class RegistrosSembradosVisibles implements Question<Long> {

    private final KudosContexto contexto;

    private RegistrosSembradosVisibles(KudosContexto contexto) {
        this.contexto = contexto;
    }

    public static RegistrosSembradosVisibles enLaTabla(KudosContexto contexto) {
        return new RegistrosSembradosVisibles(contexto);
    }

    @Override
    public Long answeredBy(Actor actor) {
        return KudosListPage.FILAS_TABLA.resolveAllFor(actor).stream()
                .filter(fila -> contexto.mensajesUnicos.values().stream()
                        .anyMatch(unico -> fila.getText().contains(unico)))
                .count();
    }
}
