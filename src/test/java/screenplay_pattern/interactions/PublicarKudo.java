package screenplay_pattern.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.HashMap;
import java.util.Map;

public class PublicarKudo implements Interaction {

    private static final String PRODUCER_PATH = "/api/v1/kudos";
    private static final String CLAVE_RECEPTOR = "receptor";
    private static final String CLAVE_CATEGORIA = "categoria";
    private static final String CLAVE_MENSAJE = "mensaje";

    private final String from;
    private final Map<String, String> row;

    private PublicarKudo(String from, Map<String, String> row) {
        this.from = from;
        this.row = row;
    }

    public static PublicarKudo desde(String from) {
        return new PublicarKudo(from, Map.of());
    }

    public PublicarKudo conDatos(Map<String, String> row) {
        return new PublicarKudo(from, row);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Map<String, String> body = new HashMap<>();
        body.put("from", from);
        body.put("to", row.get(CLAVE_RECEPTOR));
        body.put("category", row.get(CLAVE_CATEGORIA));
        body.put("message", row.get(CLAVE_MENSAJE));

        actor.attemptsTo(
                Post.to(PRODUCER_PATH)
                        .with(request -> request
                                .contentType("application/json")
                                .body(body))
        );

        int statusCode = actor.asksFor(LastResponse.received()).statusCode();
        if (statusCode != 202) {
            throw new RuntimeException(
                    "Seeder fallo. HTTP " + statusCode + " -> " + actor.asksFor(LastResponse.received()).asString());
        }
    }
}
