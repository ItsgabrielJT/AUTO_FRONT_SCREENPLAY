package screenplay_pattern.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import screenplay_pattern.userinterface.KudosListPage;

import java.util.List;
import java.util.stream.Collectors;

public class MensajesVisibles implements Question<List<String>> {

    private MensajesVisibles() {}

    public static MensajesVisibles enLaTabla() {
        return new MensajesVisibles();
    }

    @Override
    public List<String> answeredBy(Actor actor) {
        return KudosListPage.CELDAS_MENSAJE.resolveAllFor(actor).stream()
                .map(celda -> celda.getText().trim())
                .collect(Collectors.toList());
    }
}
