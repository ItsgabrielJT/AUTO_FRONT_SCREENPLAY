package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import screenplay_pattern.models.ConfiguracionEntorno;

public class UsarApiProducer implements Task {

    public UsarApiProducer() {
    }

    public static UsarApiProducer configurada() {
        return new UsarApiProducer();
    }

    @Override
    @Step("{0} configura la API producer para sus peticiones")
    public <T extends Actor> void performAs(T actor) {
        actor.can(CallAnApi.at(ConfiguracionEntorno.actual().producerBaseUrl()));
    }
}
