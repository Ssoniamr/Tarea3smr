package dam.pmdm.tarea3smr;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
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

import dam.pmdm.tarea3smr.responses.ResponseTipoPokemon;
import dam.pmdm.tarea3smr.responses.ResponseType;
import dam.pmdm.tarea3smr.responses.ResponseUnPokemonList;
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
        Log.d("CicloDeVida", "onCreate - ListaPokemosCapturados");
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
        Log.d("CicloDeVida", "onCreateView - ListaPokemosCapturados");
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
        Log.d("CicloDeVida", "onViewCreated - ListaPokemosCapturados");

        adapter = new PokemonCapturadoRecyclerViewAdapter(pokemonCapturado, getActivity());
        binding.pokemonsCapturadosRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.pokemonsCapturadosRecyclerview.setAdapter(adapter);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean permitirEliminacion = preferences.getBoolean("eliminar_pokemon", false);
        adapter.setDeletionEnabled(permitirEliminacion);

        if (permitirEliminacion) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    adapter.eliminarPokemon(position);
                }
            });
            itemTouchHelper.attachToRecyclerView(binding.pokemonsCapturadosRecyclerview);
        }

        actualizarListaPokemonsCapturados();

        if (getArguments() != null) {
            String pokemonName = getArguments().getString("pokemonName");
            Log.d("Pokemon", "Argumentos recibidos: " + pokemonName);
            obtenerDetallesPokemon(pokemonName);
        } else {
            Log.d("Pokemon", "No se recibieron argumentos");
        }
    }

    /**
     * Método que añade un Pokémon capturado a la lista.
     */
    public void anadirPokemonCapturado(ResponseDetallePokemon pokemon) {
        if (pokemon != null && !pokemonCapturado.contains(pokemon)) {
            pokemonCapturado.add(pokemon);
            adapter.notifyItemInserted(pokemonCapturado.size() - 1);
            Log.d("Pokemon", "Pokémon añadido a la lista: " + pokemon.getName());
        } else {
            Log.e("Pokemon", "Pokémon nulo o duplicado no añadido a la lista");
        }
    }

    /**
     * Método para obtener los detalles del Pokémon capturado.
     */
    public void obtenerDetallesPokemon(String pokemonName) {
        Log.d("Pokemon", "obtenerDetallesPokemon: " + pokemonName);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("capturedPokemons").document(pokemonName).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("Pokemon", "El Pokémon ya existe en Firebase: " + pokemonName);
                } else {
                    ApiInterface apiService = ApiAdaptador.getApiService();
                    Call<ResponseDetallePokemon> call = apiService.getDetallePokemon(pokemonName);
                    call.enqueue(new Callback<ResponseDetallePokemon>() {
                        @Override
                        public void onResponse(Call<ResponseDetallePokemon> call, Response<ResponseDetallePokemon> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ResponseDetallePokemon detallesPokemon = response.body();
                                Log.d("Pokemon", "Detalles obtenidos: " + detallesPokemon.getName());

                                if (detallesPokemon.getSprites() != null) {
                                    Log.d("Pokemon", "Sprites obtenidos: " + detallesPokemon.getSprites().getFrontDefault());
                                } else {
                                    Log.e("Pokemon", "ResponseSprites es nulo");
                                }

                                anadirPokemonCapturado(detallesPokemon);
                                ((MainActivity) getActivity()).guardarPokemonEnFirebase(detallesPokemon);
                            } else {
                                Log.e("Pokemon", "Error en la respuesta de la API: " + response.errorBody());
                                Toast.makeText(getContext(), "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseDetallePokemon> call, Throwable t) {
                            Log.e("Pokemon", "Error al obtener los detalles del Pokémon", t);
                        }
                    });
                }
            } else {
                Log.e("Firebase", "Error al verificar la existencia del Pokémon en Firebase", task.getException());
            }
        });
    }


    /**
     * Método para actualizar la lista de pokemons capturados.
     */
    private void actualizarListaPokemonsCapturados() {
        pokemonCapturado.clear(); // Limpiar la lista antes de actualizar
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("capturedPokemons")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Asegurarse de que los datos se deserializan correctamente
                            ResponseDetallePokemon pokemon = document.toObject(ResponseDetallePokemon.class);
                            if (pokemon != null) {
                                Log.d("Pokemon", "Pokemon deserializado: " + pokemon.getName());
                                Log.d("Pokemon", "Index: " + pokemon.getIndex());
                                Log.d("Pokemon", "Weight: " + pokemon.getWeight());
                                Log.d("Pokemon", "Height: " + pokemon.getHeight());
                                // Verificar que los datos importantes no son nulos
                                if (pokemon.getSprites() != null && pokemon.getSprites().getFrontDefault() != null) {
                                    Log.d("Pokemon", "URL de la imagen: " + pokemon.getSprites().getFrontDefault());
                                } else {
                                    Log.e("Pokemon", "Error al deserializar imagenUrl - es nulo");
                                }
                                if (pokemon.getTypes() != null) {
                                    for (ResponseTipoPokemon tipo : pokemon.getTypes()) {
                                        if (tipo != null && tipo.getType() != null && tipo.getType().getName() != null) {
                                            Log.d("Pokemon", "Type: " + tipo.getType().getName());
                                        } else {
                                            Log.e("Pokemon", "Error al deserializar el tipo - es nulo");
                                        }
                                    }
                                } else {
                                    Log.e("Pokemon", "Types es nulo");
                                }
                                if (!pokemonCapturado.contains(pokemon)) {
                                    pokemonCapturado.add(pokemon);
                                }
                            } else {
                                Log.e("Pokemon", "Error al deserializar el Pokemon - Pokemon es nulo");
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("Pokemon", "Error getting documents.", task.getException());
                    }
                });
    }


}
