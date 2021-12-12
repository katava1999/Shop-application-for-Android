package model;

public class Artikal {

    private Integer id;
    private String naziv;
    private String opis;
    private double cena;
    private int prodavac_id;

    public Artikal() {
    }

    public int getProdavac_id() {
        return prodavac_id;
    }

    public void setProdavac_id(int prodavac_id) {
        this.prodavac_id = prodavac_id;
    }

    public Artikal(Integer id, String naziv, String opis, double cena, int prodavac_id) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
        this.prodavac_id = prodavac_id;
    }

    public Artikal(Integer id, String naziv, String opis, double cena) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

}
