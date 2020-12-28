package eu.ase.proiect.util;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

import eu.ase.proiect.R;
import eu.ase.proiect.database.model.Book;

public class Author implements Serializable {
    private int idAuthor;
    private String name;
    private String shortBiography;
    private String imgUrlAuthor;
//    private List<Book> listBooks;

    public Author(int id, String name, String shortBiography, String imgUrlAuthor) {
        this.idAuthor = id;
        this.name = name;
        this.shortBiography = shortBiography;
        this.imgUrlAuthor = imgUrlAuthor;
    }

    public int getId() {
        return idAuthor;
    }

    public void setId(int id) {
        this.idAuthor = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortBiography() {
        return shortBiography;
    }

    public void setShortBiography(String shortBiography) {
        this.shortBiography = shortBiography;
    }

    public String getImgUrlAuthor() {
        return imgUrlAuthor;
    }

    public void setImgUrlAuthor(String imgUrlAuthor) {
        this.imgUrlAuthor = imgUrlAuthor;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + idAuthor +
                ", name='" + name + '\'' +
                ", shortBiography='" + shortBiography + '\'' +
                ", imgUrlAuthor='" + imgUrlAuthor + '\'' +
                '}';
    }
}
