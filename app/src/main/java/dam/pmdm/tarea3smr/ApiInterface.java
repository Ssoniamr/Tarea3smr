package dam.pmdm.tarea3smr;

import dam.pmdm.tarea3smr.responses.ResponseDetallePokemon;
import dam.pmdm.tarea3smr.responses.ResponseListaPokemons;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interfaz para definir las llamadas a la API de PokeAPI.
 */
public interface ApiInterface {

    /**
     * Método para obtener la lista de Pokémon.
     *
     * @param offset Desplazamiento para la paginación.
     * @param limit  Límite de resultados por página.
     * @return Llamada con la lista de Pokémon.
     */
    @GET("pokemon")
    Call<ResponseListaPokemons> getPokemonsList(@Query("offset") int offset, @Query("limit") int limit);

    /**
     * Método para obtener los detalles de un Pokémon.
     *
     * @param name Nombre del Pokémon.
     * @return Llamada con los detalles del Pokémon.
     */
    @GET("pokemon/{name}")
    Call<ResponseDetallePokemon> getDetallePokemon(@Path("name") String name);
}
