package dam.pmdm.tarea3smr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import dam.pmdm.tarea3smr.responses.ResponseListaPokemons;
import dam.pmdm.tarea3smr.databinding.FragmentListaPokemonsDisponiblesBinding;
import dam.pmdm.tarea3smr.responses.ResponseUnPokemonList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaPokemonsDisponibles extends Fragment {
    private FragmentListaPokemonsDisponiblesBinding binding;
    private PokemonsDisponiblesRecyclerviewAdapter adapter;
    private ArrayList<ResponseUnPokemonList> listaPokemonsDisponibles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListaPokemonsDisponiblesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new PokemonsDisponiblesRecyclerviewAdapter(listaPokemonsDisponibles, getActivity());
        binding.pokemonsDisponiblesRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.pokemonsDisponiblesRecyclerview.setAdapter(adapter);

        ApiInterface apiService = ApiAdaptador.getApiService();
        apiService.getPokemonsList(0, 150).enqueue(new Callback<ResponseListaPokemons>() {
            @Override
            public void onResponse(Call<ResponseListaPokemons> call, Response<ResponseListaPokemons> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ResponseUnPokemonList> pokemonLists = response.body().getUnPokemonLists();
                        listaPokemonsDisponibles.addAll(pokemonLists);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override public void onFailure(Call<ResponseListaPokemons> call, Throwable t) {

                Log.e("API error", "no se pudo obtener la lista");
                }
        });
    }

    @Override public void onDestroyView() {
        super.onDestroyView(); binding = null;
    }
}