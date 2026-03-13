package screenplay_pattern.userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import screenplay_pattern.models.ConfiguracionEntorno;

public class KudosListPage {

      public static final String PATH = "/kudos/list";

      public static final Target FILAS_TABLA =
            Target.the("filas de la tabla Kudos")
                  .located(By.cssSelector("tbody tr"));

      public static final Target CELDAS_EMISOR =
            Target.the("celdas de emisor (columna 1)")
                  .located(By.cssSelector("tbody tr td:nth-child(1)"));

      public static final Target BADGES_CATEGORIA =
            Target.the("badges de categoría (columna 3)")
                  .located(By.cssSelector("tbody tr td:nth-child(3) span"));

      public static final Target CELDAS_MENSAJE =
            Target.the("celdas de mensaje (columna 4)")
                  .located(By.cssSelector("tbody tr td:nth-child(4)"));

      public static final Target ETIQUETA_TOTAL_ELEMENTOS =
            Target.the("etiqueta de totalElements")
                  .located(By.xpath("//p[contains(text(),'encontrad')]"));

      public static final Target FILTRO_CATEGORIA =
            Target.the("filtro por categoría")
                  .located(By.cssSelector("select[aria-label='Filtrar por categoría']"));

      public static final Target CAMPO_BUSQUEDA_MENSAJE =
            Target.the("buscador de kudos")
                  .located(By.cssSelector("input[aria-label='Buscar kudos']"));

      public static final Target BOTON_APLICAR_FILTROS =
            Target.the("botón aplicar filtros")
                  .located(By.cssSelector("button[type='submit']"));

      public static String urlDelListado() {
            return ConfiguracionEntorno.actual().webBaseUrl() + PATH;
      }
}
