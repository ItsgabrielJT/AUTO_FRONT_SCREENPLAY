package screenplay_pattern.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.actors.OnStage;
import screenplay_pattern.models.KudosContexto;
import screenplay_pattern.questions.*;
import screenplay_pattern.tasks.*;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class ListarKudosStepDefinition {

        private final KudosContexto contexto = new KudosContexto();

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }

    private Actor elEmpleado() {
        return OnStage.theActorCalled("el empleado");
    }


    @Dado("que se ha ejecutado el seeder de Kudos para {string} con las categorías:")
    public void queSeHaEjecutadoElSeederDeKudosParaConLasCategorias(String from,
                                                                     DataTable dataTable) {
        List<Map<String, String>> filas = dataTable.asMaps(String.class, String.class);
        elEmpleado().attemptsTo(SembrarKudos.para(from, filas, contexto));
    }


    @Cuando("el empleado visualiza el listado de Kudos")
    public void elEmpleadoVisualizaElListadoDeKudos() {
        elEmpleado().attemptsTo(VisualizarListadoDeKudos.enElNavegador());
    }

    @Entonces("el listado debe mostrar los registros cargados por el emisor")
    public void elListadoDebeMostrarLosRegistrosCargadosPorElEmisor() {
        elEmpleado().should(
                seeThat("registros sembrados visibles en la tabla",
                        RegistrosSembradosVisibles.enLaTabla(contexto),
                        equalTo((long) contexto.cantidadSembrada))
        );
    }

    @Y("el contador {string} debe reflejar la cantidad de registros insertados")
    public void elContadorDebeReflejarLaCantidadDeRegistrosInsertados(String campo) {
        long minEsperado = contexto.totalElementsAntesDeSeed + contexto.cantidadSembrada;
        elEmpleado().should(
                seeThat("totalElements en el sistema",
                        TotalElementosActual.enElSistema(),
                        greaterThanOrEqualTo(minEsperado))
        );
    }

    @Y("cada registro en pantalla debe mostrar a {string} como el emisor")
    public void cadaRegistroEnPantallaDebeMostrarAComoElEmisor(String email) {
        elEmpleado().should(
                seeThat("emisor en filas sembradas",
                        EmisorEnFilasSembradas.verificada(contexto, email),
                        is(true))
        );
    }


    @Cuando("el empleado filtra el listado por la categoría {string}")
    public void elEmpleadoFiltraElListadoPorLaCategoria(String categoria) {
        elEmpleado().attemptsTo(
                VisualizarListadoDeKudos.enElNavegador(),
                FiltrarPorCategoria.conValor(categoria, contexto)
        );
    }

    @Y("el empleado busca por el mensaje {string}")
    public void elEmpleadoBuscaPorElMensaje(String mensaje) {
        elEmpleado().attemptsTo(
                BuscarPorMensaje.conTexto(mensaje, contexto),
                AplicarFiltros.delEscenario(contexto)
        );
    }

    @Entonces("el listado debe mostrar los resultados que coincidan con la categoría {string}")
    public void elListadoDebeMostrarLosResultadosQueCoincidanConLaCategoria(String categoria) {
        elEmpleado().should(
                seeThat("categorías visibles en la tabla",
                        CategoriasVisibles.enLaTabla(),
                        everyItem(equalToIgnoringCase(categoria)))
        );
    }

    @Y("el mensaje de los registros visibles debe ser {string}")
    public void elMensajeDeLosRegistrosVisiblesDebeSer(String mensaje) {
        String mensajeUnico = contexto.mensajesUnicos.getOrDefault(mensaje, mensaje);
        elEmpleado().should(
                seeThat("mensajes visibles en la tabla",
                        MensajesVisibles.enLaTabla(),
                        hasItem(containsString(mensajeUnico)))
        );
    }
}
