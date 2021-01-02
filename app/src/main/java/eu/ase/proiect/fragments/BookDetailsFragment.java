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
import eu.ase.proiect.database.model.Author;
import eu.ase.proiect.database.model.Book;
import eu.ase.proiect.database.service.AuthorService;
import eu.ase.proiect.database.service.BookService;
import eu.ase.proiect.util.BookAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BookDetailsFragment extends Fragment {

    private Book book; //primita ca parametru la deschidere
    private Author author; // primit ca parametru la deschidere
    private List<Book> listBooks = new ArrayList<>(); //contine o carte si e trimitsa ca parametru pe adapter
    private List<Author> listAuthor = new ArrayList<>(); //contine un autor si e trimitsa ca parametru pe adapter
    private List<Book> listFavoriteBooks = new ArrayList<>(); // contine toate cartile favorite
    private List<Book> listAllBooksDb = new ArrayList<>(); // contine toate cartile
    private List<Author> listAllAuthors = new ArrayList<>(); // contine toati autorii

    private ListView lvBookDetails;
    private TextView tvDescription;
    private Button btnAddToFavorites;
    private Button btnRemoveFromFavorites;

    private BookService bookService;
    private AuthorService authorService;
    private Integer eachBooksHasAuthor=0;

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


        //preiau obiectul book si author din fragmentul AllBookFragment  /  FavoriteBooksFragment
        getBookFromAllBookFragment();

//              setez titlu
        ((MainActivity) getActivity()).setActionBatTitle(getString(R.string.title_book_details));

        bookService = new BookService(getContext().getApplicationContext());
        authorService = new AuthorService(getContext().getApplicationContext());

        //        lista de carti favorite este populata cu cartile din baza de date
        bookService.getAllFavoriteBooks(getAllFavoriteBooksDbCallback());
        authorService.getAll(getAllAuthorsDbCallback());

        addBookAdapter();

//           daca cartea exista in lista de favorite, btn Add e invizibil, altfel btn Remove e invizibil
        updateVisibilityButtons(view);



    }

    //    preluare book / author din allbookFragment / favoriteBooksFragmnet
    private void getBookFromAllBookFragment() {
        Bundle bundle = getArguments();
        book = (Book)bundle.getSerializable(AllBooksFragment.BOOK_DETAILS_KEY);
        author = (Author)bundle.getSerializable(AllBooksFragment.AUTHOR_DETAILS_KEY);
        if(book != null && author != null) {
            listBooks.add(book);
            tvDescription.setText(book.getDescription());
            listAuthor.add(author);
        } else {
            Toast.makeText(getContext().getApplicationContext(), R.string.error_message_transfer_between_fragment,Toast.LENGTH_LONG).show();
        }
    }

    private void updateVisibilityButtons(View view) {
        if(isFavoriteBook()) {
            btnAddToFavorites.setVisibility(view.INVISIBLE);
            btnRemoveFromFavorites.setVisibility(view.VISIBLE);
        } else{
            btnAddToFavorites.setVisibility(view.VISIBLE);
            btnRemoveFromFavorites.setVisibility(view.INVISIBLE);
        }
    }

    private boolean isFavoriteBook(){
        Book book2 = listBooks.get(0);
        for (Book b: listFavoriteBooks) {
            if(b.getIdBook() == book2.getIdBook()){
                book.setIs_read(b.getIs_read());
                return true;
            }
        }
        return false;
    }

    private boolean authorExist() {
        Author author2 = listAuthor.get(0);
        for (Author a: listAllAuthors) {
            if(a.getIdAuthor() == author2.getIdAuthor()){
                return true;
            }
        }
        return false;
    }



    //         eveniment click buton AddBookToFavorites
    private void evClickBtnAddToFavorite() {
        btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFavoriteBook()){
                    if(!authorExist()){
//                        add author in db  + in Callback  se face insert la carte + update la listFavoriteBooks
                        authorService.insertAuthor(insertAuthorIntoDbCallback(), author);
                    } else{
                        book.setIs_favorite(1);
                        bookService.insertBook(insertBookIntoDbCallback(),book);
                    }
                }

            }
        });
    }

    // eveniment click buton RemoveBookFromFavorites
    private void evClickBtnRemoveFromFavorite() {
        btnRemoveFromFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavoriteBook()){
                    if(book.getIs_read()==0){
                        //TODO delete book, after author
                        bookService.delete(deleteBookFromDbCallback(),book);
                    } else{
                        //update book: is_favorite = 0
                        book.setIs_favorite(0);
                        bookService.updateBook(updateBookIntoDbCallback(), book);

                    }
                 }

            }
        });
    }


    private void addBookAdapter(){
        BookAdapter bookAdapter = new BookAdapter(getContext().getApplicationContext(), R.layout.item_book, listBooks, listAuthor, getLayoutInflater());
        lvBookDetails.setAdapter(bookAdapter);
    }

    public void notifyAdapter(){
        ArrayAdapter adapter =  (ArrayAdapter) lvBookDetails.getAdapter();
        adapter.notifyDataSetChanged();
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
                    notifyAdapter();
//                    book.setIs_favorite(1);
//                    bookService.updateBook(updateBookIntoDbCallback(),book);
                }
            }
        };
    }

    private Callback<Author> insertAuthorIntoDbCallback(){
        return new Callback<Author>() {
            @Override
            public void runResultOnUiThread(Author result) {
                if(result != null){
                    listAllAuthors.add(result);
                    book.setIs_favorite(1);
                    bookService.insertBook(insertBookIntoDbCallback(), book);

                }
            }
        };
    }

    private Callback<List<Book>> getAllFavoriteBooksDbCallback(){
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

    private Callback<List<Author>> getAllAuthorsDbCallback(){
        return new Callback<List<Author>>() {
            @Override
            public void runResultOnUiThread(List<Author> result) {
                if(result != null){
                    listAllAuthors.clear();
                    listAllAuthors.addAll(result);
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
                    notifyAdapter();
                }
            }
        };

    }

    private Callback<Integer> deleteBookFromDbCallback(){
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if(result != -1){
                    book.setIs_favorite(0);
                    notifyAdapter();
                    bookService.getAllFavoriteBooks(getAllFavoriteBooksDbCallback());
                    updateVisibilityButtons(getView());

                    bookService.eachBooksHasAuthor(eachBooksHasAuthorCallback(),book.getIdFKAuthor());

                    if(eachBooksHasAuthor>0){
                        authorService.delete(deleteAuthorFromDbCallback(),author);
                    }
                    Toast.makeText(getContext(), getString(R.string.confirm_remove_to_favorite,book.getTitle()),Toast.LENGTH_SHORT).show();
                }
            }
        };
    }


    private Callback<Integer> deleteAuthorFromDbCallback(){
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if(result != -1){
                    //update listAllAuthors
                    authorService.getAll(getAllAuthorsDbCallback());
                }
            }
        };
    }

    private Callback<Integer> eachBooksHasAuthorCallback(){
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if(result != -1){
                    eachBooksHasAuthor = result;
                }
            }
        };
    }



}