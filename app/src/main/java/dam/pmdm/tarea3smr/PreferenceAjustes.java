package dam.pmdm.tarea3smr;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.google.firebase.auth.FirebaseAuth;

public class PreferenceAjustes extends PreferenceFragmentCompat {

    public static Preference acercaDe;
    public static Preference cerrarSesion;
    public static String idioma;
    public static ListPreference idiomaList;
    public static PreferenceCategory preferenceCategory;
    public static SwitchPreference eliminarPokemonPreference;


    /**
     * Método que inicializa las referencias y les asigna listener para manejarlas mediante el
     *      * método {@link #onClick(Preference)}
     * @param savedInstanceState If the fragment is being re-created from a previous saved state,
     *                           this is the state.
     * @param rootKey            If non-null, this preference fragment should be rooted at the
     *                           {@link PreferenceAjustes} with this key.
     */
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        acercaDe = findPreference("acerca_de");
        cerrarSesion = findPreference("cerrar_sesion");
        idiomaList = findPreference("elegir_idioma");
        preferenceCategory = findPreference("preference_category");
        eliminarPokemonPreference = findPreference("eliminar_pokemon");

        acercaDe.setOnPreferenceClickListener(this::onClick);
        cerrarSesion.setOnPreferenceClickListener(this::onClick);
        idiomaList.setOnPreferenceChangeListener((preference, newValue) -> {
            idioma = (String) newValue;
            if(((MainActivity) getActivity())!=null) {
                ((MainActivity) getActivity()).changeLanguage(idioma);
            }
            updateLanguajeView();
            return true;
        });

    }

    private boolean onClick(Preference preference) {
        if(getContext()!=null){
        if(preference.getKey().equals("acerca_de")){
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.acerca_de)
                    .setMessage(R.string.infromacion_desarrollador)
                    .setPositiveButton("ok", null)
                    .show();
            return true;
            }else if(preference.getKey().equals("cerrar_sesion")) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.cerrar_sesi_n)
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
     * Metodo para actualizar las vistas despues de cambiar el idioma
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
