package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay_pattern.userinterface.KudosListPage;

import java.time.Duration;

public class VisualizarListadoDeKudos implements Task {

    private static final Duration ESPERA = Duration.ofSeconds(8);

    public VisualizarListadoDeKudos() {}

    public static VisualizarListadoDeKudos enElNavegador() {
        return new VisualizarListadoDeKudos();
    }

    @Override
    @Step("{0} navega al listado de Kudos y espera que la tabla cargue")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(KudosListPage.urlDelListado()));
        new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), ESPERA)
                .until(driver -> !driver.findElements(By.cssSelector("tbody tr")).isEmpty());
    }
}
