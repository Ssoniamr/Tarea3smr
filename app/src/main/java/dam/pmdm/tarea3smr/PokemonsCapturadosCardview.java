package dam.pmdm.tarea3smr;

import static dam.pmdm.tarea3smr.MainActivity.obtenerTiposString;

import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import dam.pmdm.tarea3smr.databinding.FragmentPokemonsCapturadosCardviewBinding;
import dam.pmdm.tarea3smr.responses.ResponseDetallePokemon;

/**
 * Clase que crea los viewHolders del RecyclerView.
 */
public class PokemonsCapturadosCardview extends RecyclerView.ViewHolder {

    public FragmentPokemonsCapturadosCardviewBinding binding;

    /**
     * Constructor para inicializar un viewHolder.
     *
     * @param binding indica el objeto FragmentPokemonsCapturadosCardviewBinding generado que contiene las
     *                vistas del diseño xml.
     */
    public PokemonsCapturadosCardview(FragmentPokemonsCapturadosCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Método para vincular los datos del Pokémon capturado a las vistas.
     *
     * @param pokemon objeto ResponseDetallePokemon que contiene los datos del Pokémon.
     */
    public void bind(ResponseDetallePokemon pokemon) {
        if (pokemon != null) {
            // Verifica que el campo sprite no sea nulo antes de acceder a él
            if (pokemon.getSprite() != null) {
                // Carga la imagen del Pokémon
                Picasso.get().load(pokemon.getSprite()).into(binding.imagenPokemonCapturado);
            } else {
                binding.imagenPokemonCapturado.setImageResource(R.drawable.ic_launcher_background);
            }

            // Vincula el nombre del Pokémon
            binding.nombrePokemonCapturado.setText(pokemon.getName());

            // Obtiene y vincula el tipo del Pokémon
            String tipos = obtenerTiposString(pokemon);
            binding.tipoPokemonCapturado.setText(tipos);

            // Configura el botón de eliminación si la funcionalidad de eliminación está activada
            ImageButton deleteButton = binding.deleteButton;
            if (((PokemonCapturadoRecyclerViewAdapter) getBindingAdapter()).isDeletionEnabled()) {
                deleteButton.setVisibility(View.VISIBLE);
                deleteButton.setOnClickListener(v -> {
                    int position = getBindingAdapterPosition();
                    ((PokemonCapturadoRecyclerViewAdapter) getBindingAdapter()).eliminarPokemon(position);
                });
            } else {
                deleteButton.setVisibility(View.GONE);
            }
        }
    }
}
