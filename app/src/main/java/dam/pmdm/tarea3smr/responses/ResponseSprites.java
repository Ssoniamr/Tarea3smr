package dam.pmdm.tarea3smr.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa los sprites de un Pokémon.
 */
public class ResponseSprites {

    @SerializedName("front_default")
    @Expose
    private String frontDefault;

    /**
     * Constructor vacío para la clase.
     */
    public ResponseSprites() {
    }

    /**
     * Constructor con el sprite por defecto.
     *
     * @param frontDefault URL del sprite frontal por defecto.
     */
    public ResponseSprites(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    // Métodos getter y setter para el sprite frontal por defecto

    /**
     * Obtiene la URL del sprite frontal por defecto.
     *
     * @return URL del sprite frontal por defecto.
     */
    public String getFrontDefault() {
        return frontDefault;
    }

    /**
     * Establece la URL del sprite frontal por defecto.
     *
     * @param frontDefault URL del sprite frontal por defecto.
     */
    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }
}
