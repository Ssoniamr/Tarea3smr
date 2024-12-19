package dam.pmdm.tarea3smr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dam.pmdm.tarea3smr.databinding.FragmentPokemonsCapturadosCardviewBinding;
import dam.pmdm.tarea3smr.databinding.FragmentPokemonsCapturadosCardviewBinding;

/**
 * Clase adaptador para mostrar una lista de personajes en un RecyclerView
 */
public class PokemonCapturadoRecyclerViewAdapter extends RecyclerView.Adapter<PokemonsCapturadosCardview> {

    private final ArrayList<PokemonData> pokemonCapturado;
    private final Context context;

    /**
     * Constructor para inicializar el adactador
     *
     * @param pokemonCapturado indica la lista de pokemons capturados.
     * @param context          indica el contexto de la actividad.
     */
    public PokemonCapturadoRecyclerViewAdapter(ArrayList<PokemonData> pokemonCapturado, Context context) {
        this.pokemonCapturado = pokemonCapturado;
        this.context = context;
    }

    /**
     * Método que crea un nuevo viewHolder
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return retorna el nuevo viewHolder creado
     */
    @NonNull
    @Override
    public PokemonsCapturadosCardview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentPokemonsCapturadosCardviewBinding binding = FragmentPokemonsCapturadosCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PokemonsCapturadosCardview(binding);
    }

    /**
     * vincula los datos del pokemonCapturado actual a viewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull PokemonsCapturadosCardview holder, int position) {
        PokemonData pokemonActual = this.pokemonCapturado.get(position);
        holder.bind(pokemonActual);

        holder.itemView.setOnClickListener(view -> itemcliked(pokemonActual, view));

    }

    /**
     * Método que maneja el evento clic sobre el item, indica hacia donde navegar llamando al metodo
     * {@link MainActivity#pokemonCapturadoClicked(PokemonDataData, View)}
     *
     * @param pokemonActual indica el objeto PokemonData actual.
     * @param view          indica la vista del item que se clicó.
     */
    private void itemcliked(PokemonData pokemonActual, View view) {
        ((MainActivity) context).pokemonCapturadoClicked(pokemonActual, view);
    }

    /**
     * Obtiene el tamaño del Arraylist que contiene la lista de pokemons capturados.
     *
     * @return retorna el numero de posiciones del Arraylist que contiene la lista de pokemons capturados.
     */
    @Override
    public int getItemCount() {
        return pokemonCapturado.size();
    }
}
