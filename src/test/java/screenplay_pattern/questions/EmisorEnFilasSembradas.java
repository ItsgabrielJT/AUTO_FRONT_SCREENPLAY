package screenplay_pattern.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.userinterface.KudosListPage;

import java.util.List;

public class EmisorEnFilasSembradas implements Question<Boolean> {

    private final KudosContexto contexto;
    private final String        emailEsperado;

    private EmisorEnFilasSembradas(KudosContexto contexto, String emailEsperado) {
        this.contexto      = contexto;
        this.emailEsperado = emailEsperado;
    }

    public static EmisorEnFilasSembradas verificada(KudosContexto contexto, String emailEsperado) {
        return new EmisorEnFilasSembradas(contexto, emailEsperado);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        List<WebElementFacade> filas    = KudosListPage.FILAS_TABLA.resolveAllFor(actor);
        List<WebElementFacade> emisores = KudosListPage.CELDAS_EMISOR.resolveAllFor(actor);

        if (filas.isEmpty() || filas.size() != emisores.size()) return false;

        long validadas = 0;
        for (int i = 0; i < filas.size(); i++) {
            String textoFila = filas.get(i).getText();
            boolean esFilaSembrada = contexto.contieneMensajeSembradoEn(textoFila);
            if (!esFilaSembrada) continue;

            String textoEmisor = emisores.get(i).getText();
            if (!textoEmisor.contains(emailEsperado)) return false;
            validadas++;
        }

        return validadas == contexto.getCantidadSembrada();
    }
}
