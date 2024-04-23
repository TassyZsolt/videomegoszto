package hu.adatb.videomegoszto.model;

public class User {
   private int felhasznaloId;
    private String nev;
    private String email;
    private String jelszo;
    private String regisztracioDatuma;

    public User() {
    }

    public User(int felhasznaloId, String nev, String email, String jelszo, String regisztracioDatuma) {
        this.felhasznaloId = felhasznaloId;
        this.nev = nev;
        this.email = email;
        this.jelszo = jelszo;
        this.regisztracioDatuma = regisztracioDatuma;
    }

    public int getFelhasznaloId() {
        return felhasznaloId;
    }

    public void setFelhasznaloId(int felhasznaloId) {
        this.felhasznaloId = felhasznaloId;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getRegisztracioDatuma() {
        return regisztracioDatuma;
    }

    public void setRegisztracioDatuma(String regisztracioDatuma) {
        this.regisztracioDatuma = regisztracioDatuma;
    }

    @Override
    public String toString() {
        return "User{" +
                "felhasznaloId=" + felhasznaloId +
                ", nev='" + nev + '\'' +
                ", email='" + email + '\'' +
                ", jelszo='" + jelszo + '\'' +
                ", regisztracioDatuma='" + regisztracioDatuma + '\'' +
                '}';
    }
}
