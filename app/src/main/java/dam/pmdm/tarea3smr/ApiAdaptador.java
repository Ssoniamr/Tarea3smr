package dam.pmdm.tarea3smr;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase que proporciona un adaptador para realizar peticiones a la API de PokeAPI.
 */
public class ApiAdaptador {
    private static ApiInterface API_SERVICE;
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    /**
     * Método para obtener el servicio de la API.
     *
     * @return Instancia de ApiInterface para realizar peticiones a la API.
     */
    public static ApiInterface getApiService() {
        // Creamos el cliente HTTP
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // Asociamos el cliente configurado a Retrofit
                    .build();

            API_SERVICE = retrofit.create(ApiInterface.class);
        }

        return API_SERVICE;
    }
}
