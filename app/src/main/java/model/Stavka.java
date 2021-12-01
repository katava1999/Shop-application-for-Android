package model;

import java.io.Serializable;

public class Stavka implements Serializable {

    private Integer id;
    private int kolicina;
    private int artikal_id;

    public Stavka(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public int getArtikal_id() {
        return artikal_id;
    }

    public void setArtikal_id(int artikal_id) {
        this.artikal_id = artikal_id;
    }

    public Stavka(Integer id, int kolicina, int artikal_id) {
        this.id = id;
        this.kolicina = kolicina;
        this.artikal_id = artikal_id;
    }
}
