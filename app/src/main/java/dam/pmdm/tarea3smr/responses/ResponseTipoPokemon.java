
package dam.pmdm.tarea3smr.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseTipoPokemon {

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseTipoPokemon() {
    }

    public ResponseTipoPokemon(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResponseTipoPokemon withName(String name) {
        this.name = name;
        return this;
    }

}
