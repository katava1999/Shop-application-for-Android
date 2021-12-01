package model;


public class Porudzbina {

    private Integer id;
    private int kupac_id;
    private int stavka_id;
    private double ukupno_cena;

    public Porudzbina(){

    }

    public Porudzbina(int kupac_id, int stavka_id, double ukupno_cena ) {
        this.kupac_id = kupac_id;
        this.stavka_id = stavka_id;
        this.ukupno_cena = ukupno_cena;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getKupac_id() {
        return kupac_id;
    }

    public void setKupac_id(int kupac_id) {
        this.kupac_id = kupac_id;
    }

    public int getStavka_id() {
        return stavka_id;
    }

    public void setStavka_id(int stavka_id) {
        this.stavka_id = stavka_id;
    }

    public double getUkupno_cena() {
        return ukupno_cena;
    }

    public void setUkupno_cena(double ukupno_cena) {
        this.ukupno_cena = ukupno_cena;
    }
}
