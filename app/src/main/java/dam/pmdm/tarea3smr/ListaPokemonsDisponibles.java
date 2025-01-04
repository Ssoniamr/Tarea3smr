package dam.pmdm.tarea3smr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dam.pmdm.tarea3smr.responses.ResponseListaPokemons;
import dam.pmdm.tarea3smr.responses.ResponseUnPokemonList;
import dam.pmdm.tarea3smr.databinding.FragmentListaPokemonsDisponiblesBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragmento que muestra la lista de Pokémon disponibles.
 */
public class ListaPokemonsDisponibles extends Fragment {
    private FragmentListaPokemonsDisponiblesBinding binding;
    private PokemonsDisponiblesRecyclerviewAdapter adapter;
    private ArrayList<ResponseUnPokemonList> listaPokemonsDisponibles = new ArrayList<>();

    /**
     * Método llamado para crear la vista del fragmento.
     *
     * @param inflater           Inflador para la vista.
     * @param container          Contenedor de la vista.
     * @param savedInstanceState Estado guardado del fragmento.
     * @return La vista inflada del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListaPokemonsDisponiblesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método llamado cuando la vista del fragmento ha sido creada.
     *
     * @param view               La vista creada.
     * @param savedInstanceState Estado guardado del fragmento.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Configuración del adaptador para el RecyclerView con la lista de Pokémon disponibles
        adapter = new PokemonsDisponiblesRecyclerviewAdapter(listaPokemonsDisponibles, getActivity());
        binding.pokemonsDisponiblesRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.pokemonsDisponiblesRecyclerview.setAdapter(adapter);

        // Llamada a la API para obtener la lista de Pokémon
        ApiInterface apiService = ApiAdaptador.getApiService();
        apiService.getPokemonsList(0, 150).enqueue(new Callback<ResponseListaPokemons>() {
            // Método llamado cuando la respuesta de la API es exitosa
            @Override
            public void onResponse(Call<ResponseListaPokemons> call, Response<ResponseListaPokemons> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Añade los Pokémon obtenidos a la lista y notifica al adaptador para actualizar la vista
                    List<ResponseUnPokemonList> pokemonLists = response.body().getUnPokemonList();
                    listaPokemonsDisponibles.addAll(pokemonLists);
                    adapter.notifyDataSetChanged();
                } else {
                    // Muestra un mensaje de error si la respuesta no es exitosa
                    Toast.makeText(getContext(), "No se pudo obtener la lista de Pokémon", Toast.LENGTH_SHORT).show();
                }
            }

            // Método llamado cuando la llamada a la API falla
            @Override
            public void onFailure(Call<ResponseListaPokemons> call, Throwable t) {
                // Muestra un mensaje de error si ocurre un fallo en la llamada a la API
                Toast.makeText(getContext(), "Error al obtener la lista de Pokémon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método llamado cuando la vista del fragmento está a punto de ser destruida.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
