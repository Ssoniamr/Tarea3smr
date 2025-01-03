package dam.pmdm.tarea3smr;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import dam.pmdm.tarea3smr.databinding.FragmentPkemonsDetailBinding;

/**
 * Clase que muestra los detalles de un pokemon.
 */
public class PkemonsDetailFragment extends Fragment {

    private FragmentPkemonsDetailBinding binding;

    /**
     * Infla el diseño para poder mostrar el fragmento que muestra los detalles del pokemon.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return retorna la vista raíz para el diseño del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPkemonsDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Metodo que configura la vista del fragmento, obtiene los argumentos y los muestra.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String imagen = getArguments().getString("imagenUrl");
            String name = getArguments().getString("name");
            String index = getArguments().getString("index");
            String tipos = getArguments().getString("tipos");
            String peso = getArguments().getString("peso");
            String altura = getArguments().getString("altura");

            // Ponemos los tipos de datos en negrita
            String negrita = getString(R.string.indice_del_pokemon);
            Spannable spannable = new SpannableString(index);
            int start = index.indexOf(negrita);
            int end = start + negrita.length();
            spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            Picasso.get().load(imagen).into(binding.imagePokemonDetail);
            binding.nombrePokemon.setText(name);
            binding.indexPokemon.setText(spannable);
            binding.tipoPokemon.setText(tipos);
            binding.pesoPokemon.setText(peso);
            binding.alturaPokemon.setText(altura);
            String seleccion = getString(R.string.has_seleccionado_a) + " " + binding.nombrePokemon.getText();
            Toast.makeText(requireContext(), seleccion, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
