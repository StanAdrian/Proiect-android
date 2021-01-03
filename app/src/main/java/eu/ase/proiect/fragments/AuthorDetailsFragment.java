package eu.ase.proiect.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import eu.ase.proiect.MainActivity;
import eu.ase.proiect.R;
import eu.ase.proiect.database.model.Author;
import eu.ase.proiect.database.model.Book;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthorDetailsFragment extends Fragment {
    private Author author;
    private ArrayList<Author> listAuthor = new ArrayList<>();
    private ArrayList<Book> listBooks = new ArrayList<>();

    public AuthorDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_author, container, false);
        initComponents();

        return view;
    }

    private void initComponents() {
        //        setez titlu
        ((MainActivity) getActivity()).setActionBatTitle(getString(R.string.title_book_read));
        getAuthorFromBookDetailsFragment();
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




}