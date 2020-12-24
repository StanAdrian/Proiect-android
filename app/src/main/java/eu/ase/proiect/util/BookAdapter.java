package eu.ase.proiect.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

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

        }
        return view;
    }






}
