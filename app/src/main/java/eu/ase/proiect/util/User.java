package eu.ase.proiect.util;

import java.util.HashMap;
import java.util.Map;

import eu.ase.proiect.database.model.Book;

public class User {

    private int idUser;
    public static String name;
    public static Map<Long, Book> mapFavoriteBook= new HashMap<Long, Book>();
    public static Map<Integer,Book> mapBookRead = new HashMap<Integer, Book>();;


}
