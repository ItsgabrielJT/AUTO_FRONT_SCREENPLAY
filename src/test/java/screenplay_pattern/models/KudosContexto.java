package screenplay_pattern.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KudosContexto {

    private long totalElementsAntesDeSeed;
    private int cantidadSembrada;
    private final Map<String, String> mensajesUnicos = new HashMap<>();
    private String categoriaSeleccionada;
    private String mensajeBuscado;

    public void reset() {
        mensajesUnicos.clear();
        categoriaSeleccionada = null;
        mensajeBuscado = null;
        cantidadSembrada = 0;
        totalElementsAntesDeSeed = 0;
    }

    public long getTotalElementsAntesDeSeed() {
        return totalElementsAntesDeSeed;
    }

    public void setTotalElementsAntesDeSeed(long totalElementsAntesDeSeed) {
        this.totalElementsAntesDeSeed = totalElementsAntesDeSeed;
    }

    public int getCantidadSembrada() {
        return cantidadSembrada;
    }

    public void setCantidadSembrada(int cantidadSembrada) {
        this.cantidadSembrada = cantidadSembrada;
    }

    public String getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(String categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public String getMensajeBuscado() {
        return mensajeBuscado;
    }

    public void setMensajeBuscado(String mensajeBuscado) {
        this.mensajeBuscado = mensajeBuscado;
    }

    public void registrarMensajeUnico(String mensajeBase, String mensajeUnico) {
        mensajesUnicos.put(mensajeBase, mensajeUnico);
    }

    public String mensajeUnicoPara(String mensajeBase) {
        return mensajesUnicos.getOrDefault(mensajeBase, mensajeBase);
    }

    public boolean contieneMensajeSembradoEn(String texto) {
        return mensajesUnicos.values().stream().anyMatch(texto::contains);
    }

    public List<String> mensajesSembrados() {
        return List.copyOf(mensajesUnicos.values());
    }
}
