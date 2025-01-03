package dam.pmdm.tarea3smr.responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDetallePokemon {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("index")
    @Expose
    private long index;
    @SerializedName("sprites")
    @Expose
    private ResponseSprites sprites;
    @SerializedName("types")
    @Expose
    private List<ResponseTipoPokemon> types;
    @SerializedName("weight")
    @Expose
    private long weight;
    @SerializedName("height")
    @Expose
    private long height;

    public ResponseDetallePokemon() {
    }

    public ResponseDetallePokemon(String name, long index, ResponseSprites sprites, List<ResponseTipoPokemon> types, long weight, long height) {
        this.name = name;
        this.index = index;
        this.sprites = sprites;
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
