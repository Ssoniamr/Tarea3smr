package dam.pmdm.tarea3smr;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.PreferenceManager;

import android.widget.Toast;

import android.content.SharedPreferences;

/**
 * Clase que maneja los ajustes de preferencias de la aplicación.
 */
public class PreferenceAjustes extends PreferenceFragmentCompat {

    public static Preference acercaDe;
    public static Preference cerrarSesion;
    public static String idioma;
    public static ListPreference idiomaList;
    public static PreferenceCategory preferenceCategory;
    public static SwitchPreference eliminarPokemonPreference;

    /**
     * Método que se llama durante la creación del fragmento y carga las preferencias desde un recurso XML.
     *
     * @param savedInstanceState Si es no nulo, este fragmento se está reconstruyendo
     *                           desde un estado guardado previamente.
     * @param rootKey            Si se especifica, este fragmento debe
     *                           anidar su jerarquía de preferencias dentro de una PreferenceScreen con esta clave.
     */
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        acercaDe = findPreference("acerca_de");
        cerrarSesion = findPreference("cerrar_sesion");
        idiomaList = findPreference("elegir_idioma");
        preferenceCategory = findPreference("preference_category");
        eliminarPokemonPreference = findPreference("eliminar_pokemon");

        if (acercaDe != null) {
            acercaDe.setOnPreferenceClickListener(this::onClick);
        }
        if (cerrarSesion != null) {
            cerrarSesion.setOnPreferenceClickListener(this::onClick);
        }
        if (idiomaList != null) {
            idiomaList.setOnPreferenceChangeListener((preference, newValue) -> {
                idioma = (String) newValue;
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).changeLanguage(idioma);
                }
                updateLanguajeView();
                return true;
            });
        }

        // Configurar el SwitchPreference
        if (eliminarPokemonPreference != null) {
            eliminarPokemonPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isChecked = (Boolean) newValue;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("eliminar_pokemon", isChecked);
                editor.apply();
                // Mostrar un Toast según el estado del SwitchPreference
                if (isChecked) {
                    Toast.makeText(getContext(), R.string.mensaje_de_activacion_switch, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.mensaje_desactivacion_switch, Toast.LENGTH_SHORT).show();
                }
                return true;
            });
        }
    }

    /**
     * Maneja los clics en las preferencias de la lista. crea un dialog segun la vista clicada
     *
     * @param preference La preferencia que fue clicada.
     * @return true si el clic se maneja correctamente, false en caso contrario.
     */
    private boolean onClick(Preference preference) {
        if (getContext() != null) {
            if (preference.getKey().equals("acerca_de")) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.acerca_de)
                        .setMessage(R.string.infromacion_desarrollador)
                        .setPositiveButton("ok", null)
                        .show();
                return true;
            } else if (preference.getKey().equals("cerrar_sesion")) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.cerrar_sesion)
                        .setMessage(R.string.confirmar_cierre)
                        .setPositiveButton("Sí", (dialog, which) -> {
                            if (getActivity() != null) {
                                ((MainActivity) getActivity()).cerrarSesion();
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para actualizar las vistas después de cambiar el idioma.
     */
    public void updateLanguajeView() {
        preferenceCategory.setTitle(R.string.ajustes);
        idiomaList.setTitle(R.string.idioma_titulo_ajustes);
        idiomaList.setSummary(R.string.elige_un_idioma);
        eliminarPokemonPreference.setTitle(R.string.eliminar_pokemon);
        eliminarPokemonPreference.setSummary(R.string.elim_pokemon_title_opciones);
        acercaDe.setTitle(R.string.acerca_de);
        acercaDe.setSummary(R.string.info_app);
        cerrarSesion.setTitle(R.string.cerrar_sesi_n);
    }
}
