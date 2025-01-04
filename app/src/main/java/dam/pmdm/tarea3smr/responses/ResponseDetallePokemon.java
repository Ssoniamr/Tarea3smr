package dam.pmdm.tarea3smr.responses;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa los detalles de un Pokémon.
 */
public class ResponseDetallePokemon {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private long index;

    @SerializedName("sprites")
    @Expose
    private ResponseSprites sprites;

    @SerializedName("sprite")
    @Expose
    private String sprite;

    @SerializedName("types")
    @Expose
    private List<ResponseTipoPokemon> types;

    @SerializedName("weight")
    @Expose
    private long weight;

    @SerializedName("height")
    @Expose
    private long height;

    /**
     * Constructor vacío para la clase.
     */
    public ResponseDetallePokemon() {
    }

    /**
     * Constructor con todos los parámetros.
     *
     * @param name    Nombre del Pokémon.
     * @param index   ID del Pokémon.
     * @param sprites Sprites del Pokémon.
     * @param sprite  Sprite del Pokémon.
     * @param types   Tipos del Pokémon.
     * @param weight  Peso del Pokémon.
     * @param height  Altura del Pokémon.
     */
    public ResponseDetallePokemon(String name, long index, ResponseSprites sprites, String sprite, List<ResponseTipoPokemon> types, long weight, long height) {
        this.name = name;
        this.index = index;
        this.sprites = sprites;
        this.sprite = sprite;
        this.types = types;
        this.weight = weight;
        this.height = height;
    }

    // Métodos getter y setter para cada campo

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public ResponseSprites getSprites() {
        return sprites;
    }

    public void setSprites(ResponseSprites sprites) {
        this.sprites = sprites;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public List<ResponseTipoPokemon> getTypes() {
        return types;
    }

    public void setTypes(List<ResponseTipoPokemon> types) {
        this.types = types;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }
}
