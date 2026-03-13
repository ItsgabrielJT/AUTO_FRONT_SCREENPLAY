package screenplay_pattern.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class TotalElementosActual implements Question<Long> {

    private static final String CONSUMER_BASE_URL = "http://localhost:8081";
    private static final String CONSUMER_PATH = "/api/v1/kudos";

    private TotalElementosActual() {}

    public static TotalElementosActual enElSistema() {
        return new TotalElementosActual();
    }

    @Override
    public Long answeredBy(Actor actor) {
        SerenityRest.given()
                .baseUri(CONSUMER_BASE_URL)
                .get(CONSUMER_PATH);
        return SerenityRest.lastResponse().jsonPath().getLong("totalElements");
    }
}
