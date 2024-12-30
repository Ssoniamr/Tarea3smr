
package dam.pmdm.tarea3smr.responses;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseListaPokemons {

    @SerializedName("results")
    @Expose
    private List<ResponseUnPokemonList> unPokemonList;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseListaPokemons() {
    }

    public ResponseListaPokemons(List<ResponseUnPokemonList> unPokemonList) {
        super();
        this.unPokemonList = unPokemonList;
    }

    public List<ResponseUnPokemonList> getUnPokemonLists() {
        return unPokemonList;
    }

    public void setUnPokemonLists(List<ResponseUnPokemonList> unPokemonLists) {
        this.unPokemonList = unPokemonLists;
    }

    public ResponseListaPokemons withResults(List<ResponseUnPokemonList> results) {
        this.unPokemonList = results;
        return this;
    }

}

