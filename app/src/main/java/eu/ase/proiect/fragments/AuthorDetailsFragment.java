package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.ase.proiect.MainActivity;
import eu.ase.proiect.R;
import eu.ase.proiect.asyncTask.Callback;
import eu.ase.proiect.database.model.Author;
import eu.ase.proiect.database.model.Book;
import eu.ase.proiect.database.service.AuthorService;
import eu.ase.proiect.database.service.BookService;
import eu.ase.proiect.util.AuthorAdapter;
import eu.ase.proiect.util.BookAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthorDetailsFragment extends Fragment {
    private Author author;
    private ArrayList<Author> listAuthor = new ArrayList<>();
    private ArrayList<Book> listBooks = new ArrayList<>();
    private ListView lvAuthor;
    private TextView tvBiographyAuthor;
    private ListView lvAuthorWithBooks;

    private BookService bookService;


    public AuthorDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_author, container, false);
        initComponents(view);

                                                                            return view;
    }

    private void initComponents(View view) {
        lvAuthor = view.findViewById(R.id.lv_author_details);
        tvBiographyAuthor = view.findViewById(R.id.tv_f_author_details_description);
        // activez scroll
        tvBiographyAuthor.setMovementMethod(new ScrollingMovementMethod());
        lvAuthorWithBooks = view.findViewById(R.id.lv_author_books);

        bookService = new BookService(getContext().getApplicationContext());



        //        setez titlu
        ((MainActivity) getActivity()).setActionBatTitle(getString(R.string.title_book_read));
        getAuthorFromBookDetailsFragment();
        addAuthorAdapter();
        tvBiographyAuthor.setText(author.getShortBiography());


//    TODO preluat lista carti ale unui autor
        bookService.getBooksWithIdAuthor(getAllBooksWithIdAuthorDbCallback(), author.getIdAuthor());

//        add adapter
        addBookAdapter();
    }
    private void getAuthorFromBookDetailsFragment() {
        Bundle bundle = getArguments();
        author = (Author)bundle.getSerializable(BookDetailsFragment.AUTHOR_KEY);
        if(author != null) {
            listAuthor.add(author);
        } else {
            Toast.makeText(getContext().getApplicationContext(), R.string.error_message_transfer_between_fragment,Toast.LENGTH_SHORT).show();
        }
    }


    private void addAuthorAdapter(){
        AuthorAdapter authorAdapter = new AuthorAdapter(getContext().getApplicationContext(), R.layout.item_author, listAuthor, getLayoutInflater());
        lvAuthor.setAdapter(authorAdapter);
    }

    public void notifyAuthorAdapter(){
        ArrayAdapter adapter =  (ArrayAdapter) lvAuthor.getAdapter();
        adapter.notifyDataSetChanged();
    }


    private void addBookAdapter(){
        BookAdapter bookAdapter = new BookAdapter(getContext().getApplicationContext(), R.layout.item_book, listBooks, listAuthor, getLayoutInflater());
        lvAuthorWithBooks.setAdapter(bookAdapter);
    }

    public void notifyBookAdapter(){
        ArrayAdapter adapter =  (ArrayAdapter) lvAuthorWithBooks.getAdapter();
        adapter.notifyDataSetChanged();
    }


    private Callback<List<Book>> getAllBooksWithIdAuthorDbCallback(){
        return new Callback<List<Book>>() {
            @Override
            public void runResultOnUiThread(List<Book> result) {
                if(result != null){
                    listBooks.clear();
                    listBooks.addAll(result);
                }
            }
        };
    }

}