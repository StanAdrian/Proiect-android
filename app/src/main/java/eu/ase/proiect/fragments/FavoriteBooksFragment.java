package eu.ase.proiect.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ase.proiect.R;
import eu.ase.proiect.util.Book;
import eu.ase.proiect.util.BookAdapter;
import eu.ase.proiect.util.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteBooksFragment extends Fragment {

    private ListView lvFavoriteBook;
    private List<Book> listFavoriteBooks = new ArrayList<>();
    public FavoriteBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_books, container, false);

        initComponents(view);
        addBookAdapter();

        return view;
    }

    private void initComponents(View view) {
        lvFavoriteBook = view.findViewById(R.id.lv_favorite_book);
        for (Map.Entry<Integer, Book> item : User.mapFavoriteBook.entrySet()) {
            listFavoriteBooks.add(item.getValue());
        }
    }





    private void addBookAdapter(){
        BookAdapter bookAdapter = new BookAdapter(getContext().getApplicationContext(), R.layout.item_book, listFavoriteBooks, getLayoutInflater());
        lvFavoriteBook.setAdapter(bookAdapter);
    }

    private void notifyAdapter(){
        ArrayAdapter adapter =  (ArrayAdapter) lvFavoriteBook.getAdapter();
        adapter.notifyDataSetChanged();
    }

}