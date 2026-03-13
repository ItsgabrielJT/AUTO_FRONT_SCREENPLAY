package screenplay_pattern.models;

import java.util.HashMap;
import java.util.Map;

public class KudosContexto {

    public long  totalElementsAntesDeSeed;
    public int   cantidadSembrada;
    public final Map<String, String> mensajesUnicos = new HashMap<>();
    public String categoriaSeleccionada;
    public String mensajeBuscado;

    public void reset() {
        mensajesUnicos.clear();
        categoriaSeleccionada = null;
        mensajeBuscado        = null;
        cantidadSembrada      = 0;
    }
}
