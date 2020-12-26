package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        //initializare views
        lvBookDetails = view.findViewById(R.id.lv_book_details);
        tvDescription = view.findViewById(R.id.tv_f_book_details_description);
        btnAddToFavorite = view.findViewById(R.id.btn_f_book_details_addToFavorite);


        //preiau obiectul book din fragmentul AllBookFragment
        Bundle bundle = getArguments();
        book = (Book)bundle.getSerializable(AllBooksFragment.BOOK_DETAILS_KEY);
        if(book != null) {
            lBooks.add(book);
            tvDescription.setText(book.getDescription());
        } else {
            Toast.makeText(getContext().getApplicationContext(), R.string.error_message_transfer_between_fragment,Toast.LENGTH_LONG).show();
        }


        // eveniment click buton AddFavorite
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