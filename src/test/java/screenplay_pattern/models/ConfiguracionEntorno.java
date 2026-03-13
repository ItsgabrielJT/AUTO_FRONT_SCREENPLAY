package screenplay_pattern.models;

import net.serenitybdd.model.environment.ConfiguredEnvironment;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;

import java.util.Map;

public class ConfiguracionEntorno {

    private static final String WEB_BASE_URL = "webdriver.base.url";
    private static final String PRODUCER_BASE_URL = "api.producer.base.url";
    private static final String CONSUMER_BASE_URL = "api.consumer.base.url";

    private static final Map<String, String> VALORES_POR_DEFECTO = Map.of(
            WEB_BASE_URL, "http://localhost:5173",
            PRODUCER_BASE_URL, "http://localhost:8082",
            CONSUMER_BASE_URL, "http://localhost:8081"
    );

    private final EnvironmentVariables environmentVariables;
    private final EnvironmentSpecificConfiguration environmentConfiguration;

    private ConfiguracionEntorno(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
        this.environmentConfiguration = EnvironmentSpecificConfiguration.from(environmentVariables);
    }

    public static ConfiguracionEntorno actual() {
        return new ConfiguracionEntorno(ConfiguredEnvironment.getEnvironmentVariables());
    }

    public String webBaseUrl() {
        return valorRequerido(WEB_BASE_URL);
    }

    public String producerBaseUrl() {
        return valorRequerido(PRODUCER_BASE_URL);
    }

    public String consumerBaseUrl() {
        return valorRequerido(CONSUMER_BASE_URL);
    }

    private String valorRequerido(String key) {
        String value = environmentConfiguration.getOptionalProperty(key)
                .orElse(environmentVariables.getProperty(key));

        if (value == null || value.isBlank()) {
            value = VALORES_POR_DEFECTO.get(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("No se encontro configuracion para " + key);
        }
        return value;
    }
}
