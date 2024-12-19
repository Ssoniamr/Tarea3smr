package dam.pmdm.tarea3smr;

/**
 * Clase que contiene los datos de un pokémon.
 */
public class PokemonData {

    private final String nombre;
    private final int imagenPokemonsLista;
    private final int imagenPokemonDetalle;
    private final int indice;
    private final String tipo;
    private final String peso;
    private final String altura;

    /**
     * constructor para inicializar los datos de un pokemon.
     *
     * @param nombre               indica el nombre del pokemon.
     * @param imagenPokemonsLista  indica la imagen del pokemon que se mostrará en el RyclerView.
     * @param imagenPokemonDetalle indica la imagen del pokemon que se mostrará en la pagina de detalle.
     * @param indice               indica la posición del pokemon en la lista pokedex.
     * @param tipo                 indica el tipo de pokemon.
     * @param peso                 indica el peso del pokemon.
     * @param altura               indica la altura del pokemon.
     */
    public PokemonData(String nombre, int imagenPokemonsLista, int imagenPokemonDetalle, int indice, String tipo, String peso, String altura) {
        this.nombre = nombre;
        this.imagenPokemonsLista = imagenPokemonsLista;
        this.imagenPokemonDetalle = imagenPokemonDetalle;
        this.indice = indice;
        this.tipo = tipo;
        this.peso = peso;
        this.altura = altura;
    }

    /**
     * metodo para obtener el nombre del pokemon.
     *
     * @return retorna el nombre del pokemon.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * metodo para obtener la imagen del pokemon que se mostrará en el RecylerView.
     *
     * @return retorna la imagen del pokemon que se muestra en el RecyclerView.
     */
    public int getImagenPokemonsLista() {
        return imagenPokemonsLista;
    }

    /**
     * metodo para obtener la imagen del pokemon que se mostrará en la pagina de detalle.
     *
     * @return retorna la imagen del pokemon que se muestra en la pagina de detalle.
     */
    public int getImagenPokemonDetalle() {
        return imagenPokemonDetalle;
    }

    /**
     * metodo para obtener el indice del pokemon en la lista pokedex.
     *
     * @return retorna el indice del pokemon en la lista pokedex.
     */
    public int getIndice() {
        return indice;
    }

    /**
     * metodo para obtener el tipo del pokemon.
     *
     * @return retorna el tipo del pokemon.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * metodo para obtener el peso del pokemon.
     *
     * @return retorna el peso del pokemon.
     */
    public String getPeso() {
        return peso;
    }

    /**
     * metodo para obtener la altura del pokemon.
     *
     * @return retorna la altura del pokemon,
     */
    public String getAltura() {
        return altura;
    }
}
