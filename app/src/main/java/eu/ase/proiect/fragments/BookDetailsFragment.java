package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.ase.proiect.MainActivity;
import eu.ase.proiect.R;
import eu.ase.proiect.asyncTask.Callback;
import eu.ase.proiect.database.model.Book;
import eu.ase.proiect.database.service.AuthorService;
import eu.ase.proiect.database.service.BookService;
import eu.ase.proiect.util.BookAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BookDetailsFragment extends Fragment {

    private Book book;
    private List<Book> listBooks = new ArrayList<>();
    private List<Book> listFavoriteBooks = new ArrayList<>();
    private ListView lvBookDetails;
    private TextView tvDescription;
    private Button btnAddToFavorites;
    private Button btnRemoveFromFavorites;

    private BookService bookService;
    private AuthorService authorService;


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

        evClickBtnAddToFavorite();
        evClickBtnRemoveFromFavorite();



        return view;
    }

    private void initComponents(View view) {
//        initializare views
        lvBookDetails = view.findViewById(R.id.lv_book_details);
        tvDescription = view.findViewById(R.id.tv_f_book_details_description);
        btnAddToFavorites = view.findViewById(R.id.btn_f_book_details_addToFavorite);
        btnRemoveFromFavorites = view. findViewById(R.id.btn_f_book_details_removeFromFavorite);


        //preiau obiectul book din fragmentul AllBookFragment
        getBookFromAllBookFragment();

//              setez titlu
        ((MainActivity) getActivity()).setActionBatTitle(getString(R.string.title_book_details));

        addBookAdapter();

        bookService = new BookService(getContext().getApplicationContext());
        authorService = new AuthorService(getContext().getApplicationContext());


        //        lista de carti favorite este populata cu cartile din baza de date
        bookService.getAll(getAllBooksDbCallback());


//           daca cartea exista in lista de favorite, btn Add e invizibil, altfel btn Remove e invizibil
        updateVisibilityButtons(view);



    }

    //    preluare book din allbookFragment
    private void getBookFromAllBookFragment() {
        Bundle bundle = getArguments();
        book = (Book)bundle.getSerializable(AllBooksFragment.BOOK_DETAILS_KEY);
        if(book != null) {
            listBooks.add(book);
            tvDescription.setText(book.getDescription());
        } else {
            Toast.makeText(getContext().getApplicationContext(), R.string.error_message_transfer_between_fragment,Toast.LENGTH_LONG).show();
        }
    }

    private void updateVisibilityButtons(View view) {
        if(isFavorite()) {
            btnAddToFavorites.setVisibility(view.INVISIBLE);
            btnRemoveFromFavorites.setVisibility(view.VISIBLE);
        } else{
            btnAddToFavorites.setVisibility(view.VISIBLE);
            btnRemoveFromFavorites.setVisibility(view.INVISIBLE);
        }
    }

    private boolean isFavorite(){
        Book book2 = listBooks.get(0);
        for (Book b: listFavoriteBooks) {
//            && book2.getIs_favorite() == 1
            if(b.getIdBook() == book2.getIdBook()){
                return true;
            }
        }
        return false;
    }



    private void evClickBtnAddToFavorite() {
        //         eveniment click buton AddBookToFavorites
        btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFavorite()){
//                  add book in db
                    bookService.insertBook(insertBookIntoDbCallback(),book);

//                    TODO update is_fav=1

                } else {
                    Toast.makeText(getContext().getApplicationContext(), getString(R.string.book_exist, book.getTitle()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void evClickBtnRemoveFromFavorite() {
        // eveniment click buton RemoveBookFromFavorites
        btnRemoveFromFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavorite()){
                    bookService.delete(deleteBookFromDbCallback(),book);

                 }
                else{
                    Toast.makeText(getContext(), getString(R.string.book_no_exist, book.getTitle()),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void addBookAdapter(){
        BookAdapter bookAdapter = new BookAdapter(getContext().getApplicationContext(), R.layout.item_book, listBooks, getLayoutInflater());
        lvBookDetails.setAdapter(bookAdapter);
    }


    /*************          DATABASE         ******************/
    private Callback<Book> insertBookIntoDbCallback(){
        return new Callback<Book>() {
            @Override
            public void runResultOnUiThread(Book result) {
                if(result != null){
                    listFavoriteBooks.add(result);
                    updateVisibilityButtons(getView());
                    Toast.makeText(getContext().getApplicationContext(),getString(R.string.confirm_add_to_favorite, book.getTitle()), Toast.LENGTH_SHORT).show();

                }
            }
        };
    }

    private Callback<List<Book>> getAllBooksDbCallback(){
        return new Callback<List<Book>>() {
            @Override
            public void runResultOnUiThread(List<Book> result) {
                if(result != null){
                    listFavoriteBooks.clear();
                    listFavoriteBooks.addAll(result);
                    updateVisibilityButtons(getView());
                }

            }
        };
    }


    private Callback<Book> updateBookIntoDbCallback(){
        return new Callback<Book>() {
            @Override
            public void runResultOnUiThread(Book result) {
                if(result!=null){
//                    TODO seminar10: 1:35(hh:mm)
                }
            }
        };
    }

    private Callback<Integer> deleteBookFromDbCallback(){
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if(result != -1){
                    bookService.getAll(getAllBooksDbCallback());
                    updateVisibilityButtons(getView());
                    Toast.makeText(getContext(), getString(R.string.confirm_remove_to_favorite,book.getTitle()),Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

}