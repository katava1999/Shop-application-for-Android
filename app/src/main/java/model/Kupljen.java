package model;

public class Kupljen {

    private Integer id;
    private String artikal;
    private String username;
    private int kolicina;
    private double ukupna_cena;

    public Kupljen(Integer id, String artikal, String username, int kolicina, double ukupna_cena) {
        this.id = id;
        this.artikal = artikal;
        this.username = username;
        this.kolicina = kolicina;
        this.ukupna_cena = ukupna_cena;
    }

    public Kupljen(String artikal, String username, int kolicina, double ukupna_cena) {
        this.artikal = artikal;
        this.username = username;
        this.kolicina = kolicina;
        this.ukupna_cena = ukupna_cena;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtikal() {
        return artikal;
    }

    public void setArtikal(String artikal) {
        this.artikal = artikal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getUkupna_cena() {
        return ukupna_cena;
    }

    public void setUkupna_cena(double ukupna_cena) {
        this.ukupna_cena = ukupna_cena;
    }
}
