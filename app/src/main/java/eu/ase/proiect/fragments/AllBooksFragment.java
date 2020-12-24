package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import eu.ase.proiect.R;
import eu.ase.proiect.util.Book;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllBooksFragment extends Fragment {


    public static final String BOOKS_KEY = "books_key";

    public AllBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_books, container, false);
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
}