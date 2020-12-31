package eu.ase.proiect;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

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

import eu.ase.proiect.asyncTask.AsyncTaskRunner;
import eu.ase.proiect.asyncTask.Callback;
import eu.ase.proiect.database.service.AuthorService;
import eu.ase.proiect.database.service.BookService;
import eu.ase.proiect.fragments.AllBooksFragment;
import eu.ase.proiect.fragments.BooksReadFragment;
import eu.ase.proiect.fragments.FavoriteBooksFragment;
import eu.ase.proiect.database.model.Book;
import eu.ase.proiect.fragments.SettingsFragment;
import eu.ase.proiect.network.HttpManager;
import eu.ase.proiect.database.model.Author;
import eu.ase.proiect.util.BookJsonParser;

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

    private AuthorService authorService;
    private BookService bookService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configNavigation();
        //getDataFromFireBase.getaBook(listBooks);


        authorService = new AuthorService(getApplicationContext());
        bookService = new BookService(getApplicationContext());

//      lista allBooks este alcatuita din JSON si b, b1, b2 (hardcodate) (pe viitor va fi alc. din JSON si firebase)
        Book b = new Book(100,"An American Marriage","Is a book about romance and sweeting love!", "URLImage", 248, 11, 2.8f, R.drawable.book1, 0, 0, 200);
          Book b1 = new Book(101,"The Great Gasby","This book live in last generation. It's abaout crime.", "URLImage",
                  308, 21, 4.2f, R.drawable.gatsby2, 0,0,200);
          Book b2 = new Book(102,"The fault in our stars","Descriere", "URLImage", 321, 34, 4.8f, R.drawable.thefault, 0, 0, 200);

          listBooks.add(b);
          listBooks.add(b1);
          listBooks.add(b2);


          //        Author a = new Author(200,"Ion Creanga","Scurta biografie despre autor", "url nonfunctional");
//        authorService.insertAuthor(insertAuthorIntoDbCallback(), a);

          //          bookService.delete(deleteBookFromDbCallback(),b);
//          bookService.delete(deleteBookFromDbCallback(),b1);
//          bookService.delete(deleteBookFromDbCallback(),b2);

//////        inserare carte in baza de date
//          bookService.insertBook(insertBookIntoDbCallback(), b);
//          bookService.insertBook(insertBookIntoDbCallback(), b1);
//          bookService.insertBook(insertBookIntoDbCallback(), b2);

//          bookService.getAll(getAllBooksDbCallback());




//      firebaseFirestore.collection("Carti").document("solo_leveling")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        Book carte = documentSnapshot.toObject(Book.class);
//                        listBooks.add(carte);
//                    }
//                });


        initComponents();

//        Preluare carti din url
        getBooksFromNetwork();


        openDefaultFragment(savedInstanceState);



    }

    private void getBooksFromNetwork(){
        Callable<String> asyncOperation = new HttpManager(URL_BOOKS);
        Callback<String> mainThreadOperation = getMainThreadOperationForBooks();
        asyncTaskRunner.executeAsync(asyncOperation,mainThreadOperation);
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






/*************   DATABASE         *****************/
    public Callback<Author> insertAuthorIntoDbCallback(){
        return new Callback<Author>() {
            @Override
            public void runResultOnUiThread(Author result) {
                if(result != null){
                    listAuthors.add(result);
                }
            }
        };
    }


    private Callback<Book> insertBookIntoDbCallback(){
        return new Callback<Book>() {
            @Override
            public void runResultOnUiThread(Book result) {
                if(result != null){
                    listBooks.add(result);
//                    notifyAdapter();
                }
            }
        };
    }

    private Callback<List<Book>> getAllBooksDbCallback(){
        return new Callback<List<Book>>() {
            @Override
            public void runResultOnUiThread(List<Book> result) {
//                listBooks.clear();
                listBooks.addAll(result);

            }
        };
    }

    private Callback<Integer> deleteBookFromDbCallback(){
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {

                if(result != -1){

                }
            }
        };

    }


}