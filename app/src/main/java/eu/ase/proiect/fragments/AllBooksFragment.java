package eu.ase.proiect.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.proiect.MainActivity;
import eu.ase.proiect.R;
import eu.ase.proiect.util.Book;
import eu.ase.proiect.util.BookAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllBooksFragment extends Fragment {


    public static final String BOOK_KEY = "book_key";
    public static final String BOOK_DETAILS_KEY = "book_details_key";
    private ListView listViewAllBooks;
    private List<Book> listBooks = new ArrayList<>();

    public AllBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_books, container, false);

        initComponents(view);
        listBooks.add(new Book("An American Marriage","Descriere","Tayari Jones", "URLImage", 248, 11, 2.8f, R.drawable.book1));
        listBooks.add(new Book("The Great Gasby","Descriere","F. Scott Fitzgerland", "URLImage", 308, 21, 4.2f, R.drawable.gatsby2));
        listBooks.add(new Book("The fault in our stars","Descriere","John Green", "URLImage", 321, 34, 4.8f, R.drawable.thefault));

        listViewAllBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
                Bundle bundle = new Bundle();
                bundle.putSerializable(AllBooksFragment.BOOK_DETAILS_KEY, listBooks.get(position));
                BookDetailsFragment frg2 = new BookDetailsFragment();
                frg2.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_frame_container,frg2).commit();

            }
        });



        return view;

    }


    private void initComponents(View view) {
        listViewAllBooks = view.findViewById(R.id.lv_book);
        addBookAdapter();

    }

    public static AllBooksFragment newInstance(ArrayList<Book> listBooks) {
        AllBooksFragment fragment = new AllBooksFragment();
        //Bundle este o clasa asemanatoare cu intentul, doar ca nu poate deschide activitati.
        //Este utilizata pentru transmiterea de informatii intre activitati/fragmente
        //in cazul curente adaugam lista de cheltuieli pentru a o afisa in ListView-ul din fragment Home
        Bundle bundle = new Bundle();
        //bundle.putParcelableArrayList(AllBooksFragment.BOOKS_KEY, listBooks);
        fragment.setArguments(bundle);
        return fragment;
    }





    private void addBookAdapter(){
        BookAdapter bookAdapter = new BookAdapter(getContext().getApplicationContext(), R.layout.item_book, listBooks, getLayoutInflater());
        listViewAllBooks.setAdapter(bookAdapter);
    }

    private void notifyAdapter(){
        ArrayAdapter adapter =  (ArrayAdapter) listViewAllBooks.getAdapter();
        adapter.notifyDataSetChanged();
    }

}