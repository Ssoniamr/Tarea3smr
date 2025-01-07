package dam.pmdm.tarea3smr;

import androidx.recyclerview.widget.RecyclerView;

import dam.pmdm.tarea3smr.databinding.FragmentPokemonsDisponiblesCardViewBinding;
import dam.pmdm.tarea3smr.responses.ResponseUnPokemonList;

/**
 * Clase que crea los ViewHolders del RecyclerView.
 */
public class PokemonsDisponiblesCardView extends RecyclerView.ViewHolder {

    public FragmentPokemonsDisponiblesCardViewBinding binding;


    /**
     * Constructor para inicializar un ViewHolder.
     *
     * @param binding indica el objeto FragmentPokemonsDisponiblesCardViewBinding generado que contiene las
     *                vistas del diseño XML.
     */
    public PokemonsDisponiblesCardView(FragmentPokemonsDisponiblesCardViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Método para vincular los datos del Pokémon disponible a las vistas.
     *
     * @param pokemon objeto ResponseUnPokemonList que contiene los datos del Pokémon.
     */
    public void bind(ResponseUnPokemonList pokemon) {
        binding.nombrePokemonDisponible.setText(pokemon.getName());

    }


}
