package model;

import android.view.inputmethod.InlineSuggestionsRequest;

import java.io.Serializable;

public class Prodavac implements Serializable {
    private Integer id;
    private String ime;
    private String prezime;
    private String username;
    private String password;

    public Prodavac(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Prodavac(Integer id, String ime, String prezime, String username, String password) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.password = password;
    }
}
