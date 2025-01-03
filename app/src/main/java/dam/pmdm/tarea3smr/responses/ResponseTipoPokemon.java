package dam.pmdm.tarea3smr.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseTipoPokemon {

    @SerializedName("type")
    @Expose
    private ResponseType type;

    public ResponseTipoPokemon() {
    }

    public ResponseTipoPokemon(ResponseType type) {
        this.type = type;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }
}
