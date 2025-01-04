package dam.pmdm.tarea3smr;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dam.pmdm.tarea3smr.responses.ResponseTipoPokemon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import dam.pmdm.tarea3smr.databinding.FragmentListaPokemonsCapturadosBinding;
import dam.pmdm.tarea3smr.responses.ResponseDetallePokemon;

/**
 * Clase que crea el fragmento de la lista de pokemons capturados.
 * Contiene el arrayList de pokemons capturados que se mostrará en un RecyclerView.
 */
public class ListaPokemosCapturados extends Fragment {

    private FragmentListaPokemonsCapturadosBinding binding;
    private ArrayList<ResponseDetallePokemon> pokemonCapturado = new ArrayList<>();
    private PokemonCapturadoRecyclerViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Método en el que se infla el diseño del fragmento contenedor de la lista de pokemons capturados.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return retorna la vista raíz para el diseño del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListaPokemonsCapturadosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método que inicializa la lista de pokemons capturados (llamando al metodo {@link #anadirPokemonCapturado(ResponseDetallePokemon)} )
     * y pasa esta lista de personajes al RecyclerView a través de un adaptador y un LayoutManager
     * vertical.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializa el adaptador y configura el RecyclerView
        adapter = new PokemonCapturadoRecyclerViewAdapter(pokemonCapturado, getActivity());
        binding.pokemonsCapturadosRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.pokemonsCapturadosRecyclerview.setAdapter(adapter);

        // Verifica si se permite la eliminación de pokemons
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean permitirEliminacion = preferences.getBoolean("eliminar_pokemon", false);
        adapter.setDeletionEnabled(permitirEliminacion);

        // Configura el ItemTouchHelper para manejar deslizamientos si se permite la eliminación
        if (permitirEliminacion) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false; // No se manejan movimientos
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    // Elimina el elemento deslizado
                    int position = viewHolder.getAdapterPosition();
                    adapter.eliminarPokemon(position);
                }
            });
            itemTouchHelper.attachToRecyclerView(binding.pokemonsCapturadosRecyclerview);
        }

        // Actualiza la lista de pokemons capturados
        actualizarListaPokemonsCapturados();

        // Si hay argumentos, obtiene detalles del Pokémon
        if (getArguments() != null) {
            String pokemonName = getArguments().getString("pokemonName");
            obtenerDetallesPokemon(pokemonName);
        }
    }


    /**
     * Método que añade un Pokémon capturado a la lista.
     */
    public void anadirPokemonCapturado(ResponseDetallePokemon pokemon) {
        if (pokemon != null && !pokemonCapturado.contains(pokemon)) {
            pokemonCapturado.add(pokemon);
            adapter.notifyItemInserted(pokemonCapturado.size() - 1);
        }
    }

    /**
     * Método para obtener los detalles del Pokémon capturado.
     */
    public void obtenerDetallesPokemon(String pokemonName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Consulta a FirebaseFirestore para verificar si el Pokémon ya está almacenado
        db.collection("capturedPokemons").document(pokemonName).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Si el documento existe, significa que el Pokémon ya está en Firebase
                } else {
                    // Si el documento no existe, realiza una llamada a la API para obtener los detalles del Pokémon
                    ApiInterface apiService = ApiAdaptador.getApiService();
                    Call<ResponseDetallePokemon> call = apiService.getDetallePokemon(pokemonName);
                    call.enqueue(new Callback<ResponseDetallePokemon>() {
                        @Override
                        public void onResponse(Call<ResponseDetallePokemon> call, Response<ResponseDetallePokemon> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ResponseDetallePokemon detallesPokemon = response.body();

                                // Obtiene el índice del Pokémon
                                detallesPokemon.getIndex();

                                // Verifica y procesa los tipos del Pokémon
                                if (detallesPokemon.getTypes() != null) {
                                    for (ResponseTipoPokemon tipo : detallesPokemon.getTypes()) {
                                        if (tipo.getType() != null && tipo.getType().getName() != null) {
                                            // Tipo obtenido correctamente
                                        }
                                    }
                                }

                                // Verifica y procesa los sprites del Pokémon
                                if (detallesPokemon.getSprites() != null && detallesPokemon.getSprites().getFrontDefault() != null) {
                                    detallesPokemon.setSprite(detallesPokemon.getSprites().getFrontDefault());
                                }

                                // Añade el Pokémon capturado a la lista y guárdalo en Firebase
                                anadirPokemonCapturado(detallesPokemon);
                                ((MainActivity) getActivity()).guardarPokemonEnFirebase(detallesPokemon);
                            } else {
                                // Muestra un mensaje de error si la respuesta de la API no es exitosa
                                Toast.makeText(getContext(), "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseDetallePokemon> call, Throwable t) {
                            // Muestra un mensaje de error si falla la llamada a la API
                        }
                    });
                }
            } else {
                // Muestra un mensaje de error si falla la consulta a Firebase
            }
        });
    }


    /**
     * Método para actualizar la lista de pokemons capturados.
     */
    private void actualizarListaPokemonsCapturados() {
        // Limpiar la lista antes de actualizarla con nuevos datos
        pokemonCapturado.clear();

        // Obtener una instancia de FirebaseFirestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Consultar la colección "capturedPokemons" en Firestore
        db.collection("capturedPokemons")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Si la consulta es exitosa, iterar sobre los documentos obtenidos
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convertir cada documento en un objeto ResponseDetallePokemon
                            ResponseDetallePokemon pokemon = document.toObject(ResponseDetallePokemon.class);
                            if (pokemon != null) {
                                // Verificar y procesar el sprite del Pokémon
                                if (pokemon.getSprite() != null) {
                                }

                                // Verificar y procesar los tipos del Pokémon
                                if (pokemon.getTypes() != null) {
                                    List<String> tiposPokemon = new ArrayList<>();
                                    for (Map<String, String> tipoMap : (List<Map<String, String>>) document.get("types")) {
                                        if (tipoMap != null && tipoMap.get("name") != null) {
                                            tiposPokemon.add(tipoMap.get("name"));
                                        }
                                    }
                                }

                                // Añadir el Pokémon a la lista si no está ya presente
                                if (!pokemonCapturado.contains(pokemon)) {
                                    pokemonCapturado.add(pokemon);
                                }
                            }
                        }
                        // Notificar al adaptador que los datos han cambiado para que se actualice la vista
                        adapter.notifyDataSetChanged();
                    } else {
                        // Manejar el error si la consulta no es exitosa
                    }
                });
    }

}
