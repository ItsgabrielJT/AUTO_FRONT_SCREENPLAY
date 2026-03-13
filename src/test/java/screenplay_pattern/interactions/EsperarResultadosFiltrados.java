package screenplay_pattern.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay_pattern.models.KudosContexto;

import java.time.Duration;

public class EsperarResultadosFiltrados implements Interaction {

    private static final Duration ESPERA = Duration.ofSeconds(8);

    private final KudosContexto contexto;

    private EsperarResultadosFiltrados(KudosContexto contexto) {
        this.contexto = contexto;
    }

    public static EsperarResultadosFiltrados para(KudosContexto contexto) {
        return new EsperarResultadosFiltrados(contexto);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), ESPERA)
                .until(driver -> {
                    try {
                        var filas = driver.findElements(By.cssSelector("tbody tr"));
                        if (filas.isEmpty()) {
                            return false;
                        }

                        boolean categoriaOk = true;
                        if (contexto.getCategoriaSeleccionada() != null) {
                            var badges = driver.findElements(By.cssSelector("tbody tr td:nth-child(3) span"));
                            categoriaOk = !badges.isEmpty() && badges.stream()
                                    .allMatch(b -> b.getText().trim().equalsIgnoreCase(contexto.getCategoriaSeleccionada()));
                        }

                        boolean mensajeOk = true;
                        if (contexto.getMensajeBuscado() != null) {
                            var celdas = driver.findElements(By.cssSelector("tbody tr td:nth-child(4)"));
                            mensajeOk = !celdas.isEmpty() && celdas.stream()
                                    .anyMatch(c -> c.getText().contains(contexto.getMensajeBuscado()));
                        }

                        return categoriaOk && mensajeOk;
                    } catch (StaleElementReferenceException e) {
                        return false;
                    }
                });
    }
}
