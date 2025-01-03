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

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dam.pmdm.tarea3smr.databinding.ActivityMainBinding;
import dam.pmdm.tarea3smr.responses.ResponseDetallePokemon;
import dam.pmdm.tarea3smr.responses.ResponseTipoPokemon;
import dam.pmdm.tarea3smr.responses.ResponseUnPokemonList;

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

        binding.navigationButton.setOnItemSelectedListener(this::onNavigationItemSelected);

        preferenciasGuardadas();
        FirebaseApp.initializeApp(this);
    }

    private void preferenciasGuardadas() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        idioma = sharedPreferences.getString("elegir_idioma", "es");
        changeLanguage(idioma);
    }

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

    public void pokemonCapturadoClicked(ResponseDetallePokemon pokemon, View view) {
        String tipos = obtenerTiposString(pokemon);

        Bundle bundle = new Bundle();
        bundle.putString("imagenUrl", pokemon.getSprites().getFrontDefault());
        bundle.putString("name", pokemon.getName());
        bundle.putLong("indice", pokemon.getId());
        bundle.putString("tipos", tipos);
        bundle.putLong("peso", pokemon.getWeight());
        bundle.putLong("altura", pokemon.getHeight());

        Navigation.findNavController(view).navigate(R.id.pkemonsDetailFragment, bundle);
    }

    public void pokemonDisponiblesClicked(ResponseUnPokemonList pokemon) {
        Log.d("Pokemon", "pokemonDisponiblesClicked: " + pokemon.getName());

        // Asegúrate de que el NavController está inicializado
        if (navHostFragment == null) {
            Log.e("NavController", "navHostFragment es nulo");
            return;
        }
        NavController navController = navHostFragment.getNavController();
        if (navController == null) {
            Log.e("NavController", "navController es nulo");
            return;
        }

        // Crea un Bundle para pasar los datos
        Bundle bundle = new Bundle();
        bundle.putString("pokemonName", pokemon.getName());

        // Navega al fragmento ListaPokemosCapturados pasando el Bundle
        navController.navigate(R.id.fragment_lista_pokemons_capturados, bundle);

        // Añadir una pausa para asegurar que la transacción se completa
        navHostFragment.getChildFragmentManager().executePendingTransactions();
        Fragment fragment = navHostFragment.getChildFragmentManager().findFragmentById(R.id.fragment_lista_pokemons_capturados);
        if (fragment != null) {
            Log.d("Pokemon", "Fragmento encontrado después de la navegación");
        } else {
            Log.e("Pokemon", "Fragment ListaPokemosCapturados no encontrado después de la navegación");
        }
    }



    public void guardarPokemonEnFirebase(ResponseDetallePokemon pokemon) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> pokemonMap = new HashMap<>();
        pokemonMap.put("name", pokemon.getName());
        pokemonMap.put("index", pokemon.getId());
        pokemonMap.put("imageUrl", pokemon.getSprites().getFrontDefault());
        pokemonMap.put("types", obtenerTipos(pokemon));
        pokemonMap.put("weight", pokemon.getWeight());
        pokemonMap.put("height", pokemon.getHeight());
        db.collection("capturedPokemons").document(pokemon.getName())
                .set(pokemonMap)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "DocumentSnapshot successfully written for: " + pokemon.getName());
                })
                .addOnFailureListener(e -> {
                    Log.w("Firebase", "Error writing document for: " + pokemon.getName(), e);
                });
    }

    public static List<Map<String, String>> obtenerTipos(ResponseDetallePokemon pokemon) {
        List<Map<String, String>> tipos = new ArrayList<>();
        if (pokemon.getTypes() != null) {
            for (ResponseTipoPokemon tipo : pokemon.getTypes()) {
                if (tipo != null && tipo.getType() != null) {
                    Map<String, String> tipoMap = new HashMap<>();
                    tipoMap.put("name", tipo.getType().getName());
                    tipos.add(tipoMap);
                } else {
                    Log.e("Pokemon", "Error al obtener el tipo - ResponseType o getType es nulo");
                }
            }
        } else {
            Log.e("Pokemon", "Error al obtener los tipos - Types es nulo");
        }
        return tipos;
    }


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

    private void irALogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

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

    public void refreshNavigationMenu() {
        Menu menu = binding.navigationButton.getMenu();
        menu.clear();
        binding.navigationButton.inflateMenu(R.menu.button_menu);
    }
}
