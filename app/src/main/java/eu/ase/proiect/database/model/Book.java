package eu.ase.proiect.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "books")
public class Book implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idBook")
    private long idBook;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "imgUrl")
    private String imgUrl;
    @ColumnInfo(name = "pages")
    private int pages;
    @ColumnInfo(name = "review")
    private int review;
    @ColumnInfo(name = "rating")
    private float rating;
    @ColumnInfo(name = "drawableResource")
    private int drawableResource; // this for testing purpos...
    @ColumnInfo(name = "is_favorite")
    private int is_favorite;
    @ColumnInfo(name = "is_read")
    private int is_read;
    @ColumnInfo(name = "idAuthor")
    private long idAuthor;
    @ColumnInfo(name = "pdfUrl")
    private String pdfUrl;


    @Ignore
    public Book() {
    }

    public Book(long idBook, String title, String description, String imgUrl, int pages, int review, float rating, int drawableResource,
                int is_favorite, int is_read, long idAuthor,String pdfUrl) {
        this.idBook = idBook;
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.pages = pages;
        this.review = review;
        this.rating = rating;
        this.drawableResource = drawableResource;
        this.is_favorite=is_favorite;
        this.is_read = is_read;
        this.idAuthor = idAuthor;
        this.pdfUrl=pdfUrl;

    }


    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getDrawableResource() {
        return drawableResource;
    }

    public void setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public long getIdBook() {
        return idBook;
    }

    public void setIdBook(long idBook) {
        this.idBook = idBook;
    }

    @Override
    public String toString() {
        return "Book{" +
                "idBook=" + idBook +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", pages=" + pages +
                ", review=" + review +
                ", rating=" + rating +
                ", drawableResource=" + drawableResource +
                ", is_favorite=" + is_favorite +
                ", is_read=" + is_read +
                ", idAuthor=" +
                '}';
    }
}
