package dam.pmdm.tarea3smr;

import java.util.List;

import dam.pmdm.tarea3smr.responses.ResponseDetallePokemon;
import dam.pmdm.tarea3smr.responses.ResponseListaPokemons;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("pokemon")
    Call<ResponseListaPokemons> getPokemonsList(@Query("offset") int offset, @Query("limit") int limit);

   @GET("pokemon/{name}")
    Call<ResponseDetallePokemon> getdetallepokemon(@Path("name") String name);
}
