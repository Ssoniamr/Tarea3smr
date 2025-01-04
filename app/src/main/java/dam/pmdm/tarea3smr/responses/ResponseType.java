package dam.pmdm.tarea3smr.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa el tipo de un Pokémon.
 */
public class ResponseType {

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Constructor vacío para la clase.
     */
    public ResponseType() {
    }

    /**
     * Constructor con el nombre del tipo.
     *
     * @param name Nombre del tipo.
     */
    public ResponseType(String name) {
        this.name = name;
    }

    // Métodos getter y setter para el nombre del tipo

    /**
     * Obtiene el nombre del tipo.
     *
     * @return Nombre del tipo.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del tipo.
     *
     * @param name Nombre del tipo.
     */
    public void setName(String name) {
        this.name = name;
    }
}
