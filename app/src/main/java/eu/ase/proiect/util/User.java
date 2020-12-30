package eu.ase.proiect.util;

import androidx.room.Ignore;

import java.util.HashMap;
import java.util.Map;

import eu.ase.proiect.database.model.Book;

public class User {

    private int idUser;
    private  String name;
    private String mail;
    private String password;
    public static Map<Long, Book> mapFavoriteBook= new HashMap<Long, Book>();
    public static Map<Integer,Book> mapBookRead = new HashMap<Integer, Book>();

    @Ignore
    public User() {
    }

    public User(int idUser, String name, String mail, String password) {
        this.idUser = idUser;
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

