package dam.pmdm.tarea3smr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import dam.pmdm.tarea3smr.databinding.FragmentListaPokemonsCapturadosBinding;
import dam.pmdm.tarea3smr.responses.ResponseDetallePokemon;

/**
 * Clase que crea el fragment de la lista de pokemons capturados.
 * contiene el arrayList que de pokemons capturados que se mostrará en un RecyclerView.
 */
public class ListaPokemosCapturados extends Fragment {

    private FragmentListaPokemonsCapturadosBinding binding;
    private ArrayList<ResponseDetallePokemon> pokemonCapturado;
    private PokemonCapturadoRecyclerViewAdapter adapter;

    /**
     * Metodo en el que se infla el diseño del fragmento contenedor de la lista de pokemons capturados.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return  retorna la vista raiz para el diseño del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListaPokemonsCapturadosBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    /**
     * Método que inicializa la lista de personajes (llamando al metodo {@link #listaPokemonsCapturados()} )
     * y pasa esta lista de personaje al RecyclerView atraves de un adactador y un LayoutManager
     * vertical.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //llamada al metodo que crea la lista de pokemons capturados
        listaPokemonsCapturados();
        //creacion del adapter y el LinerLayuotManager
        adapter = new PokemonCapturadoRecyclerViewAdapter(pokemonCapturado, getActivity());
        binding.pokemonsCapturadosRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.pokemonsCapturadosRecyclerview.setAdapter(adapter);

    }

    /**
     * Metódo que crea la lista de pokemons capturados.
     * añade objetos de tipo PokemonsData y los añade a un Arraylist
     */
    private void listaPokemonsCapturados(ResponseDetallePokemon pokemon) {
        pokemonCapturado = new ArrayList<>();
        pokemonCapturado.add(pokemon);
        adapter.notifyDataSetChanged();

    }
}