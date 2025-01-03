package dam.pmdm.tarea3smr.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSprites {

    @SerializedName("front_default")
    @Expose
    private String frontDefault;

    public ResponseSprites() {
    }

    public ResponseSprites(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }
}
