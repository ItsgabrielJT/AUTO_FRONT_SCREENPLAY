package screenplay_pattern.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class TotalElementosActual implements Question<Long> {

    private static final String CONSUMER_PATH = "/api/v1/kudos";

    private TotalElementosActual() {}

    public static TotalElementosActual enElSistema() {
        return new TotalElementosActual();
    }

    @Override
    public Long answeredBy(Actor actor) {
        actor.attemptsTo(Get.resource(CONSUMER_PATH));
        return actor.asksFor(LastResponse.received()).jsonPath().getLong("totalElements");
    }
}
