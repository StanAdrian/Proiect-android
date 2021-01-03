package eu.ase.proiect.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import eu.ase.proiect.LoginActivity;
import eu.ase.proiect.MainActivity;
import eu.ase.proiect.R;
import eu.ase.proiect.database.model.Author;
import eu.ase.proiect.database.model.Book;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private Button btnSave;
    private CheckBox checkBox_save_data;
    private CheckBox checkBox_automatic_login;
    private Button btnLogOut;
    private SharedPreferences preferences;
    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initComponents(view);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoginDataInSharedPreferences();
                Toast.makeText(getContext().getApplicationContext(), getString(R.string.succesfull_save),Toast.LENGTH_SHORT).show();
                if(preferences.getBoolean(LoginActivity.SAVE_LOGIN_DATA,false)){
                    btnLogOut.setVisibility(View.VISIBLE);
                }
                else{
                    btnLogOut.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initComponents(View view) {
        //        setez titlu
        ((MainActivity) getActivity()).setActionBatTitle(getString(R.string.title_settings));
        btnSave = view.findViewById(R.id.btn_save_data_login);
        btnLogOut=view.findViewById(R.id.btn_log_out);
        checkBox_save_data = view.findViewById(R.id.cb_save_data);
        checkBox_automatic_login = view.findViewById(R.id.cb_automatic_login);
        preferences = this.getActivity().getSharedPreferences(LoginActivity.PROFILE_SHARED_PREF, Context.MODE_PRIVATE);
        loadFromSharedPreference();
        if(checkBox_automatic_login.isChecked()){
            btnLogOut.setVisibility(View.INVISIBLE);
        }

    }

    private void loadFromSharedPreference(){
        checkBox_save_data.setChecked(preferences.getBoolean(LoginActivity.SAVE_LOGIN_DATA, false));
        checkBox_automatic_login.setChecked(preferences.getBoolean(LoginActivity.AUTOMATIC_LOGIN, false));
    }

    private void saveLoginDataInSharedPreferences() {
//        scriu in fisierul de preferinta numele si parola
        boolean checkBoxSave = checkBox_save_data.isChecked();
        boolean checkBoxAutomaticLogin = checkBox_automatic_login.isChecked();

//                salvarea in fisierul de preferinte
        SharedPreferences.Editor editor =preferences.edit();
        editor.putBoolean(LoginActivity.SAVE_LOGIN_DATA,checkBoxSave);
        editor.putBoolean(LoginActivity.AUTOMATIC_LOGIN,checkBoxAutomaticLogin);
        editor.apply();
    }

}