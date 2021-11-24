package com.example.projekat_android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Artikal;
import model.Korisnik;
import model.Kupac;
import model.Prodavac;

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
        MyDB.execSQL("create Table users(id INTEGER PRIMARY KEY AUTOINCREMENT,ime TEXT, prezime TEXT, username TEXT NOT NULL, password TEXT, uloga TEXT)");
        MyDB.execSQL("create Table kupci(id INTEGER PRIMARY KEY AUTOINCREMENT ,ime TEXT, prezime TEXT, username TEXT NOT NULL, password TEXT)");
        MyDB.execSQL("create Table prodavci(id INTEGER PRIMARY KEY AUTOINCREMENT ,ime TEXT, prezime TEXT, username TEXT NOT NULL, password TEXT)");
        MyDB.execSQL("create Table artikli(id INTEGER PRIMARY KEY AUTOINCREMENT, naziv TEXT, opis TEXT, cena DOUBLE)");

        MyDB.execSQL("Insert into users(id,ime,prezime,username,password,uloga) VALUES (1,'milorad','miloradovic','miloradm','321','administrator')");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password,uloga) VALUES (2,'nenad','nenadovic','nenadn','123','prodavac')");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password,uloga) VALUES (3,'stefan','stefanovic','stefans','123','kupac')");

        MyDB.execSQL("Insert into kupci(id,ime,prezime,username,password) VALUES (1,'stefan','stefanovic','stefans','123')");

        MyDB.execSQL("Insert into prodavci(id,ime,prezime,username,password) VALUES (1,'nenad','nenadovic','nenadn','523')");
        MyDB.execSQL("Insert into artikli(id,naziv,opis,cena) VALUES (1,'monitor','philips','331.21')");
        MyDB.execSQL("Insert into artikli(id,naziv,opis,cena) VALUES (2,'aaaaaa','asda','331.21')");
        MyDB.execSQL("Insert into artikli(id,naziv,opis,cena) VALUES (3,'qwd','asd','231.21')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists kupci");
        MyDB.execSQL("drop Table if exists prodavci");
        MyDB.execSQL("drop Table if exists artikli");
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
            String uloga = cursor.getString(5);

            Korisnik korisnik = new Korisnik(id,ime,prezime,username,password,uloga);

            return korisnik;
        }
        else{
            return null;
        }

    }

    public Boolean insertData(String ime, String prezime, String username, String password, String uloga){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ime", ime);
        contentValues.put("prezime", prezime);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("uloga", uloga);

        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;

    }

    public Boolean insertKupci(String ime, String prezime, String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ime", ime);
        contentValues.put("prezime", prezime);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = MyDB.insert("kupci", null, contentValues);
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

    public Boolean insertProdavci(String ime, String prezime, String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ime", ime);
        contentValues.put("prezime", prezime);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = MyDB.insert("prodavci", null, contentValues);
        if(result==-1) return false;
        else
            return true;

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
}
