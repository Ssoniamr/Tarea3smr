package dam.pmdm.tarea3smr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dam.pmdm.tarea3smr.databinding.FragmentPkemonsDetailBinding;
import dam.pmdm.tarea3smr.responses.ResponseDetallePokemon;
import dam.pmdm.tarea3smr.responses.ResponseTipoPokemon;
import dam.pmdm.tarea3smr.responses.ResponseType;

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
            String sprite = getArguments().getString("sprite");
            String name = getArguments().getString("name");
            Long index = getArguments().getLong("index");
            String types = getArguments().getString("types");
            Long weight = getArguments().getLong("weight");
            Long height = getArguments().getLong("height");

            // Cargar la imagen del Pokémon usando Picasso
            Picasso.get().load(sprite).into(binding.imagePokemonDetail);
            binding.nombrePokemon.setText(name);
            String indice = "INDEX:  " + index;
            binding.indexPokemon.setText(indice);

            // Convertir el JSON de tipos a una lista de mapas y luego a un string legible
            String readableTypes = "TIPOS:  " + obtenerTiposStringFromJson(types);
            binding.tipoPokemon.setText(readableTypes);

            String peso = "PESO:  " + weight;
            binding.pesoPokemon.setText(peso);

            String altura = "ALTURA:  " + height;
            binding.alturaPokemon.setText(altura);

            // Mostrar un mensaje Toast indicando el Pokémon seleccionado
            String seleccion = getString(R.string.has_seleccionado_a) + " " + binding.nombrePokemon.getText();
            Toast.makeText(requireContext(), seleccion, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método auxiliar para convertir el JSON de tipos a un formato legible usando obtenerTiposString.
     *
     * @param typesJson El JSON que contiene los tipos del Pokémon.
     * @return Una cadena con los tipos del Pokémon en un formato legible.
     */
    private String obtenerTiposStringFromJson(String typesJson) {
        List<Map<String, String>> tiposMapList = new Gson().fromJson(typesJson, new TypeToken<List<Map<String, String>>>() {
        }.getType());

        // Creación de un objeto ResponseDetallePokemon temporal para usar obtenerTiposString
        ResponseDetallePokemon pokemonTemp = new ResponseDetallePokemon();
        pokemonTemp.setTypes(convertirMapAListaTipos(tiposMapList));

        // Aquí se usa el método obtenerTiposString
        return MainActivity.obtenerTiposString(pokemonTemp);
    }

    /**
     * Método auxiliar para convertir la lista de mapas a una lista de ResponseTipoPokemon.
     *
     * @param tiposMapList La lista de mapas que contiene los tipos del Pokémon.
     * @return Una lista de objetos ResponseTipoPokemon.
     */
    private List<ResponseTipoPokemon> convertirMapAListaTipos(List<Map<String, String>> tiposMapList) {
        List<ResponseTipoPokemon> tiposList = new ArrayList<>();
        for (Map<String, String> tipoMap : tiposMapList) {
            if (tipoMap != null && tipoMap.get("name") != null) {
                ResponseType responseType = new ResponseType(tipoMap.get("name"));
                ResponseTipoPokemon tipo = new ResponseTipoPokemon(responseType);
                tiposList.add(tipo);
            }
        }
        return tiposList;
    }
}
