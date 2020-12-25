package eu.ase.proiect.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.proiect.R;

public class BookAdapter extends ArrayAdapter<Book> {

    private Context context;
    private List<Book> listBooks;
    private LayoutInflater inflater;
    private int resource;

    public BookAdapter(@NonNull Context context,
                       int resource,
                       @NonNull List<Book> objects,
                       LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listBooks = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource,parent,false);
        Book book = listBooks.get(position);

        if(book != null) {
            addBookTitle(view,book.getTitle());
            addBookAuthor(view, book.getAuthor());
            addRatingBar(view, book.getRating());
        }
        return view;

    }

    private void addBookTitle(View view, String title) {
        TextView textView = view.findViewById(R.id.item_book_title);
        populateFromView(title, textView);
    }

    private void addBookAuthor(View view, String author) {
        TextView textView = view.findViewById(R.id.item_book_author);
        populateFromView(author, textView);
    }

    private void addRatingBar(View view, float rating){
        RatingBar ratingBar = view.findViewById(R.id.item_book_ratingbar);
        if(rating >= 0){
            ratingBar.setRating(rating);
        } else{
            ratingBar.setRating(0);
        }
    }


    private void populateFromView(String string, TextView textView) {
        if (string != null && !string.trim().isEmpty()) {
            textView.setText(string);
        } else {
            textView.setText(R.string.no_content);
        }
    }

}
