package screenplay_pattern.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import screenplay_pattern.userinterface.KudosListPage;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriasVisibles implements Question<List<String>> {

    private CategoriasVisibles() {}

    public static CategoriasVisibles enLaTabla() {
        return new CategoriasVisibles();
    }

    @Override
    public List<String> answeredBy(Actor actor) {
        return KudosListPage.BADGES_CATEGORIA.resolveAllFor(actor).stream()
                .map(badge -> badge.getText().trim())
                .collect(Collectors.toList());
    }
}
