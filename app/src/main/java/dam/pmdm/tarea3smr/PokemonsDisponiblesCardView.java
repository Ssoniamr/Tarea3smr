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
import dam.pmdm.tarea3smr.databinding.FragmentPokemonsDisponiblesCardViewBinding;
import dam.pmdm.tarea3smr.responses.ResponseUnPokemonList;


public class PokemonsDisponiblesCardView extends RecyclerView.ViewHolder {

    public FragmentPokemonsDisponiblesCardViewBinding binding;

    /**
     * Constructor para inicializar un viewHolder.
     *
     * @param binding indica el objeto FragmentPokemonsDisponiblesCardviewBinding generado que contiene las
     *                vistas del diseño xml.
     */
    public PokemonsDisponiblesCardView(FragmentPokemonsDisponiblesCardViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }


    public void bind(ResponseUnPokemonList pokemon) {
        binding.nombrePokemonDisponible.setText(pokemon.getName());
    }
}