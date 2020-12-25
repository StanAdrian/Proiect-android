package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.proiect.R;
import eu.ase.proiect.util.Book;
import eu.ase.proiect.util.BookAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BookDetailsFragment extends Fragment {

    private Book book;
    private List<Book> lBooks = new ArrayList<>();
    private ListView lvBookDetails;
    private TextView tvDescription;

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
        Bundle bundle = this.getArguments();
        Book book = (Book)bundle.getSerializable(AllBooksFragment.BOOK_DETAILS_KEY);

        if(book != null) {
            lBooks.add(book);
        }

        tvDescription.setText(book.getDescription());
    }


    private void addBookAdapter(){
        BookAdapter bookAdapter = new BookAdapter(getContext().getApplicationContext(), R.layout.item_book, lBooks, getLayoutInflater());
        lvBookDetails.setAdapter(bookAdapter);
    }



}