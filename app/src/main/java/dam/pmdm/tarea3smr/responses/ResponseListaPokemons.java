package dam.pmdm.tarea3smr.responses;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa una lista de Pokémon.
 */
public class ResponseListaPokemons {

    @SerializedName("results")
    @Expose
    private List<ResponseUnPokemonList> unPokemonList;

    /**
     * Constructor vacío para la clase.
     */
    public ResponseListaPokemons() {
    }

    /**
     * Constructor con la lista de Pokémon.
     *
     * @param unPokemonList Lista de Pokémon.
     */
    public ResponseListaPokemons(List<ResponseUnPokemonList> unPokemonList) {
        this.unPokemonList = unPokemonList;
    }

    // Métodos getter y setter para la lista de Pokémon

    /**
     * Obtiene la lista de Pokémon.
     *
     * @return Lista de Pokémon.
     */
    public List<ResponseUnPokemonList> getUnPokemonList() {
        return unPokemonList;
    }

    /**
     * Establece la lista de Pokémon.
     *
     * @param unPokemonList Lista de Pokémon.
     */
    public void setUnPokemonList(List<ResponseUnPokemonList> unPokemonList) {
        this.unPokemonList = unPokemonList;
    }
}
