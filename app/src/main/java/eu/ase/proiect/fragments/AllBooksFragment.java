package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.proiect.MainActivity;
import eu.ase.proiect.R;
import eu.ase.proiect.asyncTask.Callback;
import eu.ase.proiect.database.model.Author;
import eu.ase.proiect.database.model.Book;
import eu.ase.proiect.database.service.BookService;
import eu.ase.proiect.util.BookAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllBooksFragment extends Fragment {

    public static final String BOOK_DETAILS_KEY = "book_details_key";
    public static final String AUTHOR_DETAILS_KEY = "author_details_key";
    public static final String BOOKS_KEY="book_key";
    public static final String AUTHOR_KEY = "author_key";


    private ListView listViewAllBooks;
    private List<Book> listBooks = new ArrayList<>();
    private List<Author> listAuthors = new ArrayList<>();

    public AllBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_books, container, false);

//        Initializare componente + adaugare adapter + setare titlu + preluare lista carti din main activity
        initComponents(view);

//         click pe item din listview
        listViewAllBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // trimit obiectul book in fragmentul BookDetailsFragment
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                BookDetailsFragment frg2 = new BookDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_DETAILS_KEY, listBooks.get(position));
                bundle.putSerializable(AUTHOR_DETAILS_KEY, getAuthorMeetBook(listBooks.get(position).getIdFKAuthor()));
                frg2.setArguments(bundle);
                ft.replace(R.id.main_frame_container, frg2);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


        return view;
    }


    private void initComponents(View view) {
        //initializare view
        listViewAllBooks = view.findViewById(R.id.lv_book);

//        preiau lista de carti din activitatea main
        listBooks = (List<Book>) getArguments().getSerializable(BOOKS_KEY);
        listAuthors = (List<Author>) getArguments().getSerializable(AUTHOR_KEY);

        //adaug adapter
        addBookAdapter();
//

//        setez titlu
        ((MainActivity) getActivity()).setActionBatTitle(getString(R.string.title_all_books));


  }

    public static AllBooksFragment newInstance(ArrayList<Book> listBooks, ArrayList<Author> listAuthors) {

        AllBooksFragment fragment = new AllBooksFragment();
        //Bundle este o clasa asemanatoare cu intentul, doar ca nu poate deschide activitati.
        //Este utilizata pentru transmiterea de informatii intre activitati/fragmente
        //in cazul curente adaugam lista de cheltuieli pentru a o afisa in ListView-ul din fragment Home
        Bundle bundle = new Bundle();
        bundle.putSerializable(BOOKS_KEY, listBooks);
        bundle.putSerializable(AUTHOR_KEY, listAuthors);
        fragment.setArguments(bundle);
        return fragment;

    }

    private void addBookAdapter(){
        BookAdapter bookAdapter = new BookAdapter(getContext().getApplicationContext(), R.layout.item_book, listBooks, listAuthors, getLayoutInflater());
        listViewAllBooks.setAdapter(bookAdapter);
    }

    public void notifyInternalAdapter(){
        ArrayAdapter adapter =  (ArrayAdapter) listViewAllBooks.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private Author getAuthorMeetBook(long idAuthor){
        for (Author a: listAuthors) {
            if(a.getIdAuthor() == idAuthor){
                return a;
            }
        }
        return null;
    }

}