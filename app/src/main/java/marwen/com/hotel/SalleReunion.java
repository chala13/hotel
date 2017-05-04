package marwen.com.hotel;

/**
 * Created by Taher on 25/04/2016.
 */
public class SalleReunion {

    private String image;
    private String nom;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public SalleReunion(String image) {
        this.image = image;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public SalleReunion(String nom, String image) {
        this.nom = nom;
        this.image = image;
    }

    public SalleReunion() {
    }
}
