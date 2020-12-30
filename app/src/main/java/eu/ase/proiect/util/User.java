package eu.ase.proiect.util;

import java.util.HashMap;
import java.util.Map;

import eu.ase.proiect.database.model.Book;

public class User {

    private static int idUser;
    public static String email;
    public static String username;
    public static String password;
    // 0 - barbat, 1 -feminin
    public static int sex;

    public static Map<Long, Book> mapFavoriteBook= new HashMap<Long, Book>();
    public static Map<Integer,Book> mapBookRead = new HashMap<Integer, Book>();





}
