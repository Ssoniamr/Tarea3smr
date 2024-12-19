package dam.pmdm.tarea3smr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dam.pmdm.tarea3smr.databinding.FragmentPokemonsCapturadosCardviewBinding;

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


    public void  bind(PokemonData pokemon){
        binding.imagenPokemonCapturado.setImageResource(pokemon.getImagenPokemonsLista());
        binding.nombrePokemonCapturado.setText(pokemon.getNombre());
    }
}