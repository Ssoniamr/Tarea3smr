package dam.pmdm.tarea3smr.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa un Pokémon en una lista.
 */
public class ResponseUnPokemonList {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("url")
    @Expose
    private String url;

    /**
     * Constructor vacío para la clase.
     */
    public ResponseUnPokemonList() {
    }

    /**
     * Constructor con nombre y URL del Pokémon.
     *
     * @param name Nombre del Pokémon.
     * @param url  URL del Pokémon.
     */
    public ResponseUnPokemonList(String name, String url) {
        this.name = name;
        this.url = url;
    }

    // Métodos getter y setter para el nombre y la URL del Pokémon

    /**
     * Obtiene el nombre del Pokémon.
     *
     * @return Nombre del Pokémon.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del Pokémon.
     *
     * @param name Nombre del Pokémon.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la URL del Pokémon.
     *
     * @return URL del Pokémon.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL del Pokémon.
     *
     * @param url URL del Pokémon.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
