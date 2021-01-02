package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.ase.proiect.MainActivity;
import eu.ase.proiect.R;
import eu.ase.proiect.database.model.Book;
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
    private Button btnAddToFavorites;
    private Button btnRemoveFromFavorites;

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
        //se poate face scroll in descrierea textview-ului
        tvDescription.setMovementMethod(new ScrollingMovementMethod());
        btnAddToFavorites = view.findViewById(R.id.btn_f_book_details_addToFavorite);
        btnRemoveFromFavorites = view. findViewById(R.id.btn_f_book_details_removeFromFavorite);


        //preiau obiectul book din fragmentul AllBookFragment
        getBookFromAllBookFragment();

//              setez titlu
        ((MainActivity) getActivity()).setActionBatTitle(getString(R.string.title_book_details));


//           daca cartea exista in lista de favorite, btn Add e invizibil, altfel btn Remove e invizibil
        updateVisibilityButtons(view);


        addBookAdapter();
    }

    private void updateVisibilityButtons(View view) {
        if(User.mapFavoriteBook.containsKey(book.getIdBook())) {
            btnAddToFavorites.setVisibility(view.INVISIBLE);
            btnRemoveFromFavorites.setVisibility(view.VISIBLE);
        } else{
            btnAddToFavorites.setVisibility(view.VISIBLE);
            btnRemoveFromFavorites.setVisibility(view.INVISIBLE);
        }
    }

    private void evClickBtnRemoveFromFavorite() {
        // eveniment click buton RemoveBookFromFavorites
        btnRemoveFromFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.mapFavoriteBook.containsKey(book.getIdBook())){
                    User.mapFavoriteBook.remove(book.getIdBook());
                    updateVisibilityButtons(getView());
                    Toast.makeText(getContext(), getString(R.string.confirm_remove_to_favorite,book.getTitle()),Toast.LENGTH_SHORT).show();
                    //faca neagra inima
                    ImageView favimg=getView().findViewById(R.id.item_img_favorite);
                    favimg.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
                else{
                    Toast.makeText(getContext(), getString(R.string.book_no_exist, book.getTitle()),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void evClickBtnAddToFavorite() {
        //         eveniment click buton AddBookToFavorites
        btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!User.mapFavoriteBook.containsKey(book.getIdBook())){
                    User.mapFavoriteBook.put(book.getIdBook(),book);
                    updateVisibilityButtons(getView());
                    Toast.makeText(getContext().getApplicationContext(),getString(R.string.confirm_add_to_favorite, book.getTitle()), Toast.LENGTH_LONG).show();
                    //faca rosu inima
                    ImageView favimg=getView().findViewById(R.id.item_img_favorite);
                    favimg.setImageResource(R.drawable.ic_favorite_red_24);
                } else {
                    Toast.makeText(getContext().getApplicationContext(), getString(R.string.book_exist, book.getTitle()), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //    preluare book din allbookFragment
    private void getBookFromAllBookFragment() {
        Bundle bundle = getArguments();
        book = (Book)bundle.getSerializable(AllBooksFragment.BOOK_DETAILS_KEY);
        if(book != null) {
            lBooks.add(book);
            tvDescription.setText(book.getDescription());
        } else {
            Toast.makeText(getContext().getApplicationContext(), R.string.error_message_transfer_between_fragment,Toast.LENGTH_LONG).show();
        }
    }


    private void addBookAdapter(){
        BookAdapter bookAdapter = new BookAdapter(getContext().getApplicationContext(), R.layout.item_book, lBooks, getLayoutInflater());
        lvBookDetails.setAdapter(bookAdapter);
    }

    private void notifyAdapter(){
        ArrayAdapter adapter =  (ArrayAdapter) lvBookDetails.getAdapter();
        adapter.notifyDataSetChanged();
    }


}