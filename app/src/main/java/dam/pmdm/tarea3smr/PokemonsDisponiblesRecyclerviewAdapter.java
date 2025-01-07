package dam.pmdm.tarea3smr;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String pokemonName = pokemonActual.getName();
        // Consulta a FirebaseFirestore para verificar si el Pokémon ya está almacenado
        db.collection("capturedPokemons").document(pokemonName).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Si el documento existe, significa que el Pokémon ya está en Firebase
                    //cambiamos color en la lista de pokemons disponibles.
                    int noDisponible = ContextCompat.getColor(context, R.color.gris);
                    holder.binding.nombrePokemonDisponible.setTextColor(noDisponible);

                    //deshabilitamos el clic y mostramos toast
                    holder.itemView.setOnClickListener(v -> {
                        Snackbar.make(holder.itemView, R.string.este_pok_mon_ya_fue_capturado, Snackbar.LENGTH_SHORT).show();

                    });
                } else {
                    //sino existe lo ponemos en el color original.
                    int disponible = ContextCompat.getColor(context, R.color.Azul_oscuro);
                    holder.binding.nombrePokemonDisponible.setTextColor(disponible);

                    //volver habilitar clic original si el pokemon existe
                    holder.itemView.setOnClickListener(view -> itemClicked(pokemonActual, view));
                }
            }
        });
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
        // Capturar el Pokémon y actualizar el estado
        ((MainActivity) context).pokemonDisponiblesClicked(pokemonActual);
        notifyItemChanged(pokemonDisponibles.indexOf(pokemonActual));
        Toast.makeText(context, "Has capturado a " + pokemonActual.getName(), Toast.LENGTH_SHORT).show();
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
