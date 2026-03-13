package screenplay_pattern.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class EstablecerValorSelect implements Interaction {

    private final Target target;
    private final String valor;

    private EstablecerValorSelect(Target target, String valor) {
        this.target = target;
        this.valor  = valor;
    }

    public static EstablecerValorSelect en(Target target, String valor) {
        return new EstablecerValorSelect(target, valor);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebElement elemento = target.resolveFor(actor);
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        js.executeScript(
            "var setter = Object.getOwnPropertyDescriptor(" +
            "  window.HTMLSelectElement.prototype, 'value').set;" +
            "setter.call(arguments[0], arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            elemento,
            valor
        );
    }
}
