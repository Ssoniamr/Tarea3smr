package dam.pmdm.tarea3smr.responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseListaPokemons {

    @SerializedName("results")
    @Expose
    private List<ResponseUnPokemonList> unPokemonList;

    public ResponseListaPokemons() {
    }

    public ResponseListaPokemons(List<ResponseUnPokemonList> unPokemonList) {
        this.unPokemonList = unPokemonList;
    }

    public List<ResponseUnPokemonList> getUnPokemonList() {
        return unPokemonList;
    }

    public void setUnPokemonList(List<ResponseUnPokemonList> unPokemonList) {
        this.unPokemonList = unPokemonList;
    }
}
