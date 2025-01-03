package dam.pmdm.tarea3smr;

import static dam.pmdm.tarea3smr.MainActivity.obtenerTiposString;

import android.util.Log;

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
            // Verifica que el objeto ResponseSprites no sea nulo antes de acceder a él
            if (pokemon.getSprites() != null) {
                // Carga la imagen del Pokémon
                Picasso.get().load(pokemon.getSprites().getFrontDefault()).into(binding.imagenPokemonCapturado);
                Log.d("Pokemon", "Imagen vinculada: " + pokemon.getSprites().getFrontDefault());
            } else {
                Log.e("Pokemon", "Error al vincular imagen - ResponseSprites es nulo");
            }

            // Vincula el nombre del Pokémon
            binding.nombrePokemonCapturado.setText(pokemon.getName());
            Log.d("Pokemon", "Nombre vinculado: " + pokemon.getName());

            // Obtiene y vincula el tipo del Pokémon
            String tipos = obtenerTiposString(pokemon);
            binding.tipoPokemonCapturado.setText(tipos);
            Log.d("Pokemon", "Tipos vinculados: " + tipos);
        } else {
            Log.e("Pokemon", "Error al vincular datos - Pokémon es nulo");
        }
    }

}
