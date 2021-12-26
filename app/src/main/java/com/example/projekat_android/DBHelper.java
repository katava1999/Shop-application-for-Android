package com.example.projekat_android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import model.Artikal;
import model.Korisnik;
import model.Kupljen;
import model.Porudzbina;
import model.Stavka;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    List<Artikal> artikalList = new ArrayList<>();
    public static final String DATABASE_NAME = "Login.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(id INTEGER PRIMARY KEY AUTOINCREMENT,ime TEXT, prezime TEXT, username TEXT NOT NULL, password TEXT)");
        MyDB.execSQL("create Table kupci(id INTEGER PRIMARY KEY AUTOINCREMENT ,ime TEXT, prezime TEXT, username TEXT NOT NULL, password TEXT)");
        MyDB.execSQL("create Table prodavci(id INTEGER PRIMARY KEY AUTOINCREMENT ,ime TEXT, prezime TEXT, username TEXT NOT NULL, password TEXT)");
        MyDB.execSQL("create Table artikli(id INTEGER PRIMARY KEY AUTOINCREMENT, naziv TEXT, opis TEXT, cena DOUBLE, korisnik_id INTEGER, FOREIGN KEY (korisnik_id) REFERENCES users(id))");
        MyDB.execSQL("create Table stavke(id INTEGER PRIMARY KEY, kolicina INTEGER, artikal_id INTEGER, FOREIGN KEY(artikal_id) REFERENCES artikli(id))");
        MyDB.execSQL("create Table porudzbine(id INTEGER PRIMARY KEY AUTOINCREMENT, korisnik_id INTEGER NOT NULL, stavka_id INTEGER NOT NULL, ukupno_cena REAL NOT NULL ,FOREIGN KEY(korisnik_id) REFERENCES users(id),FOREIGN KEY(stavka_id) REFERENCES stavke(id))");
        MyDB.execSQL("create Table kupljen(id INTEGER PRIMARY KEY AUTOINCREMENT,artikal TEXT, username TEXT NOT NULL, kolicina INTEGER, ukupna_cena REAL)");

        MyDB.execSQL("Insert into users(id,ime,prezime,username,password) VALUES (1,'milorad','miloradovic','miloradm','321')");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password) VALUES (2,'nenad','nenadovic','nenadn','523')");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password) VALUES (3,'stefan','stefanovic','stefans','123')");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password) VALUES (4,'marko','markovic','markom','123')");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password) VALUES (5,'marko','markovic','markoM','123')");

        MyDB.execSQL("Insert into artikli(id,naziv,opis,cena,korisnik_id) VALUES (1,'Philips','monitor','39540',2)");
        MyDB.execSQL("Insert into artikli(id,naziv,opis,cena,korisnik_id) VALUES (2,'Lenovo','laptop','55000',5)");
        MyDB.execSQL("Insert into artikli(id,naziv,opis,cena,korisnik_id) VALUES (3,'LG','televizor','56000',5)");
        MyDB.execSQL("Insert into artikli(id,naziv,opis,cena,korisnik_id) VALUES (4,'Samsung A21s','mobilni telefon','24000',5)");
        MyDB.execSQL("Insert into artikli(id,naziv,opis,cena,korisnik_id) VALUES (5,'Honor 8x','mobilni telefon','15000',2)");
        MyDB.execSQL("Insert into artikli(id,naziv,opis,cena,korisnik_id) VALUES (6,'Midea','klima uredjaj','98245',1)");
        MyDB.execSQL("Insert into artikli(id,naziv,opis,cena,korisnik_id) VALUES (7,'Galanz','klima uredjaj','44600',1)");

}

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists kupci");
        MyDB.execSQL("drop Table if exists prodavci");
        MyDB.execSQL("drop Table if exists artikli");
        MyDB.execSQL("drop Table if exists stavke");
        MyDB.execSQL("drop Table if exists porudzbine");
        MyDB.execSQL("drop Table if exists kupljen");
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Korisnik findKorisnik(String usernamee){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where username=?", new String[] {usernamee});
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String ime = cursor.getString(1);
            String prezime = cursor.getString(2);
            String username = cursor.getString(3);
            String password = cursor.getString(4);

            Korisnik korisnik = new Korisnik(id,ime,prezime,username, password);

            return korisnik;
        }
        else{
            return null;
        }

    }

    public Boolean insertData(String ime, String prezime, String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ime", ime);
        contentValues.put("prezime", prezime);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;

    }

    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public List<Artikal> getArtikli(){
        String sql = "select * from artikli ";
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Artikal> artikli = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String naziv = cursor.getString(1);
                String opis = cursor.getString(2);
                Double cena = Double.parseDouble(cursor.getString(3));
                artikli.add(new Artikal(id,naziv,opis,cena));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return artikli;

    }

    public void insertStavke(Stavka stavka){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", stavka.getId());
        contentValues.put("kolicina", stavka.getKolicina());
        contentValues.put("artikal_id", stavka.getArtikal_id());

        MyDB.insert("stavke", null, contentValues);
    }

    public void insertPorudzbinu(Porudzbina porudzbina){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("korisnik_id", porudzbina.getKupac_id());
        contentValues.put("stavka_id", porudzbina.getStavka_id());
        contentValues.put("ukupno_cena", porudzbina.getUkupno_cena());


        MyDB.insert("porudzbine", null, contentValues);

    }

    public void updateArtikal(Artikal artikal){
        ContentValues contentValues = new ContentValues();
        contentValues.put("naziv", artikal.getNaziv());
        contentValues.put("opis", artikal.getOpis());
        contentValues.put("cena", artikal.getCena());
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.update("artikli",contentValues, "id = ?", new String[] {String.valueOf(artikal.getId())});

    }

    public List<Artikal> getArtikliProdavca(String prodavacId){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Artikal> artikliList = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("select * from artikli where korisnik_id=?", new String[] {prodavacId});
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String naziv = cursor.getString(1);
                String opis = cursor.getString(2);
                Double cena = Double.parseDouble(cursor.getString(3));
                Integer prodavac_id = cursor.getInt(4);
                artikliList.add(new Artikal(id,naziv,opis,cena,prodavac_id));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return artikliList;

    }

    public void deleteArtikal(int id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.delete("artikli", "id = ?", new String[] {String.valueOf(id)});
    }

    public void insertArtikal(Artikal artikal){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("naziv", artikal.getNaziv());
        contentValues.put("opis", artikal.getOpis());
        contentValues.put("cena", artikal.getCena());
        contentValues.put("korisnik_id", artikal.getProdavac_id());

        MyDB.insert("artikli", null, contentValues);

    }

    public List<Kupljen> getArtikliKupca(String usernamee){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Kupljen> artikliK = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("select * from kupljen where username=?", new String[] {usernamee});
        if(cursor.moveToFirst()){
            do{cursor.getInt(4);
                int id = Integer.parseInt(cursor.getString(0));
                String artikal = cursor.getString(1);
                String username = cursor.getString(2);
                Integer kolicina = cursor.getInt(3);
                Double ukupna_cena = Double.parseDouble(cursor.getString(4));
                artikliK.add(new Kupljen(id, artikal, username, kolicina, ukupna_cena));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return artikliK;

    }

    public void insertArtikalKupca(Kupljen kupljen){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("artikal", kupljen.getArtikal());
        contentValues.put("username", kupljen.getUsername());
        contentValues.put("kolicina", kupljen.getKolicina());
        contentValues.put("ukupna_cena", kupljen.getUkupna_cena());

        MyDB.insert("kupljen", null, contentValues);

    }
}
