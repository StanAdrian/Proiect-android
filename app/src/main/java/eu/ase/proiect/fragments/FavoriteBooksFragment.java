package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu.ase.proiect.MainActivity;
import eu.ase.proiect.R;
import eu.ase.proiect.asyncTask.Callback;
import eu.ase.proiect.database.model.Book;
import eu.ase.proiect.database.service.BookService;
import eu.ase.proiect.util.BookAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteBooksFragment extends Fragment implements Serializable {

    public static final String BOOK_DETAILS_KEY = "book_details_key";
    private ListView lvFavoriteBooks;
    private List<Book> listFavoriteBooks = new ArrayList<>();
    private BookService bookService;


    public FavoriteBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_books, container, false);

        initComponents(view);

//      click pe item din list view
        lvFavoriteBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // trimit obiectul book in fragmentul BookDetailsFragment
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                BookDetailsFragment frg2 = new BookDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_DETAILS_KEY, listFavoriteBooks.get(position));
                frg2.setArguments(bundle);
                ft.replace(R.id.main_frame_container, frg2);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        return view;
    }

    private void initComponents(View view) {

        lvFavoriteBooks = view.findViewById(R.id.lv_favorite_book);

        addBookAdapter();

        //      Preluare lista carti favorite din Db SQLite
        bookService = new BookService(getContext().getApplicationContext());
        bookService.getAll(getAllBooksDbCallback());

        //        setez titlu
        ((MainActivity) getActivity()).setActionBatTitle(getString(R.string.title_favorite_book));



    }





    private void addBookAdapter(){
        BookAdapter bookAdapter = new BookAdapter(getContext().getApplicationContext(), R.layout.item_book, listFavoriteBooks, getLayoutInflater());
        lvFavoriteBooks.setAdapter(bookAdapter);
    }

    public void notifyInternalAdapter(){
        ArrayAdapter adapter =  (ArrayAdapter) lvFavoriteBooks.getAdapter();
        adapter.notifyDataSetChanged();
    }

    /*************          DATABASE         ******************/
    private Callback<List<Book>> getAllBooksDbCallback(){
        return new Callback<List<Book>>() {
            @Override
            public void runResultOnUiThread(List<Book> result) {
                if(result != null){
                    listFavoriteBooks.clear();
                    listFavoriteBooks.addAll(result);
                    notifyInternalAdapter();
                }
            }
        };
    }

    private Callback<List<Book>> getAllBooksFavoriteDbCallback(){
        return new Callback<List<Book>>() {
            @Override
            public void runResultOnUiThread(List<Book> result) {
                listFavoriteBooks.clear();
                listFavoriteBooks.addAll(result);
                notifyInternalAdapter();
            }
        };
    }

}