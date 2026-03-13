package screenplay_pattern.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.userinterface.KudosListPage;

import java.time.Duration;

public class AplicarFiltros implements Task {

    private static final Duration ESPERA = Duration.ofSeconds(8);

    private final KudosContexto contexto;

    private AplicarFiltros(KudosContexto contexto) {
        this.contexto = contexto;
    }

    public static AplicarFiltros delEscenario(KudosContexto contexto) {
        return new AplicarFiltros(contexto);
    }

    @Override
    @Step("{0} aplica los filtros y espera el resultado actualizado")
    public <T extends Actor> void performAs(T actor) {
        KudosListPage.BOTON_APLICAR_FILTROS.resolveFor(actor).click();
        esperarResultadosFiltrados(actor);
    }

    private void esperarResultadosFiltrados(Actor actor) {
        new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), ESPERA)
                .until(driver -> {
                    try {
                        var filas = driver.findElements(By.cssSelector("tbody tr"));
                        if (filas.isEmpty()) return false;

                        boolean categoriaOk = true;
                        if (contexto.categoriaSeleccionada != null) {
                            var badges = driver.findElements(
                                    By.cssSelector("tbody tr td:nth-child(3) span"));
                            categoriaOk = !badges.isEmpty() && badges.stream()
                                    .allMatch(b -> b.getText().trim()
                                            .equalsIgnoreCase(contexto.categoriaSeleccionada));
                        }

                        boolean mensajeOk = true;
                        if (contexto.mensajeBuscado != null) {
                            var celdas = driver.findElements(
                                    By.cssSelector("tbody tr td:nth-child(4)"));
                            mensajeOk = !celdas.isEmpty() && celdas.stream()
                                    .anyMatch(c -> c.getText().contains(contexto.mensajeBuscado));
                        }

                        return categoriaOk && mensajeOk;
                    } catch (StaleElementReferenceException e) {
                        return false;
                    }
                });
    }
}
