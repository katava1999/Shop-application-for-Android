package model;

import java.io.Serializable;

public class Korisnik implements Serializable {

    private Integer _id;
    private String Ime;
    private String Prezime;
    private String Username;
    private String Password;

    public Korisnik() {
    }

    public Korisnik(Integer id, String ime, String prezime, String username, String password) {
        _id = id;
        Ime = ime;
        Prezime = prezime;
        Username = username;
        Password = password;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        this.Ime = ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public void setPrezime(String prezime) {
        this.Prezime = prezime;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

}
