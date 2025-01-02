package dam.pmdm.tarea3smr;

import static dam.pmdm.tarea3smr.MainActivity.obtenerTiposString;

import android.text.TextUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public void  bind(ResponseDetallePokemon pokemon){
        Picasso.get().load(pokemon.getSprite()).into(binding.imagenPokemonCapturado);
        binding.nombrePokemonCapturado.setText(pokemon.getName());
        String tipos = obtenerTiposString(pokemon);
        binding.tipoPokemonCapturado.setText(tipos);
    }
}