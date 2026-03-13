package screenplay_pattern.interactions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

import java.util.HashMap;
import java.util.Map;

public class PublicarKudo implements Interaction {

    private static final String PRODUCER_BASE_URL = "http://localhost:8082";
    private static final String PRODUCER_PATH = "/api/v1/kudos";

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
        body.put("to", row.get("receptor"));
        body.put("category", row.get("categoria"));
        body.put("message", row.get("mensaje"));

        SerenityRest.given()
            .baseUri(PRODUCER_BASE_URL)
            .contentType("application/json")
            .body(body)
            .post(PRODUCER_PATH);

        int statusCode = SerenityRest.lastResponse().statusCode();
        if (statusCode != 202) {
            throw new RuntimeException(
                    "Seeder fallo. HTTP " + statusCode + " -> " + SerenityRest.lastResponse().asString());
        }
    }
}
