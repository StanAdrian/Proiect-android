package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.ase.proiect.MainActivity;
import eu.ase.proiect.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksReadFragment extends Fragment {


    public BooksReadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books_read, container, false);
        initComponents();

        return view;
    }

    private void initComponents() {
        //        setez titlu
        ((MainActivity) getActivity()).setActionBatTitle(getString(R.string.title_book_read));
    }

}