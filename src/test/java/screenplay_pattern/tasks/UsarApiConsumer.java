package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import screenplay_pattern.models.ConfiguracionEntorno;

public class UsarApiConsumer implements Task {

    public UsarApiConsumer() {
    }

    public static UsarApiConsumer configurada() {
        return new UsarApiConsumer();
    }

    @Override
    @Step("{0} configura la API consumer para sus peticiones")
    public <T extends Actor> void performAs(T actor) {
        actor.can(CallAnApi.at(ConfiguracionEntorno.actual().consumerBaseUrl()));
    }
}
