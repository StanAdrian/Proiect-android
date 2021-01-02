package eu.ase.proiect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText nume_utilizator,email_utilizator,parola_utilizator;
    private Button btn_log_in,btn_sign_up;
    private ProgressBar baraProgresLogIN;
    private ImageView imagine_utilizator;

    private CheckBox checkBox_save;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();

        imagine_utilizator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cand da clic pe imagine sa isi faca cont, o sa vad cum fac ca atunci cand da pe imagine sa aleaga din mai multe conturi si sa le apara imaginea
                Intent regActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(regActivity);
                finish();
            }
        });
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aici se face mutarea pe pagina de inregistrare
                Intent regActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(regActivity);
                finish();
            }
        });
        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baraProgresLogIN.setVisibility(View.VISIBLE);
                btn_log_in.setVisibility(View.INVISIBLE);
                final String mail=email_utilizator.getText().toString();
                final String parola=parola_utilizator.getText().toString();
                if (mail.isEmpty() || parola.isEmpty()){
                    arataMesaj("Verificati campurile!");
                }
                else {
                    signIN(mail,parola);

                }
            }
        });
    }

    private void initComponents() {
        nume_utilizator=findViewById(R.id.log_username);
        email_utilizator=findViewById(R.id.log_username);
        parola_utilizator=findViewById(R.id.log_password);
        btn_log_in=findViewById(R.id.log_in_btn);
        btn_sign_up=findViewById(R.id.log_signup_btn);
        baraProgresLogIN=findViewById(R.id.log_progressBar);
        imagine_utilizator=findViewById(R.id.log_ImageView);
        mAuth= FirebaseAuth.getInstance();
        checkBox_save = findViewById(R.id.checkBox_save_user_and_password);

        preferences = getSharedPreferences("profileSharedPref", MODE_PRIVATE);

    }

    private void signIN(String mail, String parola) {
        //folosim functiile din firebase pt login
    mAuth.signInWithEmailAndPassword(mail,parola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful())
            {
                saveLoginDataInSharedPreferences();


                baraProgresLogIN.setVisibility(View.INVISIBLE);
                btn_log_in.setVisibility(View.VISIBLE);
                //functie de trimitere in activitatea principala
                updateUI();
            }
            else {
                arataMesaj(task.getException().getMessage());
                baraProgresLogIN.setVisibility(View.INVISIBLE);
                btn_log_in.setVisibility(View.VISIBLE);
            }
        }
    });
    }



    private void saveLoginDataInSharedPreferences() {
        //                scriu in fisierul de preferinta numele si parola
        boolean checkBoxSave = checkBox_save.isChecked();

//                salvarea in fisierul de preferinte
        SharedPreferences.Editor editor =preferences.edit();
        editor.putBoolean("saveLoginData",checkBoxSave);
        editor.putString("email",email_utilizator.getText().toString());
        editor.putString("password", parola_utilizator.getText().toString());
        editor.apply();
    }

    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(homeActivity);
        finish();
    }

    private void arataMesaj(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //aici se face logare automata
        FirebaseUser user=mAuth.getCurrentUser();
        if (user != null){
            //userul este deja conectat deci il trimitem in ecranul principal
            updateUI();
        }
    }
}