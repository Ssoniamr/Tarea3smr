package dam.pmdm.tarea3smr;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import dam.pmdm.tarea3smr.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private SharedPreferences sharedPreferences;

    /**
     * metodo en el que se configura lo necesario para poder inicar la activity, la interfaz de
     * usuario y la navegacion.
     *
     * @param savedInstanceState en el caso de que exista, indica un estado anterior guardado de la activity para inicializarla desde ahí.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //se infla el layoud usando viewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //--------------CONFIGURACION DE NAVEGACION--------------

        //obtenemos el contenedor de fragmentos.
        FragmentManager fragmentManager = getSupportFragmentManager();
        navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);

        //si este no es nulo, obetemos el controlador de navegacion y lo conectamos con el navigationButton
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.navigationButton, navController);
        }

        binding.navigationButton.setOnItemSelectedListener(this::onNavigationItemSelected);

        preferenciasGuardadas();

    }

    private void preferenciasGuardadas() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }


    /**
     * Método que indica a donde navegar segun el item seleccionado del buttonNavigation
     * @param menuItem indica el item seleccionado.
     * @return devuelve un booleano que indica si se ha realizado la navegacion.
     */
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int menuItemElegido = menuItem.getItemId();

        if (menuItemElegido == R.id.preferences_ajustes) {
            navController.navigate(R.id.preferenceAjustes);
            return true;
        } else if (menuItemElegido == R.id.fragment_lista_pokemons_disponibles) {
            navController.navigate(R.id.fragment_lista_pokemons_disponibles);
            return true;
        } else if (menuItemElegido == R.id.fragment_lista_pokemons_capturados) {
            navController.navigate(R.id.fragment_lista_pokemons_capturados);
            return true;
        }
        return true;
    }

    /**
     * Método para pasar los datos de un fragment a otro.
     * Obtiene la información del objeto PokemonData perteneciente a la view que se selecciono y los envia
     * al fragmente pkemonsDetailFragment.
     * @param pokemon indica el objeto de tipo PokemonData que contiene la información del pokemon que se selecciono.
     * @param view inidca la vistas, es decir, el item del RecyclerView que se seleccioná.
     */
    public void pokemonCapturadoClicked(PokemonData pokemon, View view){
        Bundle bundle = new Bundle();
        bundle.putInt("imagen", pokemon.getImagenPokemonDetalle());
        bundle.putString("name", pokemon.getNombre());
        bundle.putInt("indice", pokemon.getIndice());
        bundle.putString("altura", pokemon.getAltura());
        bundle.putString("peso", pokemon.getPeso());

        Navigation.findNavController(view).navigate(R.id.pkemonsDetailFragment, bundle);

    }

}



