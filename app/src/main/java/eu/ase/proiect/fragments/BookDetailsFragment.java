package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eu.ase.proiect.R;
import eu.ase.proiect.util.Book;
import eu.ase.proiect.util.BookAdapter;
import eu.ase.proiect.util.User;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BookDetailsFragment extends Fragment {

    private Book book;
    private List<Book> lBooks = new ArrayList<>();
    private ListView lvBookDetails;
    private TextView tvDescription;
    private Button btnAddToFavorite;

    public BookDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);
        initComponents(view);
        addBookAdapter();


        return view;
    }

    private void initComponents(View view) {
        lvBookDetails = view.findViewById(R.id.lv_book_details);
        tvDescription = view.findViewById(R.id.tv_f_book_details_description);
        btnAddToFavorite = view.findViewById(R.id.btn_f_book_details_addToFavorite);
        Bundle bundle = this.getArguments();
        book = (Book)bundle.getSerializable(AllBooksFragment.BOOK_DETAILS_KEY);
        if(book != null) {
            lBooks.add(book);
            tvDescription.setText(book.getDescription());
        }

        btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.mapFavoriteBook == null){
                    User.mapFavoriteBook = new HashMap<Integer, Book>();
                    User.mapFavoriteBook.put(book.getId(),book);
                } else if(!User.mapFavoriteBook.containsKey(book.getId())){
                    User.mapFavoriteBook.put(book.getId(),book);
                }
            }
        });

    }


    private void addBookAdapter(){
        BookAdapter bookAdapter = new BookAdapter(getContext().getApplicationContext(), R.layout.item_book, lBooks, getLayoutInflater());
        lvBookDetails.setAdapter(bookAdapter);
    }



}