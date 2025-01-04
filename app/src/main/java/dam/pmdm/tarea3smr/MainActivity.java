package dam.pmdm.tarea3smr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dam.pmdm.tarea3smr.databinding.ActivityMainBinding;
import dam.pmdm.tarea3smr.responses.ResponseDetallePokemon;
import dam.pmdm.tarea3smr.responses.ResponseTipoPokemon;
import dam.pmdm.tarea3smr.responses.ResponseUnPokemonList;

/**
 * MainActivity es la actividad principal que gestiona la navegación y las operaciones principales de la aplicación.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private SharedPreferences sharedPreferences;
    private String idioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se infla el layout usando ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuración de navegación
        FragmentManager fragmentManager = getSupportFragmentManager();
        navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.navigationButton, navController);
        }

        // Configurar el listener para el botón de navegación
        binding.navigationButton.setOnItemSelectedListener(this::onNavigationItemSelected);

        // Cargar preferencias guardadas
        preferenciasGuardadas();

        // Inicializar Firebase
        FirebaseApp.initializeApp(this);
    }

    /**
     * Carga las preferencias guardadas y configura el idioma y opciones de eliminación de Pokémon.
     */
    private void preferenciasGuardadas() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        idioma = sharedPreferences.getString("elegir_idioma", "es");
        changeLanguage(idioma);

        // Obtiene el estado del SwitchPreference
        boolean eliminarPokemonEnabled = sharedPreferences.getBoolean("eliminar_pokemon", false);
        if (eliminarPokemonEnabled) {
            // Lógica adicional si el SwitchPreference está activado
            Log.d("MainActivity", "Eliminación de Pokémon activada");
        } else {
            Log.d("MainActivity", "Eliminación de Pokémon desactivada");
        }
    }

    /**
     * Maneja la selección de elementos en el botón de navegación.
     *
     * @param menuItem El elemento del menú seleccionado.
     * @return true si la navegación fue exitosa, false en caso contrario.
     */
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();

        if (menuItemId == R.id.preferences_ajustes) {
            navController.navigate(R.id.preferenceAjustes);
            return true;
        } else if (menuItemId == R.id.fragment_lista_pokemons_disponibles) {
            navController.navigate(R.id.fragment_lista_pokemons_disponibles);
            return true;
        } else if (menuItemId == R.id.fragment_lista_pokemons_capturados) {
            navController.navigate(R.id.fragment_lista_pokemons_capturados);
            return true;
        }
        return false;
    }

    /**
     * Maneja el clic en un Pokémon capturado para mostrar sus detalles.
     *
     * @param pokemon El objeto ResponseDetallePokemon que representa al Pokémon capturado.
     * @param view    La vista que fue clicada.
     */
    public void pokemonCapturadoClicked(ResponseDetallePokemon pokemon, View view) {
        // Obtener los tipos del Pokémon como cadena de texto
        String tipos = obtenerTiposString(pokemon);

        // Crear un Bundle para pasar los datos al fragmento de detalle
        Bundle bundle = new Bundle();
        bundle.putString("sprite", pokemon.getSprite());
        bundle.putString("name", pokemon.getName());
        bundle.putLong("index", pokemon.getIndex());
        bundle.putString("types", obtenerTipos(pokemon).toString());
        bundle.putLong("weight", pokemon.getWeight());
        bundle.putLong("height", pokemon.getHeight());

        // Navegar al fragmento de detalle de Pokémon
        Navigation.findNavController(view).navigate(R.id.pkemonsDetailFragment, bundle);
    }

    /**
     * Maneja el clic en un Pokémon disponible para mostrar sus detalles.
     *
     * @param pokemon El objeto ResponseUnPokemonList que representa al Pokémon disponible.
     */
    public void pokemonDisponiblesClicked(ResponseUnPokemonList pokemon) {
        // Asegúrate de que el NavController está inicializado
        if (navHostFragment == null) {
            return;
        }
        NavController navController = navHostFragment.getNavController();
        if (navController == null) {
            return;
        }

        // Crear un Bundle para pasar los datos
        Bundle bundle = new Bundle();
        bundle.putString("pokemonName", pokemon.getName());

        // Navegar al fragmento ListaPokemosCapturados pasando el Bundle
        navController.navigate(R.id.fragment_lista_pokemons_capturados, bundle);
    }


    /**
     * Guarda los detalles de un Pokémon capturado en Firebase Firestore.
     *
     * @param pokemon El objeto ResponseDetallePokemon que representa al Pokémon capturado.
     */
    public void guardarPokemonEnFirebase(ResponseDetallePokemon pokemon) {
        // Obtener una instancia de FirebaseFirestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> pokemonMap = new HashMap<>();

        // Obtener la URL del sprite del Pokémon
        String spriteUrl = pokemon.getSprites().getFrontDefault();
        if (spriteUrl != null) {
            pokemonMap.put("sprite", spriteUrl);
        } else {
            Log.e("Firebase", "La URL del sprite es nula");
        }

        // Agregar datos básicos del Pokémon al mapa
        pokemonMap.put("name", pokemon.getName());
        pokemonMap.put("index", pokemon.getIndex());

        // Convertir la estructura de tipos para Firebase
        List<Map<String, String>> firebaseTypes = new ArrayList<>();
        for (ResponseTipoPokemon tipo : pokemon.getTypes()) {
            if (tipo.getType() != null) {
                Map<String, String> typeMap = new HashMap<>();
                typeMap.put("name", tipo.getType().getName());
                firebaseTypes.add(typeMap);
            }
        }
        pokemonMap.put("types", firebaseTypes);

        // Agregar peso y altura del Pokémon al mapa
        pokemonMap.put("weight", pokemon.getWeight());
        pokemonMap.put("height", pokemon.getHeight());

        // Guardar el documento en Firebase Firestore
        db.collection("capturedPokemons").document(pokemon.getName())
                .set(pokemonMap)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "DocumentSnapshot successfully written for: " + pokemon.getName());
                })
                .addOnFailureListener(e -> {
                    Log.w("Firebase", "Error writing document for: " + pokemon.getName(), e);
                });
    }

    /**
     * Obtiene los tipos de un Pokémon en formato de lista de mapas.
     *
     * @param pokemon El objeto ResponseDetallePokemon del cual se obtendrán los tipos.
     * @return Una lista de mapas con los tipos del Pokémon.
     */
    public static List<Map<String, String>> obtenerTipos(ResponseDetallePokemon pokemon) {
        List<Map<String, String>> tipos = new ArrayList<>();
        if (pokemon.getTypes() != null) {
            for (ResponseTipoPokemon tipo : pokemon.getTypes()) {
                if (tipo != null && tipo.getType() != null && tipo.getType().getName() != null) {
                    Map<String, String> tipoMap = new HashMap<>();
                    tipoMap.put("name", tipo.getType().getName());
                    tipos.add(tipoMap);
                } else {
                    Log.e("Pokemon", "Error al obtener el tipo - ResponseType o nombre es nulo");
                }
            }
        } else {
            Log.e("Pokemon", "Error al obtener los tipos - Types es nulo");
        }
        return tipos;
    }

    /**
     * Obtiene los tipos de un Pokémon en formato de cadena.
     *
     * @param pokemon El objeto ResponseDetallePokemon del cual se obtendrán los tipos.
     * @return Una cadena con los tipos del Pokémon separados por comas.
     */
    public static String obtenerTiposString(ResponseDetallePokemon pokemon) {
        List<Map<String, String>> tiposMapList = MainActivity.obtenerTipos(pokemon);
        List<String> tiposList = new ArrayList<>();
        for (Map<String, String> tipoMap : tiposMapList) {
            if (tipoMap != null && tipoMap.get("name") != null) {
                tiposList.add(tipoMap.get("name"));
            } else {
                Log.e("Pokemon", "Error al obtener el nombre del tipo - tipoMap o nombre es nulo");
            }
        }
        return TextUtils.join(", ", tiposList);
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void cerrarSesion() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, getString(R.string.toastCierre), Toast.LENGTH_SHORT).show();
                        irALogin();
                    }
                });
    }

    /**
     * Navega a la pantalla de inicio de sesión.
     */
    private void irALogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Cambia el idioma de la aplicación.
     *
     * @param language El código del idioma al cual se cambiará la aplicación.
     */
    public void changeLanguage(String language) {
        try {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        } catch (Exception e) {
            Log.e("LocaleError", "No se pudo cambiar el idioma", e);
        }

        invalidateOptionsMenu();
        refreshNavigationMenu();
    }



    /**
     * Refresca el menú de navegación.
     */
    public void refreshNavigationMenu() {
        Menu menu = binding.navigationButton.getMenu();
        menu.clear();
        binding.navigationButton.inflateMenu(R.menu.button_menu);
    }
}
