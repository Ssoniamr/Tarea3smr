package dam.pmdm.tarea3smr;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import dam.pmdm.tarea3smr.databinding.FragmentPokemonsCapturadosCardviewBinding;
import dam.pmdm.tarea3smr.responses.ResponseDetallePokemon;

/**
 * Clase adaptador para mostrar una lista de Pokémon capturados en un RecyclerView.
 */
public class PokemonCapturadoRecyclerViewAdapter extends RecyclerView.Adapter<PokemonsCapturadosCardview> {

    private final ArrayList<ResponseDetallePokemon> pokemonCapturado;
    private final Context context;
    private boolean isDeletionEnabled;

    /**
     * Constructor para inicializar el adaptador.
     *
     * @param pokemonCapturado lista de Pokémon capturados.
     * @param context          contexto de la actividad.
     */
    public PokemonCapturadoRecyclerViewAdapter(ArrayList<ResponseDetallePokemon> pokemonCapturado, Context context) {
        this.pokemonCapturado = pokemonCapturado;
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
    public PokemonsCapturadosCardview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentPokemonsCapturadosCardviewBinding binding = FragmentPokemonsCapturadosCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PokemonsCapturadosCardview(binding);
    }

    /**
     * Vincula los datos del Pokémon capturado actual al ViewHolder.
     *
     * @param holder   ViewHolder que debe ser actualizado para representar los contenidos del elemento en la posición dada.
     * @param position posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull PokemonsCapturadosCardview holder, int position) {
        ResponseDetallePokemon pokemonActual = this.pokemonCapturado.get(position);

        holder.bind(pokemonActual);
        holder.itemView.setOnClickListener(view -> itemClicked(pokemonActual, view));

    }

    /**
     * Método que maneja el evento de clic sobre el ítem, indica hacia dónde navegar llamando al método
     * {@link MainActivity#pokemonCapturadoClicked(ResponseDetallePokemon, View)}
     *
     * @param pokemonActual objeto PokemonData actual.
     * @param view          vista del ítem que se clicó.
     */
    private void itemClicked(ResponseDetallePokemon pokemonActual, View view) {
        ((MainActivity) context).pokemonCapturadoClicked(pokemonActual, view);
    }

    /**
     * Obtiene el tamaño del ArrayList que contiene la lista de Pokémon capturados.
     *
     * @return número de elementos del ArrayList que contiene la lista de Pokémon capturados.
     */
    @Override
    public int getItemCount() {
        return pokemonCapturado.size();
    }

    /**
     * Activa o desactiva la funcionalidad de eliminación de Pokémon.
     *
     * @param isDeletionEnabled boolean que indica si la eliminación está habilitada.
     */
    public void setDeletionEnabled(boolean isDeletionEnabled) {
        this.isDeletionEnabled = isDeletionEnabled;
        notifyDataSetChanged();
    }

    /**
     * Devuelve si la funcionalidad de eliminación está habilitada.
     *
     * @return boolean que indica si la eliminación está habilitada.
     */
    public boolean isDeletionEnabled() {
        return isDeletionEnabled;
    }

    /**
     * Elimina un Pokémon de la lista en una posición dada.
     *
     * @param position posición del Pokémon a eliminar.
     */
    public void eliminarPokemon(int position) {
        if (position >= 0 && position < pokemonCapturado.size()) {
            // Obtiene el nombre del Pokémon a eliminar
            String pokemonName = pokemonCapturado.get(position).getName();

            // Elimina el Pokémon de Firebase
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("capturedPokemons").document(pokemonName)
                    .delete()
                    .addOnSuccessListener(aVoid -> Toast.makeText(context, context.getString(R.string.pok_mon) + pokemonName + context.getString(R.string.eliminado_correctamente), Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(context, context.getString(R.string.error_al_eliminar_pok_mon) + pokemonName, Toast.LENGTH_SHORT).show());

            // Elimina el Pokémon de la lista de capturados y notifica al adaptador
            pokemonCapturado.remove(position);
            notifyItemRemoved(position);
        }
    }


}
