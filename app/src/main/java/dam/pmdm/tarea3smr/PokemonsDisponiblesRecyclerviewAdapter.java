package dam.pmdm.tarea3smr;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dam.pmdm.tarea3smr.databinding.FragmentPokemonsDisponiblesCardViewBinding;
import dam.pmdm.tarea3smr.responses.ResponseUnPokemonList;

/**
 * Clase adaptador para mostrar una lista de Pokémon disponibles en un RecyclerView.
 */
public class PokemonsDisponiblesRecyclerviewAdapter extends RecyclerView.Adapter<PokemonsDisponiblesCardView> {

    private final ArrayList<ResponseUnPokemonList> pokemonDisponibles;
    private final Context context;

    /**
     * Constructor para inicializar el adaptador.
     *
     * @param pokemonDisponibles lista de Pokémon disponibles.
     * @param context            contexto de la actividad.
     */
    public PokemonsDisponiblesRecyclerviewAdapter(ArrayList<ResponseUnPokemonList> pokemonDisponibles, Context context) {
        this.pokemonDisponibles = pokemonDisponibles;
        this.context = context;
    }

    /**
     * Método que crea un nuevo ViewHolder.
     *
     * @param parent   ViewGroup al que se añadirá la nueva vista después de ser vinculada a una posición del adaptador.
     * @param viewType tipo de la nueva vista.
     * @return el nuevo ViewHolder creado.
     */
    @NonNull
    @Override
    public PokemonsDisponiblesCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentPokemonsDisponiblesCardViewBinding binding = FragmentPokemonsDisponiblesCardViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PokemonsDisponiblesCardView(binding);
    }

    /**
     * Vincula los datos del Pokémon disponible actual al ViewHolder.
     *
     * @param holder   ViewHolder que debe ser actualizado para representar los contenidos del elemento en la posición dada.
     * @param position posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull PokemonsDisponiblesCardView holder, int position) {
        ResponseUnPokemonList pokemonActual = this.pokemonDisponibles.get(position);

        // Cambiar color si está capturado
        if (pokemonActual.isCapturado()) {
            int noDisponible = ContextCompat.getColor(context, R.color.gris);
            holder.binding.nombrePokemonDisponible.setTextColor(noDisponible);
        } else {
            int disponible = ContextCompat.getColor(context, R.color.Azul_oscuro);
            holder.binding.nombrePokemonDisponible.setTextColor(disponible);
        }

        // Vincula los datos del Pokémon disponible al ViewHolder
        holder.bind(pokemonActual);

        // Maneja el evento de clic sobre el ítem
        holder.itemView.setOnClickListener(view -> itemClicked(pokemonActual, view));
    }

    /**
     * Método que maneja el evento de clic sobre el ítem, indica hacia dónde navegar llamando al método
     * {@link MainActivity#pokemonDisponiblesClicked(ResponseUnPokemonList)}
     *
     * @param pokemonActual objeto PokemonData actual.
     * @param view          vista del ítem que se clicó.
     */
    private void itemClicked(ResponseUnPokemonList pokemonActual, View view) {
        if (pokemonActual.isCapturado()) {
            // Mostrar un mensaje si el Pokémon ya fue capturado
            Toast.makeText(context, "¡Este Pokémon ya fue capturado!", Toast.LENGTH_SHORT).show();
        } else {
            // Capturar el Pokémon y actualizar el estado
            ((MainActivity) context).pokemonDisponiblesClicked(pokemonActual);
            pokemonActual.setCapturado(true); // Marcar como capturado
            notifyItemChanged(pokemonDisponibles.indexOf(pokemonActual));
          Toast.makeText(context, "Has capturado a " + pokemonActual.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Obtiene el tamaño del ArrayList que contiene la lista de Pokémon disponibles.
     *
     * @return número de elementos del ArrayList que contiene la lista de Pokémon disponibles.
     */
    @Override
    public int getItemCount() {
        return pokemonDisponibles.size();
    }

}
