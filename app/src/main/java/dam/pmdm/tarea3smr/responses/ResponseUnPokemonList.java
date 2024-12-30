
package dam.pmdm.tarea3smr.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseUnPokemonList {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseUnPokemonList() {
    }

    public ResponseUnPokemonList(String name, String url) {
        super();
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResponseUnPokemonList withName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResponseUnPokemonList withUrl(String url) {
        this.url = url;
        return this;
    }

}
