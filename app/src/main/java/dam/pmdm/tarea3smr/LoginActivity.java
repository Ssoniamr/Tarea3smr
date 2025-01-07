package dam.pmdm.tarea3smr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import dam.pmdm.tarea3smr.databinding.ActivityLogingBinding;

/**
 * LoginActivity gestiona el proceso de autenticación del usuario.
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLogingBinding binding;

    /**
     * metodo que infla la activity LoginActivity.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    /**
     * metodo que comprueba si el usuario ya ha iniciado sesión.
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Si el usuario ya ha iniciado sesión, procede a la actividad principal
            goToMainActivity();
        } else {
            // Si el usuario no ha iniciado sesión, inicia el proceso de inicio de sesión
            startSignIn();
        }
    }

    /**
     * Inicia el proceso de inicio de sesión utilizando FirebaseUI.
     */
    private void startSignIn() {
        // Define los proveedores de autenticación
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        // Crea y lanza la intención de inicio de sesión
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.pke_ball)
                .setTheme(R.style.Base_Theme_Tarea3smr)
                .build();
        signInLauncher.launch(signInIntent);
    }

    // Registra un lanzador para manejar el resultado del inicio de sesión
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    // Maneja el resultado del inicio de sesión
                    onSignInResult(result);
                }
            }
    );

    /**
     * Maneja el resultado del proceso de inicio de sesión.
     *
     * @param result El resultado del proceso de inicio de sesión.
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            goToMainActivity();
        } else {
            // Muestra un mensaje de error si el inicio de sesión falló
            Toast.makeText(this, getString(R.string.error_al_iniciar_sesi_n), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Navega a la actividad principal.
     */
    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = (user != null) ? user.getDisplayName() : null;
        if (userName != null) {
            String mensaje = getString(R.string.bienvenido) + " " + userName;
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
