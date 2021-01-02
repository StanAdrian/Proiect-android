package eu.ase.proiect;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.proiect.FireDatabase.getDataFromFireBase;
import eu.ase.proiect.Glide.GlideApp;
import eu.ase.proiect.asyncTask.AsyncTaskRunner;
import eu.ase.proiect.asyncTask.Callback;
import eu.ase.proiect.fragments.AllBooksFragment;
import eu.ase.proiect.fragments.BooksReadFragment;
import eu.ase.proiect.fragments.FavoriteBooksFragment;
import eu.ase.proiect.database.model.Book;
import eu.ase.proiect.fragments.SettingsFragment;
import eu.ase.proiect.network.HttpManager;
import eu.ase.proiect.util.Author;
import eu.ase.proiect.util.BookJsonParser;
import eu.ase.proiect.util.User;

public class MainActivity extends AppCompatActivity {

    public static String URL_BOOKS="https://jsonkeeper.com/b/4YYJ";
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Fragment currentFragment;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private ArrayList<Book> listBooks = new ArrayList<>();
    private List<Author> listAuthors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //incerc sa fac load la poza de profil


        //testex sa vad daca porneste cu ce e in baza de date
        getDataFromFireBase.getBooks(listBooks);

        configNavigation();


//        User.mapFavoriteBook.put(b.getId(),b);
        initComponents();
        openDefaultFragment(savedInstanceState);
//        Preluare carti din url
        //getBooksFromNetwork();


    }

    private void incarca_profil() {
        View navHeader=navigationView.getHeaderView(0);
        ImageView poza_profil=navHeader.findViewById(R.id.menu_imageView);
        TextView username_menu=navHeader.findViewById(R.id.menu_username);
        TextView usernmail_menu=navHeader.findViewById(R.id.menu_usermail);
        Uri uri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        String mail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String name=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        GlideApp.with(navHeader).load(uri).into(poza_profil);
        //poza_profil.setImageURI(uri);
        username_menu.setText(name);
        usernmail_menu.setText(mail);
    }

    private void getBooksFromNetwork(){
        Callable<String> asyncOperation = new HttpManager(URL_BOOKS);
        Callback<String> mainThreadOperation = getMainThreadOperationForBooks();
        asyncTaskRunner.executeAsync(asyncOperation,mainThreadOperation);

        //testex sa vad daca porneste cu ce e in baza de date
        getDataFromFireBase.getBooks(listBooks);
    }


//    Preluare carti din JSON
    private Callback<String> getMainThreadOperationForBooks() {
        return new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {

//                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                listBooks.addAll(BookJsonParser.fromJson(result, listAuthors));
                if (currentFragment instanceof AllBooksFragment) {
                    ((AllBooksFragment) currentFragment).notifyInternalAdapter();
                }
                if (currentFragment instanceof FavoriteBooksFragment){
                    ((FavoriteBooksFragment) currentFragment).notifyInternalAdapter();
                }
            }
        };
    }


    private void configNavigation() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

  }

    private void initComponents() {
        navigationView=findViewById(R.id.nav_view);

       // select item din meniu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.nav_all_books){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    currentFragment = AllBooksFragment.newInstance(listBooks);
                    ft.replace(R.id.main_frame_container, currentFragment);
                    ft.commit();
                }
                else if(item.getItemId() == R.id.nav_favorite){
                    currentFragment = new FavoriteBooksFragment();
                    openFragment();
                }
                else if(item.getItemId() == R.id.nav_books_read) {
                    currentFragment = new BooksReadFragment();
                    openFragment();
                } else if(item.getItemId() == R.id.nav_settings){
                    currentFragment= new SettingsFragment();
                    openFragment();
                }

                Toast.makeText(getApplicationContext(),
                        getString(R.string.show_option,item.getTitle()),
                        Toast.LENGTH_LONG).show();

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        incarca_profil();

    }



//    setare titlu pentru fiecare fragment
    public void setActionBatTitle(String title){
        toolbar.setTitle(title);
    }


    /*********   FRAGMENTE    *********/

    private void openFragment() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();

    }

    private void openDefaultFragment(Bundle saveInstanceState){
        if(saveInstanceState == null) {
            currentFragment =  AllBooksFragment.newInstance(listBooks);
            openFragment();
            navigationView.setCheckedItem(R.id.nav_all_books);

        }
    }

}