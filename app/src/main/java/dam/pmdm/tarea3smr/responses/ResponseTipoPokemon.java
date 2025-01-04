package dam.pmdm.tarea3smr.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa el tipo de un Pokémon.
 */
public class ResponseTipoPokemon {

    @SerializedName("type")
    @Expose
    private ResponseType type;

    /**
     * Constructor vacío para la clase.
     */
    public ResponseTipoPokemon() {
    }

    /**
     * Constructor con el tipo de Pokémon.
     *
     * @param type Tipo de Pokémon.
     */
    public ResponseTipoPokemon(ResponseType type) {
        this.type = type;
    }

    // Métodos getter y setter para el tipo de Pokémon

    /**
     * Obtiene el tipo de Pokémon.
     *
     * @return Tipo de Pokémon.
     */
    public ResponseType getType() {
        return type;
    }

    /**
     * Establece el tipo de Pokémon.
     *
     * @param type Tipo de Pokémon.
     */
    public void setType(ResponseType type) {
        this.type = type;
    }
}
