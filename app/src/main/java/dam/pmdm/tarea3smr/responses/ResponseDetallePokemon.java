
package dam.pmdm.tarea3smr.responses;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDetallePokemon {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private long index;
    @SerializedName("sprite")
    @Expose
    private String imagenUrl;
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
     * No args constructor for use in serialization
     *
     */
    public ResponseDetallePokemon() {
    }

    public ResponseDetallePokemon(String name, long id, String sprite, List<ResponseTipoPokemon> types, long weight, long height) {
        super();
        this.name = name;
        this.index = id;
        this.imagenUrl = sprite;
        this.types = types;
        this.weight = weight;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResponseDetallePokemon withName(String name) {
        this.name = name;
        return this;
    }

    public long getId() {
        return index;
    }

    public void setId(long id) {
        this.index = id;
    }

    public ResponseDetallePokemon withId(long id) {
        this.index = id;
        return this;
    }

    public String getSprite() {
        return imagenUrl;
    }

    public void setSprite(String sprite) {
        this.imagenUrl = sprite;
    }

    public ResponseDetallePokemon withSprite(String sprite) {
        this.imagenUrl = sprite;
        return this;
    }

    public List<ResponseTipoPokemon> getTypes() {
        return types;
    }

    public void setTypes(List<ResponseTipoPokemon> types) {
        this.types = types;
    }

    public ResponseDetallePokemon withTypes(List<ResponseTipoPokemon> types) {
        this.types = types;
        return this;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public ResponseDetallePokemon withWeight(long weight) {
        this.weight = weight;
        return this;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public ResponseDetallePokemon withHeight(long height) {
        this.height = height;
        return this;
    }

}
