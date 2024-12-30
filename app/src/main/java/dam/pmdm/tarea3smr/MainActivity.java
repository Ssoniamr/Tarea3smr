package dam.pmdm.tarea3smr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

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

import java.util.Locale;

import dam.pmdm.tarea3smr.databinding.ActivityMainBinding;
import dam.pmdm.tarea3smr.responses.ResponseUnPokemonList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private SharedPreferences sharedPreferences;
    private String idioma;

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


    /**
     * Metodo que recupera las preferencias guardadas e invoca el metodo {@link #changeLanguage(String)}
     * para cambiar el idioma de la app.
     * (es invocado cada vez que se lanza la MainActivity).
     */
    private void preferenciasGuardadas() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //obtenemos el idioma guardado
        idioma = sharedPreferences.getString("elegir_idioma", "es");
        changeLanguage(idioma);
        //obtenemos la preferencia guardada para eliminar pokemons.
        if (sharedPreferences.getBoolean("eliminar_pokemon", false)) {

        }
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

     public void pokemonDisponiblesClicked(ResponseUnPokemonList pokemon, View view){


    }

    public  void cerrarSesion(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, getString(R.string.toastCierre), Toast.LENGTH_SHORT).show();
                        irALogin();
                    }
                });
    }

    private void irALogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * Método para modificar el idioma de la aplicación.
     * utiliza la configuracion del sistmea y actualiza la app. invocando los metodos
     * {@link #invalidateOptionsMenu()} y {@link #refreshNavigationMenu()}.
     *
     * @param language indica el código del idioma al que se desea cambiar.
     */
    public void changeLanguage(String language) {
        try {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        } catch (Exception e) {
            System.out.println("no pudo trabajar con locale" + e);
        }

        //llama a los metodos para actualizar el resto de la app
        invalidateOptionsMenu();
        refreshNavigationMenu();
    }

    /**
     * metodo para actualizar el menu lateral
     * (se invoca cada vez que se produce un cambio de idioma)
     */
    public void refreshNavigationMenu() {
        Menu menu = binding.navigationButton.getMenu();
        menu.clear();
        binding.navigationButton.inflateMenu(R.menu.button_menu);
    }

}



