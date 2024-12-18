package dam.pmdm.tarea3smr;

import android.os.Bundle;
import android.view.MenuItem;

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
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import dam.pmdm.tarea3smr.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;

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

        //si este no es nulo obetemos el controlador de navegacion y lo conectamos con el navigationButton
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.navigationButton, navController);
        }

        binding.navigationButton.setOnItemSelectedListener(this::onNavigationItemSelected);

    }


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

}



