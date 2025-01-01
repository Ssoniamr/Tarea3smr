package dam.pmdm.tarea3smr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dam.pmdm.tarea3smr.databinding.FragmentPokemonsCapturadosCardviewBinding;
import dam.pmdm.tarea3smr.databinding.FragmentPokemonsDisponiblesCardViewBinding;
import dam.pmdm.tarea3smr.responses.ResponseListaPokemons;
import dam.pmdm.tarea3smr.responses.ResponseUnPokemonList;

public class PokemonsDisponiblesRecyclerviewAdapter  extends  RecyclerView.Adapter<PokemonsDisponiblesCardView>{

    private final ArrayList<ResponseUnPokemonList> pokemonDisponibles;
    private final Context context;

    /**
     * Constructor para inicializar el adactador
     *
     * @param pokemonDisponibles indica la lista de pokemons disponibles.
     * @param context          indica el contexto de la actividad.
     */
    public PokemonsDisponiblesRecyclerviewAdapter(ArrayList<ResponseUnPokemonList> pokemonDisponibles, Context context) {
        this.pokemonDisponibles = pokemonDisponibles;
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
    public PokemonsDisponiblesCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentPokemonsDisponiblesCardViewBinding binding = FragmentPokemonsDisponiblesCardViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PokemonsDisponiblesCardView(binding);
    }

    /**
     * vincula los datos del pokemonDisponibles actual a viewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull PokemonsDisponiblesCardView holder, int position) {
        ResponseUnPokemonList pokemonActual = this.pokemonDisponibles.get(position);
        holder.bind(pokemonActual);

        holder.itemView.setOnClickListener(view -> itemcliked(pokemonActual, view));

    }

    /**
     * Método que maneja el evento clic sobre el item, indica hacia donde navegar llamando al metodo
     * {@link MainActivity#pokemonDisponiblesClicked(ResponseUnPokemonList)}
     *
     * @param pokemonActual indica el objeto PokemonData actual.
     * @param view          indica la vista del item que se clicó.
     */
    private void itemcliked(ResponseUnPokemonList pokemonActual, View view) {
        ((MainActivity) context).pokemonDisponiblesClicked(pokemonActual);
        Toast.makeText(context,"Has capturado a " + pokemonActual.getName(),Toast.LENGTH_SHORT).show();
    }

    /**
     * Obtiene el tamaño del Arraylist que contiene la lista de pokemons capturados.
     *
     * @return retorna el numero de posiciones del Arraylist que contiene la lista de pokemons capturados.
     */
    @Override
    public int getItemCount() {
        return pokemonDisponibles.size();
    }
}



